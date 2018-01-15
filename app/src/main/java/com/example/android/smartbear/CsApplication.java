package com.example.android.smartbear;

import android.app.Application;

import com.example.android.smartbear.di.component.ApplicationComponent;

/**
 * Created by parsh on 02.01.2018.
 */

public class CsApplication extends Application {

    private Class[] CLASSES = {
    };

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }


    public void initializeInjector() {
//        this.applicationComponent = DaggerApplicationComponent.builder()
//                .applicationModule(new ApplicationModule(this, CLASSES))
//                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
