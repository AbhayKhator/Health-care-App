package com.example.healthiswealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class rssFeed extends AppCompatActivity {

    Button logout;
    Button home;

    Button feed1,feed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        logout = findViewById(R.id.Logout_btn);
        home = findViewById(R.id.Btn_home);
        feed1 = findViewById(R.id.feed1);
        feed2 = findViewById(R.id.feed2);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent2);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), com.example.healthiswealth.MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });


    }
}