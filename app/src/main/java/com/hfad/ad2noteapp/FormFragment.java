package com.hfad.ad2noteapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hfad.ad2noteapp.models.Note;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FormFragment extends Fragment {
    private EditText editText;
    private Note note;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_form,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.editText);

        view.findViewById(R.id.btnSave).setOnClickListener(v -> save());

        //====================requireArguments======================
        note = (Note) requireArguments().getSerializable("note");
        if (note != null) editText.setText(note.getTitle());

    }

    private void save() {
        String text = editText.getText().toString().trim();
        Bundle bundle = new Bundle();
        String date = java.text.DateFormat.getDateTimeInstance().format(new Date());

        if (note == null) {

            note = new Note(text, date);
            saveToFireStore(note);

        } else {

            note.setTitle(text);
            upDateFireStore(note);

        }
        bundle.putSerializable("note", note);
        getParentFragmentManager().setFragmentResult("rk_form", bundle);

    }

    private void saveToFireStore(Note note) {
        FirebaseFirestore.getInstance()
                .collection("notes")
                .add(note).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {

                    note.setNoteId(task.getResult().getId());
                    App.getAppDataBase().noteDao().insert(note);

                    Log.e("GGG", "onComplete: Note has been added with an Id of: " + task.getResult().getId());
                    close();

                } else {

                    Log.e("GGG", "onComplete: is failed");

                }
            }
        });

    }

    private void upDateFireStore(Note note) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference noteRef = db.collection("notes")
                .document(note.getNoteId());

        noteRef.update("title", note.getTitle()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    App.getAppDataBase().noteDao().update(note);
                    Log.e("GGG", "onComplete: Updated  to" + note.getTitle());
                    close();

                } else {

                    Log.e("GGG", "onComplete:  failed to update");

                }
            }
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();
    }
}