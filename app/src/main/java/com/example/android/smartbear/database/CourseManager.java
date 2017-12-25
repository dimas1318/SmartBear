package com.example.android.smartbear.database;

import com.example.android.smartbear.courses.data.Course;

import java.util.List;

/**
 * Created by Samsung on 30.10.2017.
 */

public interface CourseManager {

   List<Course> getUserCourses();

   List<Course> getAllCourses();

   void deleteCourse(Course course, int position);

   void addCourse(Course course);
}
