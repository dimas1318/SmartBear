package com.example.android.smartbear.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.android.smartbear.FeedPagerFragment;
import com.example.android.smartbear.LoginActivity;
import com.example.android.smartbear.MainActivity;
import com.example.android.smartbear.SignupActivity;
import com.example.android.smartbear.course_details.CourseDetailsFragment;
import com.example.android.smartbear.course_details.ShortCourseDetailsFragment;
import com.example.android.smartbear.course_material.CourseMaterialFragment;
import com.example.android.smartbear.courses.data.Course;
import com.example.android.smartbear.lessons.data.Material;
import com.example.android.smartbear.material.TextCourseMaterialFragment;
import com.example.android.smartbear.tools.ToolsFragment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by parsh on 02.01.2018.
 */

@Singleton
public class Navigator extends BaseNavigator implements LoginRouter, MainRouter {

    @Inject
    public Navigator() {
    }

    /**
     * LOGIN ROUTER
     */

    @Override
    public void navigateToMainActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void navigateToLoginActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void navigateToSignupActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, SignupActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * MAIN ROUTER
     */

    @Override
    public void navigateToFeedPagerFragment(AppCompatActivity activity, int container) {
        replaceFragment(activity, container, FeedPagerFragment.newInstance(), false, false);
    }

    @Override
    public void navigateToCourseDetailsFragment(AppCompatActivity activity, int container, Course course) {
        replaceFragment(activity, container, CourseDetailsFragment.newInstance(course), false, false);
    }

    @Override
    public void navigateToShortCourseDetailsFragment(AppCompatActivity activity, int container, Course course) {
        replaceFragment(activity, container, ShortCourseDetailsFragment.newInstance(course), false, false);
    }

    @Override
    public void navigateToCourseMaterialFragment(AppCompatActivity activity, int container, List<Material> materials) {
        replaceFragment(activity, container, CourseMaterialFragment.newInstance(materials), false, false);
    }

    @Override
    public void navigateToTextCourseMaterialFragment(AppCompatActivity activity, int container, String text) {
        replaceFragment(activity, container, TextCourseMaterialFragment.newInstance(text), false, false);
    }

    @Override
    public void navigateToToolsFragment(AppCompatActivity activity, int container) {
        replaceFragment(activity, container, ToolsFragment.newInstance(), false, false);
    }
}
