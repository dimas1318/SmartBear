package com.example.android.smartbear.events;

import com.example.android.smartbear.courses.data.CourseListItem;

import java.util.List;

/**
 * Created by parsh on 05.12.2017.
 */

public class ListOfAllCoursesDownloadedEvent {
    private final List<CourseListItem> courseListItems;

    public ListOfAllCoursesDownloadedEvent(List<CourseListItem> courseListItems) {
        this.courseListItems = courseListItems;
    }

    public List<CourseListItem> getCourseListItems() {
        return courseListItems;
    }
}
