package com.example.healthiswealth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements LocationListener {

    FirebaseAuth Auth;
    Button Logout;
    Button Rssfeed;
    Button sendMail;
    TextView Mail;
    FirebaseUser user;
    private LocationManager locationManager;

    Button LocationBtn;
    TextView LocationText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Auth=FirebaseAuth.getInstance();
        Logout = findViewById(R.id.Btn_Logout);
        Mail= findViewById(R.id.show_mail);
        user = Auth.getCurrentUser();
        sendMail= findViewById(R.id.Btn_mail);
        Rssfeed = findViewById(R.id.RssActiv);
        LocationBtn = findViewById(R.id.Btn_Location);
        LocationManager locationManager;
        LocationText = findViewById(R.id.CurrentLoacationTextbox);




        if(user == null){
            Intent intent2 = new Intent(getApplicationContext(), Login.class);
            startActivity(intent2);
            finish();

        }else{

            Mail.setText(user.getEmail());
        }



        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent2);
                finish();
            }

        });
        Rssfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), com.example.healthiswealth.rssFeed.class);
                startActivity(intent2);
                finish();
            }

        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), com.example.healthiswealth.sendMail.class);
                startActivity(intent2);
                finish();
            }
        });


        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        LocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

    }

    private void getLocation(){
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,MainActivity.this);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        Toast.makeText(this, ""+location.getLatitude()+""+location.getLongitude(),Toast.LENGTH_SHORT).show();

        try {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            LocationText.setText(address);



        }catch(Exception e){
            e.printStackTrace();
        }
    }
}