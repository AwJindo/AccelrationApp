package com.example.accelrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class NewUserActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.accelrationapp.EXTRA_TEXT";
    public static final String EXTRA_NUMBER1 = "com.example.accelrationapp.EXTRA_NUMBER1";
    public static final String EXTRA_NUMBER2 = "com.example.accelrationapp.EXTRA_NUMBER2";
    public static final String EXTRA_NUMBER3 = "com.example.accelrationapp.EXTRA_NUMBER3";
    public static final String EXTRA_NUMBER4 = "com.example.accelrationapp.EXTRA_NUMBER4";
    private Button confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openUserInfoActivity(); }
        });
    }

    public void openUserInfoActivity(){
        EditText text_input_name = findViewById(R.id.text_input_name);
        String text = text_input_name.getText().toString();

        EditText text_input_socialnumber = findViewById(R.id.text_input_socialnumber);
        String number1 = text_input_socialnumber.getText().toString();

        EditText text_input_age =  findViewById(R.id.text_input_age);
        int number2 = Integer.parseInt(text_input_age.getText().toString());

        EditText text_input_weight =  findViewById(R.id.text_input_weight);
        int number3 = Integer.parseInt(text_input_weight.getText().toString());

        EditText text_input_length = findViewById(R.id.text_input_length);
        int number4 = Integer.parseInt(text_input_length.getText().toString());


        Intent intent = new Intent(this,UserInfoActivity.class);
        intent.putExtra(EXTRA_TEXT,text);
        intent.putExtra(EXTRA_NUMBER1,number1);
        intent.putExtra(EXTRA_NUMBER2,number2);
        intent.putExtra(EXTRA_NUMBER3,number3);
        intent.putExtra(EXTRA_NUMBER4,number4);
        startActivity(intent);
    }
}