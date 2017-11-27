package com.example.android.smartbear.events;

import com.example.android.smartbear.courses.data.CourseListItem;

import java.util.List;

/**
 * Created by dmitryparshin on 27.11.17.
 */

public class ListOfCoursesDownloadedEvent {
    private final List<CourseListItem> courses;

    public ListOfCoursesDownloadedEvent(List<CourseListItem> courses) {
        this.courses = courses;
    }

    public List<CourseListItem> getCourses() {
        return courses;
    }
}
