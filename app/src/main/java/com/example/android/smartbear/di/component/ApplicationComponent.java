package com.example.android.smartbear.di.component;

import com.example.android.smartbear.BaseActivity;
import com.example.android.smartbear.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by parsh on 02.01.2018.
 */

@Singleton //This component one per application
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
}
