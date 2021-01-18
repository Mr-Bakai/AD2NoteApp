package com.hfad.ad2noteapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.ad2noteapp.MainActivity;
import com.hfad.ad2noteapp.OnItemClickListener;
import com.hfad.ad2noteapp.Prefs;
import com.hfad.ad2noteapp.R;
import com.hfad.ad2noteapp.models.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private  Prefs prefs;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // this method runs once only when you launch the app
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new NoteAdapter(getContext());
        add10();
    }

    private void add10() {
        for (int i = 10; i > 0; i--) {
            String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
            adapter.addItem(new Note("This is note " + i, date));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);

        view.findViewById(R.id.fab).setOnClickListener(v -> openForm());
        setFragmentListener();
        initList();
    }

    private void initList() {
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {

                // here you get the note that you have clicked on the list

                Note note = adapter.getItem(position);
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void longClick(int position) { // StackOverFlow: Closing a custom alert dialog on button click

                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.dialog_layout, null);

                Button delete = view.findViewById(R.id.delete);
                Button cancel = view.findViewById(R.id.cancel);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                        .setView(view);

                final AlertDialog dialog = alert.create();

                delete.setOnClickListener(v -> {
                    adapter.remove(position);
                    dialog.dismiss();
                });

                cancel.setOnClickListener(v -> dialog.dismiss());
                dialog.show();
            }
        });
    }

    private void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("rk_form", getViewLifecycleOwner(),
                (requestKey, result) -> {
                    Note note = (Note) result.getSerializable("note");
                    adapter.addItem(note);
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu,
                                    @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        prefs = new Prefs(requireContext());

        switch (item.getItemId()) {
            case R.id.clear_settings:
                prefs.clearS();
                requireActivity().finish();
            return true;
            case R.id.test:
        }
        return super.onOptionsItemSelected(item);
    }
}