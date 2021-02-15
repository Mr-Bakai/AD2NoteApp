package com.hfad.ad2noteapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import com.hfad.ad2noteapp.R;

public class ProgressDialog {

    private Dialog dialog;

    public ProgressDialog(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.progress, null);

        builder.setView(view);

        dialog = builder.create();
    }


    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }
}
