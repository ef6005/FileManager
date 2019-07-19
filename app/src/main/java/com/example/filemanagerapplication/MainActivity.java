package com.example.filemanagerapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//single activity application
//MainActivity manages all fragment transactions
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
