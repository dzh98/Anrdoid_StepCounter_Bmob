package com.daizihao.stepcounter.Bean;

import cn.bmob.v3.BmobObject;

public class StepBean extends BmobObject {
    String username;
    int step_value;

    public int getStep_value() {
        return this.step_value;
    }

    public void setStep_value(int step_value) {
        this.step_value = step_value;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
