package com.example.app;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioGroup;

public class Schedule extends AppCompatActivity {

    RadioGroup rGroup;
    int afterTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        rGroup = (RadioGroup) findViewById(R.id.rBtnGroup);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rBtnOne){
                    afterTime = 15000;
                }
                else if (checkedId == R.id.rBtnFive){
                    afterTime = 60000;
                }

                else if (checkedId == R.id.rBtntwo){
                    afterTime = 120000;
                }
                else if (checkedId == R.id.rBtnfive){
                    afterTime = 300000;
                }
            }
        });
    }

    public void callBtnEvent(View view){
                Intent callEvent = new Intent(Schedule.this, Caller.class);
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(callEvent);
                    }
                }, afterTime);
    }
}