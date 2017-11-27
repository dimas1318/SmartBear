package com.example.android.smartbear.courses.presenter;

import com.example.android.smartbear.courses.data.CourseListItem;
import com.example.android.smartbear.courses.view.CourseView;
import com.example.android.smartbear.database.CourseManagerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parsh on 01.11.2017.
 */

public class AllCoursesPresenter {

    private List<CourseListItem> courses;
    private CourseView view;

    public AllCoursesPresenter() {
        courses = new CourseManagerImpl().getAllCourses();
    }

    public void requestSearch(String template) {
        ArrayList<CourseListItem> localCourses = new ArrayList<>();

        for (CourseListItem courseItem : courses) {
            if (isTargetStartsWithTemplate(template, courseItem.getCourseName())) {
                localCourses.add(courseItem);
            }
        }

        view.refreshData(localCourses);
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
