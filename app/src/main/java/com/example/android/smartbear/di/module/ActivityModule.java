package com.example.android.smartbear.di.module;

import com.example.android.smartbear.BaseActivity;
import com.example.android.smartbear.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by parsh on 02.01.2018.
 */

@Module
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    BaseActivity activity() {
        return this.activity;
    }
}