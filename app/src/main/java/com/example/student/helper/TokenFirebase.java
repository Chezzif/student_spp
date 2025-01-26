package com.example.student.helper;


import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class TokenFirebase extends FirebaseMessagingService {

    private static final String TAG = "TokenFirebase";

    @Override
    public void onNewToken(@NonNull String token){
        super.onNewToken(token);

        Log.d(TAG,"onNewToken: "+ token);

        SharedPreferences sp = getSharedPreferences("firebase",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token",token);
        editor.apply();

        Log.d(TAG,"Token telah disimpan di SharedPreferences: "+token);
    }

}
