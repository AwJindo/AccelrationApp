package com.example.accelrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StopActivity extends AppCompatActivity {

    private Button stoptest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        stoptest = (Button) findViewById(R.id.stoptest);
        stoptest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openTestResultActivity(); }
        });
    }

    public void openTestResultActivity(){
        DeviceControlActivity.readData=false;
        Intent intent = new Intent(this,TestResultActivity.class);
        startActivity(intent);
    }
}