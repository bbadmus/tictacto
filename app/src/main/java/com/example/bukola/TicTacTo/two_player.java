package com.example.bukola.TicTacTo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class two_player extends AppCompatActivity {

    public static Activity act_2p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        act_2p = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);
    }
}
