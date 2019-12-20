package com.daizihao.stepcounter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.daizihao.stepcounter.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "914fd45d728599bbc18e21ce50b1cea2");
        if (BmobUser.isLogin()) {
            Intent intent = new Intent(MainActivity.this, CounterActivity.class);
            startActivity(intent);
            finish();
        }
        Button login_button;
        Button register_button;
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }


}
