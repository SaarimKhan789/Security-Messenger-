package com.example.securemessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button e,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

            public void Encrypt(View view) {
                Intent intent=new Intent(MainActivity.this,Encoder.class);
                startActivity(intent);
            }

            public void Decrypt(View view) {
                Intent intent=new Intent(MainActivity.this,Decoder.class);
                startActivity(intent);
            }



}