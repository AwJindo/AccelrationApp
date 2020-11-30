package com.example.accelrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button starttest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        starttest = (Button) findViewById(R.id.starttest);
        starttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openStopActivity();}
        });
    }

    public void openStopActivity(){
        Intent intent = new Intent(this, StopActivity.class);
        startActivity(intent);
    }
}