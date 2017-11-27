package com.example.android.smartbear.courses.data;

import com.example.android.smartbear.lessons.data.Lesson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Samsung on 26.10.2017.
 */

public class CourseListItem implements Serializable {
    private int courseLogoId;
    private String courseName;
    private List<Lesson> lessons;

    public CourseListItem(int courseLogo, String courseName) {
        this.courseLogoId = courseLogo;
        this.courseName = courseName;
    }

    public CourseListItem(int courseLogo, String courseName, List<Lesson> lessons) {
        this.courseLogoId = courseLogo;
        this.courseName = courseName;
        this.lessons = lessons;
    }

    public int getCourseLogoId() {
        return courseLogoId;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}
