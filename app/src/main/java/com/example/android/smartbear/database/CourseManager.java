package com.example.android.smartbear.database;

import com.example.android.smartbear.courses.data.CourseListItem;

import java.util.List;

/**
 * Created by Samsung on 30.10.2017.
 */

public interface CourseManager {

   List<CourseListItem> getUserCourses();

   List<CourseListItem> getAllCourses();

   void deleteCourse(CourseListItem course, int position);
}
