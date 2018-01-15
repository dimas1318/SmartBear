package com.example.android.smartbear;

import android.support.v7.app.AppCompatActivity;

import com.example.android.smartbear.navigation.Navigator;

import javax.inject.Inject;

/**
 * Created by parsh on 02.01.2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    protected Navigator navigator;
}
