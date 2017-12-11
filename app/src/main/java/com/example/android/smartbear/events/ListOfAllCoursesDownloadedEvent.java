package com.example.android.smartbear.events;

import com.example.android.smartbear.courses.data.CourseListItem;

import java.util.List;

/**
 * Created by parsh on 05.12.2017.
 */

public class ListOfAllCoursesDownloadedEvent {
    private final List<CourseListItem> courses;

    public ListOfAllCoursesDownloadedEvent(List<CourseListItem> courses) {
        this.courses = courses;
    }

    public List<CourseListItem> getCourses() {
        return courses;
    }
}
