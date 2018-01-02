package com.example.android.smartbear.navigation;

import android.support.v7.app.AppCompatActivity;

import com.example.android.smartbear.courses.data.Course;
import com.example.android.smartbear.lessons.data.Material;

import java.util.List;

/**
 * Created by parsh on 02.01.2018.
 */

public interface MainRouter {
    void navigateToFeedPagerFragment(AppCompatActivity activity, int container);

    void navigateToCourseDetailsFragment(AppCompatActivity activity, int container, Course course);

    void navigateToShortCourseDetailsFragment(AppCompatActivity activity, int container, Course course);

    void navigateToCourseMaterialFragment(AppCompatActivity activity, int container, List<Material> materials);

    void navigateToTextCourseMaterialFragment(AppCompatActivity activity, int container, String text);

    void navigateToToolsFragment(AppCompatActivity activity, int container);
}
