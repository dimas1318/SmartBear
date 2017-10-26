package com.example.android.smartbear.courses.data;

/**
 * Created by Samsung on 26.10.2017.
 */

public class CourseListItem {
    private int courseLogoId;
    private String courseName;

    public CourseListItem(int courseLogo, String courseName) {
        this.courseLogoId = courseLogo;
        this.courseName = courseName;
    }

    public int getCourseLogoId() {
        return courseLogoId;
    }

    public String getCourseName() {
        return courseName;
    }
}
