package com.hfad.ad2noteapp.ui.auth;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "TAG";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.e(TAG, "onNewTokennnnnnnnnnn: " + s);

    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e(TAG, "onMessageReceiveddddddddddddd: " + remoteMessage );

    }
}
