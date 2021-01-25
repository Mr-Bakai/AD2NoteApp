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

import com.hfad.ad2noteapp.models.Note;

import java.util.Date;

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
        if(note !=null) editText.setText(note.getTitle());

    }

    private void save() {

        String text = editText.getText().toString().trim();
        Bundle bundle = new Bundle();
        String date = java.text.DateFormat.getDateTimeInstance().format(new Date());

        if (note == null){

            note = new Note(text, date);
            bundle.putSerializable("note", note);
            App.getAppDataBase().noteDao().insert(note);

        } else {

            note.setTitle(text);
            bundle.putSerializable("noteForSet", note);
            App.getAppDataBase().noteDao().update(note);
            
    }
        getParentFragmentManager().setFragmentResult("rk_form", bundle);
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();
    }
}