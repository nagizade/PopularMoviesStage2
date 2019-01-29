package com.nagizade.popularmoviesstage2;

/**
 * Created by Hasan Nagizade on 29 January 2019
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class NoInternet extends AppCompatActivity {

    private Button checkAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
           if(isNetworkAvailable(getApplicationContext())) {
                Intent intent = new Intent(NoInternet.this, MainActivity.class);
                 startActivity(intent);
                }


        checkAgainButton = findViewById(R.id.buttonCheckAgain);

        checkAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoInternet.this, "Checking internet connection..", Toast.LENGTH_SHORT).show();
                if(isNetworkAvailable(v.getContext())) {
                    Intent intent = new Intent(NoInternet.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NoInternet.this, "Internet is not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
