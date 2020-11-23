package com.example.accelrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private Button newuser;
    private Button existinguser;
    private ImageButton bluetoothbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newuser = (Button) findViewById(R.id.newuser);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewUserActivity();
            }
        });

        existinguser = (Button) findViewById(R.id.existinguser);
        existinguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExistingUserActivity();
            }
        });

        bluetoothbutton = (ImageButton) findViewById(R.id.bluetoothbutton);
        bluetoothbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openBTConectionActivity(); }
        });
    }

    public void openNewUserActivity(){
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }
    public void openExistingUserActivity(){
        Intent intent = new Intent(this, ExistingUserActivity.class);
        startActivity(intent);
    }

    public void openBTConectionActivity(){
        Intent intent = new Intent(this, BTConectionActivity.class);
        startActivity(intent);
    }
}