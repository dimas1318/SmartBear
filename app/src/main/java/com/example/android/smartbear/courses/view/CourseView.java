package com.example.android.smartbear.courses.view;

import com.example.android.smartbear.courses.data.Course;

import java.util.List;

/**
 * Created by parsh on 29.10.2017.
 */

public interface CourseView {
    void refreshData(List<Course> courses);

    void deleteCourse(int position);
}
