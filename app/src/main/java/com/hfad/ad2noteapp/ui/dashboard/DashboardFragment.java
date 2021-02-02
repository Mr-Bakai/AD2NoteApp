package com.hfad.ad2noteapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hfad.ad2noteapp.OnItemClickListener;
import com.hfad.ad2noteapp.R;
import com.hfad.ad2noteapp.models.Note;
import com.hfad.ad2noteapp.ui.home.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        initList();
        loadData();
    }

    // DashBoard > OnViewCreated
    private void loadData() {
        FirebaseFirestore.getInstance()

                .collection("notes")

                .get()

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

//                            List<Note> notes = new ArrayList<>();
//
//                            for (DocumentSnapshot snapshot : task.getResult()) {
//
//                                String docId = snapshot.getId();
//
//                                Note note = snapshot.toObject(Note.class);
//
//                                note.setNoteId(docId);
//
//                                notes.add(note);
//                            }

                            List<Note> list = task.getResult().toObjects(Note.class);
                            adapter.setList(list);

                        } else {

                        }
                    }
                });
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void longClick(int position) {

            }
        });
    }
}