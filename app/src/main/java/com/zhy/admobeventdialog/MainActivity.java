package com.zhy.admobeventdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.admob.admobevwindow.ADMobEv;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void myclick(View view) {
        ADMobEv.getInstance().addEv("dsfhdsuhfus","电视上法国恢复");
    }
}