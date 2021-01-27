package com.hfad.ad2noteapp.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hfad.ad2noteapp.R;

import java.util.concurrent.TimeUnit;

public class PhoneFragment extends Fragment {
    private EditText editPhone;
    private Button btnNext;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private Button btnSms;
    private EditText editSms;
    private TextView textTimer;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000; // 10 sec
    private boolean timeRunning;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPhone = view.findViewById(R.id.editPhone);
        btnNext = view.findViewById(R.id.btnNext);

        btnSms = view.findViewById(R.id.btnSms);
        editSms = view.findViewById(R.id.editSms);
        textTimer = view.findViewById(R.id.textTimer);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSms();

                editPhone.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);

                btnSms.setVisibility(View.VISIBLE);
                editSms.setVisibility(View.VISIBLE);

            }
        });

        setCallBacks();
    }

    private void countDown() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { // onTick method will be called every 1 second (countDownInterval : 1000)
                timeLeftInMilliseconds = millisUntilFinished;
                updateText();
            }

            @Override
            public void onFinish() {
                editPhone.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                editPhone.setTextColor(getResources().getColor(R.color.design_default_color_error));
                editPhone.setText("Check your phone number");

                btnSms.setVisibility(View.GONE);
                editSms.setVisibility(View.GONE);
                textTimer.setVisibility(View.GONE);
            }
        }.start();
    }

    private void updateText() {
        int minute = (int) timeLeftInMilliseconds / 60000; // we will get minutes in here
        int second = (int) timeLeftInMilliseconds % 60000 / 1000; // here we get second

        String timeLeftText;

        timeLeftText = "" + minute;
        timeLeftText += ":";

        if(second < 10) timeLeftText += "0";
        timeLeftText += second;
        textTimer.setText(timeLeftText);
    }

    private void setCallBacks() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("TAG", "onVerificationCompleted: ");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationFailed: " + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                countDown();
            }
        };
    }

    private void requestSms() {

        String phone = editPhone.getText().toString();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phone)                         // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)      // Timeout and unit
                        .setActivity(requireActivity())              // Activity (for callback binding)
                        .setCallbacks(callbacks)                    // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}