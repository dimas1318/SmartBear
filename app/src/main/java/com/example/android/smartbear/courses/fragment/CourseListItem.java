package com.example.android.smartbear.courses.fragment;

/**
 * Created by Samsung on 26.10.2017.
 */

public class CourseListItem {
    private int courseLogoId;
    private String nameOfCourse;

    public CourseListItem(int courseLogo, String nameOfCourse) {
        this.courseLogoId = courseLogo;
        this.nameOfCourse = nameOfCourse;
    }

    public int getCourseLogoId() {
        return courseLogoId;
    }

    public String getNameOfCourse() {
        return nameOfCourse;
    }
}
