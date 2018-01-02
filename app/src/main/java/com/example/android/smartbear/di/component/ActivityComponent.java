package com.example.android.smartbear.di.component;

import com.example.android.smartbear.BaseActivity;
import com.example.android.smartbear.di.PerActivity;
import com.example.android.smartbear.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by parsh on 02.01.2018.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    BaseActivity activity();
}
