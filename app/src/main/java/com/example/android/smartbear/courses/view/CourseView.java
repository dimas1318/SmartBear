package com.example.android.smartbear.courses.view;

import com.example.android.smartbear.courses.data.CourseListItem;

import java.util.List;

/**
 * Created by parsh on 29.10.2017.
 */

public interface CourseView {
    void showItemsList(List<CourseListItem> localCourses);
}
