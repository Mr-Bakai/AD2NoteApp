package com.hfad.ad2noteapp.ui.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hfad.ad2noteapp.OnItemClickListener;
import com.hfad.ad2noteapp.Prefs;
import com.hfad.ad2noteapp.R;
import com.hfad.ad2noteapp.models.BoardData;

import java.util.ArrayList;

public class BoardFragment extends Fragment {

    private BoardAdapter adapter;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnSkip).setOnClickListener(v -> close());

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        adapter = new BoardAdapter();
        viewPager.setAdapter(adapter);
        addBoardData();

        tabLayout = view.findViewById(R.id.indicator);

//        tabLayout.setupWithViewPager(viewPager, true);


        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText((""))).attach();


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                close();
            }

            @Override
            public void longClick(int position) {

            }
        });

        requireActivity().getOnBackPressedDispatcher().
                addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {

                        requireActivity().finish();

                    }
                });
    }

    // =====================================================================
    private void addBoardData() {
        ArrayList<BoardData> list = new ArrayList<>();

        list.add(new BoardData("Connected",
                "Stay connected anywhere you go. " +
                "You will have access to fast internet," +
                " Make your trip brighter with us", R.raw.data));
        list.add( new BoardData("Easy",
                "Pick any destination to go " +
                " You will get guide from local people " +
                " Who will warmly meet you and will show you the coolest places, ", R.raw.jump));
        list.add(new BoardData("Together",
                "Stay together wherever you go ." +
                        " We have tracker in each app so you will be able to see you friends location", R.raw.mm));

        adapter.addDataToBoard(list);
    }

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardsStatus();
        NavController navController = Navigation.findNavController(
                requireActivity(),
                R.id.nav_host_fragment);
        navController.navigate(R.id.phoneFragment);
    }
}