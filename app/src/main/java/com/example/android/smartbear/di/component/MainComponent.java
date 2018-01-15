package com.example.android.smartbear.di.component;

import com.example.android.smartbear.di.PerActivity;
import com.example.android.smartbear.di.module.ActivityModule;
import com.example.android.smartbear.di.module.MainModule;

import dagger.Component;

/**
 * Created by parsh on 02.01.2018.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MainModule.class})
public interface MainComponent {

}
