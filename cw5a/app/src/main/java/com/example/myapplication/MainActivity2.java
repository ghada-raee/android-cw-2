package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle b = getIntent().getExtras();
        String name = b.getString("name");
        String age = b.getString("age");
        String email = b.getString("email");
        String phone = b.getString("phone");
        String location = b.getString("location");

        TextView tname = findViewById(R.id.nametext);
        TextView tage = findViewById(R.id.agetext);
        TextView temail = findViewById(R.id.emailtext);
        TextView tphone = findViewById(R.id.phonetext);
        TextView tlocation = findViewById(R.id.locationtext);
        tname.setText("Name: "+name);
        tage.setText("Age: "+age);
        temail.setText("E-mail: "+email);
        tphone.setText("Phone: "+phone);
        tlocation.setText("Location: "+location);

        tphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+Long.parseLong(phone)));
                startActivity(call);
            }
        });

        temail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.putExtra(Intent.EXTRA_EMAIL,email);
                mail.setType("text/plain");
                startActivity(Intent.createChooser(mail,"Send email"));
            }
        });

        tlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+location);
                Intent map = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                map.setPackage("com.google.android.apps.maps");
                startActivity(map);

            }
        });



    }
}