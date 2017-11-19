package com.example.android.smartbear;

import com.example.android.smartbear.lessons.data.Lesson;

import java.util.List;

/**
 * Created by parsh on 19.11.2017.
 */

public class CourseFirebase {
    private int courseId;
    private String name;
    private List<Lesson> lessons;

    public CourseFirebase() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
