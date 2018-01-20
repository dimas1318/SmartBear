package com.example.android.smartbear.base.activities;

import android.support.v7.app.AppCompatActivity;

import com.example.android.smartbear.navigation.Navigator;

import javax.inject.Inject;

/**
 * Created by parsh on 02.01.2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected Navigator navigator;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_core);
//
//        if (this.getApplicationComponent() != null)
//            this.getApplicationComponent().inject(this);
//
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//
//        setSupportActionBar(mToolbar);
//        if (getSupportActionBar() != null)
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//    }
}
