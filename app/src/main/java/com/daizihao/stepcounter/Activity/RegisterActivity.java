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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText username_text;
        final EditText pwd_text;
        final EditText repwd_text;
        Button register_button;
        username_text = findViewById(R.id.username_text);
        username_text.setFocusable(true);
        pwd_text = findViewById(R.id.pwd_text);
        repwd_text = findViewById(R.id.repwd_text);
        register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_text.getText().toString();
                String pwd = pwd_text.getText().toString();
                String repwd = repwd_text.getText().toString();
                if (pwd.equals(repwd)) {
                    final UserBean userBean = new UserBean();
                    userBean.setUsername(username);
                    userBean.setPassword(pwd);
                    userBean.signUp(new SaveListener<UserBean>() {
                        @Override
                        public void done(UserBean user, BmobException e) {
                            if (e == null) {
                                Toast toast = Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT);
                                toast.show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                if (e.getErrorCode() == 202) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "账号名已被使用", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            }
                        }
                    });
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "两次输入的密码不一样", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
