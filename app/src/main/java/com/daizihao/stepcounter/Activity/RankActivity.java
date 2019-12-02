package com.daizihao.stepcounter.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.daizihao.stepcounter.Bean.StepBean;
import com.daizihao.stepcounter.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RankActivity extends BaseMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        BmobQuery<StepBean> query = new BmobQuery<>();
        query.setLimit(8).order("-step_value")
                .findObjects(new FindListener<StepBean>() {
                    @Override
                    public void done(List<StepBean> object, BmobException e) {
                        if (e == null) {
                            int length = object.size();
                            String[] rankData = new String[length];
                            for (int i = 0; i < length; i++) {
                                rankData[i] = (i + 1) + "    " + object.get(i).getUsername() + "              " + "步数" + object.get(i).getStep_value();
                            }
                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, rankData);
                            ListView listView = findViewById(R.id.Rank_ListView);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d("Eank_List_Error", e.getMessage());
                        }
                    }
                });


    }
}
