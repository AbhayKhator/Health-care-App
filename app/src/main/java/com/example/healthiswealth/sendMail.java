package com.example.healthiswealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sendMail extends AppCompatActivity {

    Button logout;
    Button home;

    Button EMail;

    FirebaseAuth Auth;

    FirebaseUser user;

    TextView Mail,Subject, Content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);


        logout = findViewById(R.id.Logout_btn);
        home = findViewById(R.id.Btn_home);
        EMail = findViewById(R.id.button);

        Mail = findViewById(R.id.mail);
        Subject = findViewById(R.id.Subject);
        Content = findViewById(R.id.Content);


        Auth=FirebaseAuth.getInstance();
        user = Auth.getCurrentUser();


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

        EMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subj= Subject.getText().toString(),
                        mails= Mail.getText().toString(),
                        cont= Content.getText().toString();

                if (subj.equals("") && cont.equals("") && mails.equals("")){
                    Toast.makeText(sendMail.this , "Please fill all fields",Toast.LENGTH_SHORT);

                }else {
                    sendMail(mails,cont,subj);
                    Subject.setText("");
                    Mail.setText("");
                    Content.setText("");
                }
            }
        });


    }

    public void sendMail(String To_mail, String TO_Content, String Subjectsend){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[] {To_mail});
        intent.putExtra(Intent.EXTRA_SUBJECT,Subjectsend);
        intent.putExtra(Intent.EXTRA_TEXT,TO_Content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose app to send Mail: "));

    }


}