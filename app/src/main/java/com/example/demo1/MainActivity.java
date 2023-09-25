package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public
class MainActivity extends AppCompatActivity {

    public static int TIME_OUT = 5000;
    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ตั้งเวลาเปลี่นหน้าจอ
        new Handler().postDelayed((Runnable) new Runnable() {
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, Camera.class);
                startActivity(homeIntent);
                finish();
            }
        }, TIME_OUT);
    }
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        b1 = (Button) findViewById(R.id.button);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Camera.class);
//                startActivity(intent);
//            }
//        });
//    }
}
