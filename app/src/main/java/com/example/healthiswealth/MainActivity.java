package com.example.healthiswealth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth Auth;
    Button Logout;
    TextView Mail;
    FirebaseUser user;
    private ListView listView;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Auth=FirebaseAuth.getInstance();
        Logout = findViewById(R.id.Btn_Logout);
        Mail= findViewById(R.id.show_mail);
        user = Auth.getCurrentUser();




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


    }
    public class RssFeedParser {

        public ArrayList<String> parseRssFeed(String rssUrl) {
            ArrayList<String> feedItems = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(rssUrl).get();
                Elements items = doc.select("item"); // Assuming 'item' is the tag for each feed item
                for (Element item : items) {
                    String title = item.select("title").text();
                    String description = item.select("description").text();
                    // You can extract other fields as needed (e.g., link, pubDate)
                    String feedItem = title + ": " + description;
                    feedItems.add(feedItem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return feedItems;
        }
    }


}