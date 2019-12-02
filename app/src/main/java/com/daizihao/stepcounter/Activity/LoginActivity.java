package com.daizihao.stepcounter.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daizihao.stepcounter.Bean.UserBean;
import com.daizihao.stepcounter.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username_text;
        final EditText password_text;
        Button login_button;
        username_text = findViewById(R.id.username_text);
        password_text = findViewById(R.id.password_text);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_text.getText().toString();
                String password = password_text.getText().toString();
                final UserBean userBean = new UserBean();
                userBean.setUsername(username);
                userBean.setPassword(password);
                userBean.login(new SaveListener<UserBean>() {
                    @Override
                    public void done(UserBean bmobUser, BmobException e) {
                        if (e == null) {
                            UserBean user = BmobUser.getCurrentUser(UserBean.class);
                            Toast toast = Toast.makeText(getApplicationContext(), "当前登录账号:" + user.getUsername(), Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(LoginActivity.this, CounterActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "登录失败:" + e.getMessage(), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });

            }
        });
    }
}
