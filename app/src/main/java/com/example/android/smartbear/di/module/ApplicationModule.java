package com.example.android.smartbear.di.module;

import com.example.android.smartbear.CsApplication;

import dagger.Module;

/**
 * Created by parsh on 02.01.2018.
 */

@Module
public class ApplicationModule {

    private final CsApplication application;
    private Class[] CLASSES = {};

    public ApplicationModule(CsApplication application, Class[] CLASSES) {
        this.application = application;
        this.CLASSES = CLASSES;
    }
}
