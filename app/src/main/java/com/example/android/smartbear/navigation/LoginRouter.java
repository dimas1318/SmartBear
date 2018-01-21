package com.example.android.smartbear.navigation;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by parsh on 02.01.2018.
 */

public interface LoginRouter {
    void navigateToMainActivity(AppCompatActivity activity);

    void navigateToLoginActivity(AppCompatActivity activity);

    void navigateToSignupActivity(AppCompatActivity activity);

    void navigateToForgotPasswordActivity(AppCompatActivity activity);
}
