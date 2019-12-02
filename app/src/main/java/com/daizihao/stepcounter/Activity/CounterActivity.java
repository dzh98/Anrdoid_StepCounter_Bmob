package com.daizihao.stepcounter.Activity;


import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daizihao.stepcounter.Bean.StepBean;
import com.daizihao.stepcounter.Bean.UserBean;
import com.daizihao.stepcounter.R;
import com.daizihao.stepcounter.View.CircularProgressView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.hardware.Sensor.TYPE_STEP_COUNTER;

public class CounterActivity extends BaseMenuActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView counter_view;
    private TextView aim_step_view;
    private Button sync_button;
    private Button rank_button;
    private CircularProgressView circularProgressView;
    private long mExitTime;
    private int int_step;
    private int aim_step = 10000;
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == TYPE_STEP_COUNTER) {
                Float raw_step = sensorEvent.values[0];
                int_step = Math.round(raw_step); //四舍五入
                counter_view.setText(String.valueOf(int_step)); //event.values[0]为计步历史累加值(开机以后)
                circularProgressView.setProgress(Math.round(int_step * 100 / aim_step), 800);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        counter_view = findViewById(R.id.counterView);
        aim_step_view = findViewById(R.id.aim_step_tv);
        aim_step_view.setText("目标  " + aim_step);
        circularProgressView = findViewById(R.id.circularProgressView);
        sync_button = findViewById(R.id.sync_button);
        rank_button = findViewById(R.id.rank_button);
        sync_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserBean user = BmobUser.getCurrentUser(UserBean.class);
                StepBean stepBean = new StepBean();
                String username = user.getUsername();
                stepBean.setUsername(username);
                stepBean.setStep_value(int_step);
                stepBean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "同步成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
        rank_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RankActivity.class);
                startActivity(intent);
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(TYPE_STEP_COUNTER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    protected void onPause() {
        sensorManager.unregisterListener(listener);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }

    }
}
