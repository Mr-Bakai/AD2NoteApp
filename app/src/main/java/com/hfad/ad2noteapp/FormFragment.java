package com.hfad.ad2noteapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hfad.ad2noteapp.models.Note;
import com.hfad.ad2noteapp.utils.ProgressButton;
import com.hfad.ad2noteapp.utils.ProgressDialog;

import java.util.Date;

public class FormFragment extends Fragment {

    public static final String TAG = "GGG";

    private TextInputLayout editText;
    private Note note;
    private ProgressButton progressButton;
    private View includeView;

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

        editText = (TextInputLayout) view.findViewById(R.id.editText);
        includeView = view.findViewById(R.id.progressButton);
        includeView.setOnClickListener(v -> save());

//        btnSave = view.findViewById(R.id.btnSave);
//        //.setOnClickListener(v -> save());
//        btnSave.setOnClickListener(v -> save());


        //====================requireArguments======================
        note = (Note) requireArguments().getSerializable("note");
        if (note != null) editText.getEditText().setText(note.getTitle());

        progressButton = new ProgressButton(requireContext(), view);

        showKeyBoard(editText);

    }

    private void save() {
        progressButton.buttonActivated();
        hideKeyboard();

        String text = editText.getEditText().getText().toString().trim();
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
                progressButton.buttonFinished();

                /* we wrapped into handler  for better UX
                 after showing progress bar, we gave a user to see text that says "DONE"
                 for a second  and then we go back to homeFragment
                 */

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (task.isSuccessful()) {

                            note.setNoteId(task.getResult().getId());
                            App.getAppDataBase().noteDao().insert(note);

                            Log.e("GGG", "onComplete: Note has been added with an Id of: " +
                                    "" + task.getResult().getId());
                            close();

                        } else {

                            Log.e("GGG", "onComplete: is failed");

                        }
                    }
                }, 1000);
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

                    Log.e(TAG, "onComplete:  failed to update");

                }
            }
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();
    }


    private void showKeyBoard(TextInputLayout editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = requireActivity().getCurrentFocus();
        if (view == null) view = new View(requireActivity());
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}