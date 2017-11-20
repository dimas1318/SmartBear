package com.example.android.smartbear.courses.presenter;

import android.view.View;

import com.example.android.smartbear.courses.view.CourseView;
import com.example.android.smartbear.courses.data.CourseListItem;
import com.example.android.smartbear.courses.view.fragment.CourseFragment;
import com.example.android.smartbear.database.CourseManager;
import com.example.android.smartbear.database.CourseManagerFirebase;
import com.example.android.smartbear.database.CourseManagerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parsh on 29.10.2017.
 */

public class CoursePresenter {

    private List<CourseListItem> courses;
    private CourseView view;

    public CoursePresenter() {
        CourseManager courseManager = new CourseManagerFirebase();
        courses = courseManager.getUserCourses();
    }

    public void requestSearch(String template) {
        ArrayList<CourseListItem> localCourses = new ArrayList<>();

        for (CourseListItem courseItem : courses) {
            if (isTargetStartsWithTemplate(template, courseItem.getCourseName())) {
                localCourses.add(courseItem);
            }
        }

        view.showItemsList(localCourses);
    }

    private boolean isTargetStartsWithTemplate(String template, String target) {
        template = template.toLowerCase();
        target = target.toLowerCase();
        return target.startsWith(template);
    }

    public void setView(CourseView view) {
        this.view = view;
    }

    public List<CourseListItem> getCourses() {
        return courses;
    }
}
