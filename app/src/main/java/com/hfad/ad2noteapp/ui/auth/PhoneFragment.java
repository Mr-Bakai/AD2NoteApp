package com.hfad.ad2noteapp.ui.auth;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hfad.ad2noteapp.R;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class PhoneFragment extends Fragment {
    private View viewPhone;
    private View viewCode;

    private Button btnNext;
    private Button btnSignIn;

    private EditText editPhone;

    private String verificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000; // 10 sec
    private boolean timeRunning;
    private TextView textTimer;

    private OtpTextView otpCode;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editPhone = view.findViewById(R.id.editPhone);
        textTimer = view.findViewById(R.id.textTimer);

        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnNext = view.findViewById(R.id.btnNext);

        viewPhone = view.findViewById(R.id.viewPhone);
        viewCode = view.findViewById(R.id.viewCode);

        otpCode = view.findViewById(R.id.otp_view);

        setListeners();
        setCallBacks();
        initView();
    }

    private void setListeners() {
        btnNext.setOnClickListener(v -> requestSms());
        btnSignIn.setOnClickListener(v -> confirm());

        otpCode.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the OtpBox
            }

            @Override
            public void onOTPComplete(String otp) {
                // fired when the user finished to enter otp code
                confirm();
                Log.e("TAG", "onOTPComplete: " + otp);

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

    private void confirm() {
//        String code = editCode.getText().toString();
        String code = otpCode.getOTP().trim();
        if (code.length() == 6 && TextUtils.isDigitsOnly(code)) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signIn(credential);
        }
    }

    private void initView() {
        showViewPhone();
    }

    private void showViewCode() {
        viewCode.setVisibility(View.VISIBLE);
        viewPhone.setVisibility(View.GONE);
    }

    private void showViewPhone() {
        viewCode.setVisibility(View.GONE);
        viewPhone.setVisibility(View.VISIBLE);
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

            }
        }.start();
    }

    private void updateText() {
        int minute = (int) timeLeftInMilliseconds / 60000; // we will get minutes in here
        int second = (int) timeLeftInMilliseconds % 60000 / 1000; // here we get second

        String timeLeftText;

        timeLeftText = "" + minute;
        timeLeftText += ":";

        if (second < 10) timeLeftText += "0";
        timeLeftText += second;
        textTimer.setText(timeLeftText);
    }

    private void setCallBacks() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("TAG", "onVerificationCompleted: ");
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationFailed: " + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s,
                                   @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                countDown();
                showViewCode();
            }
        };
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {

        FirebaseAuth.getInstance()
                .signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            close();

                        } else {

                            Toast.makeText(requireContext(), "Error : " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    private void close() {
        NavController navController = Navigation.findNavController(
                requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();
    }
}