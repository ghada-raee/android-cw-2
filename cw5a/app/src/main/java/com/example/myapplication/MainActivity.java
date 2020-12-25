package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Double lat,longt;
    Location l;
    Geocoder geo;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText name = findViewById(R.id.name);
        final EditText age = findViewById(R.id.age);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
       // final EditText location = findViewById(R.id.location);
        FusedLocationProviderClient flc = LocationServices.getFusedLocationProviderClient(this);
        Button b = findViewById(R.id.nextPage);
        Button b1 = findViewById(R.id.location);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=true;
                if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    flc.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            l = task.getResult();
                            if(l != null){
                                try {
                                    geo = new Geocoder(MainActivity.this, Locale.getDefault());
                                    List<Address> addresses = geo.getFromLocation(l.getLatitude(),l.getLongitude(),1);
                                    lat = addresses.get(0).getLatitude();
                                    System.out.println("Lat "+lat);
                                    longt= addresses.get(0).getLongitude();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                }
                else{
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION }, 44);
                }

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                String sname = name.getText().toString().trim();
                String sage = age.getText().toString().trim();
                String semail = email.getText().toString().trim();
                String sphone = phone.getText().toString().trim();
                // String slocation = location.getText().toString().trim();
                if (sname.matches("") || semail.matches("") || sphone.matches("") || sage.matches("") /* ||  slocation.matches("")*/)
                    Toast.makeText(MainActivity.this, "Fill all the fields please", Toast.LENGTH_LONG).show();
                else {

                   /* boolean flag = true;
                    for(int j=0;j<slocation.length();j++){
                        char c = slocation.charAt(j);
                        if(Character.isDigit(c) || c == '-' || c =='.' || c==',')
                            continue;
                        else
                            flag = false;
                    }



                   if(flag==false || !slocation.contains(","))
                        Toast.makeText(MainActivity.this,"Invalid location coordinates input", Toast.LENGTH_LONG).show();
                    else */

                   /* if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                        if(getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                            flc.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if(location != null){
                                         lat = location.getLatitude();
                                        System.out.println("Lat "+ lat);
                                        longt= location.getLongitude();

                                    }
                                }
                            });}
                        else {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }

                    }*/


                    if (Integer.parseInt(sage) < 1)
                        Toast.makeText(MainActivity.this, "Invalid Age input", Toast.LENGTH_LONG).show();
                    if (sphone.length() != 8)
                        Toast.makeText(MainActivity.this, "phone number must consist of 8 numbers", Toast.LENGTH_LONG).show();
                    if (flag) {
                        i.putExtra("name", sname);
                        i.putExtra("age", sage);
                        i.putExtra("email", semail);
                        i.putExtra("phone", sphone);
                        System.out.println("Lat1: " + lat);
                        if(lat==null)
                            i.putExtra("location", "please turn on your GPS");
                        else
                            i.putExtra("location", lat + " , " + longt);
                        flag = false;
                        lat=null;
                        longt=null;
                        startActivity(i);
                    } else
                        Toast.makeText(MainActivity.this, "please press on the location button", Toast.LENGTH_LONG).show();
                }
            }



        });

    }


}