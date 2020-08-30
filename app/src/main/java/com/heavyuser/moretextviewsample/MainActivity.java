package com.heavyuser.moretextviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.heavyuser.moretextview.MoreTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoreTextView textView1 = findViewById(R.id.textView1);
        textView1.setText("1\n2\n3\n4\n5\n");

        MoreTextView textView2 = findViewById(R.id.textView2);
        textView2.setText("111111111111111111111111111111111111111111111111111111111111111\n2222222222222222222222222222222222222222222222222222222\n3333333333333333333333333333333333333333333333333333333\n4444444444444444444444444444444444444444444444444444444\n5555555555555555555555555555555555555555555555555555555");
        textView2.setLessButton(true);

        MoreTextView textView3 = findViewById(R.id.textView3);
        textView3.setText("hello world!, hello world!, hello world!, hello world!, hello world!, hello world!, hello world!, hello world!, hello world!, hello world!, hello world!, hello world!, hello world!, hello world!");
        textView3.setLessButton(true);

        MoreTextView textView4 = findViewById(R.id.textView4);
        textView4.setText("hello world!");
        textView4.setLessButton(true);
    }
}