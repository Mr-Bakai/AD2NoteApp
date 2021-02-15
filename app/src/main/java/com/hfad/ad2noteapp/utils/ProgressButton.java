package com.hfad.ad2noteapp.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hfad.ad2noteapp.R;

public class ProgressButton {

    /**
     * this class controls views in the progress_button layout
     * see how it is done in here  https://www.youtube.com/watch?v=zv9R5EcRKHM&t=103s
     */

    private CardView cardView;
    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;
    private TextView textView;

    Animation fadeIn;


    public ProgressButton(Context context, View view) {

        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

        cardView = view.findViewById(R.id.cardViewForPBar);
        constraintLayout = view.findViewById(R.id.constraintForPBar);
        progressBar = view.findViewById(R.id.progressBarBtn);
        textView = view.findViewById(R.id.textViewForPBar);

    }

    public void buttonActivated(){
        progressBar.setAnimation(fadeIn);
        progressBar.setVisibility(View.VISIBLE);
        textView.setAnimation(fadeIn);
        textView.setText("Saving");
    }

    public void buttonFinished(){
        constraintLayout.setBackgroundColor(cardView.getResources().getColor(R.color.grey));
        progressBar.setVisibility(View.GONE);
        textView.setText("DONE");
    }
}
