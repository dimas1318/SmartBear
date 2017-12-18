package com.example.android.smartbear.events;

import com.example.android.smartbear.courses.data.Course;
import com.example.android.smartbear.courses.data.CourseListCache;

import java.util.List;

/**
 * Created by dmitryparshin on 27.11.17.
 */

public class ListOfCoursesDownloadedEvent {
    private final List<Course> courses;

    public ListOfCoursesDownloadedEvent(List<Course> courses) {
        this.courses = courses;
        CourseListCache.getInstance().getCourseList().addAll(courses);
    }

    public List<Course> getCourses() {
        return courses;
    }
}
