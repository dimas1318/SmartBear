package com.example.android.smartbear.events;

import com.example.android.smartbear.courses.data.Course;

import java.util.List;

/**
 * Created by parsh on 05.12.2017.
 */

public class ListOfAllCoursesDownloadedEvent {
    private final List<Course> courses;

    public ListOfAllCoursesDownloadedEvent(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
