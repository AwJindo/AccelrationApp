package com.example.accelrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent intent = getIntent();
        String text = intent.getStringExtra(NewUserActivity.EXTRA_TEXT);
        int number1 = intent.getIntExtra(NewUserActivity.EXTRA_NUMBER1,0);
        int number2 = intent.getIntExtra(NewUserActivity.EXTRA_NUMBER2,0);
        int number3 = intent.getIntExtra(NewUserActivity.EXTRA_NUMBER3,0);
        int number4 = intent.getIntExtra(NewUserActivity.EXTRA_NUMBER4,0);

        TextView text_view_name = findViewById(R.id.text_view_name);
        TextView text_view_socialnumber = findViewById(R.id.text_view_socialnumber);
        TextView text_view_age = findViewById(R.id.text_view_age);
        TextView text_view_weight = findViewById(R.id.text_view_weight);
        TextView text_view_length = findViewById(R.id.text_view_length);

        text_view_name.setText(text);
        text_view_socialnumber.setText("" + number1);
        text_view_age.setText(""+ number2);
        text_view_weight.setText(""+number3);
        text_view_length.setText(""+number4);
    }
}