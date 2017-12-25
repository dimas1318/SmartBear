package com.example.android.smartbear.database;

import com.example.android.smartbear.R;
import com.example.android.smartbear.courses.data.CourseListCache;
import com.example.android.smartbear.courses.data.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samsung on 30.10.2017.
 */

public class CourseManagerImpl implements CourseManager {
    @Override
    public List<Course> getUserCourses() {
        if (CourseListCache.getInstance().getCourseList().isEmpty()) {
            CourseListCache.getInstance().getCourseList().add(new Course(R.drawable.logo, "Какой то курс"));
            CourseListCache.getInstance().getCourseList().add(new Course(R.drawable.logo, "Еще какой то курс"));
            CourseListCache.getInstance().getCourseList().add(new Course(R.drawable.logo, "Очередной курс"));
            CourseListCache.getInstance().getCourseList().add(new Course(R.drawable.logo, "Очередной курс"));
            CourseListCache.getInstance().getCourseList().add(new Course(R.drawable.logo, "Интересный курс"));
            CourseListCache.getInstance().getCourseList().add(new Course(R.drawable.logo, "Неинтересный курс jsdvnsdjkn sk vnsdknv d vsdksdnklsd fv"));
        }
        return CourseListCache.getInstance().getCourseList();
    }


    @Override
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        list.add(new Course(R.drawable.logo, "Какой то курс Лехи"));
        list.add(new Course(R.drawable.logo, "Еще какой то курс Лехи"));
        list.add(new Course(R.drawable.logo, "Очередной курс Лехи"));
        return list;
    }

    @Override
    public void deleteCourse(Course course, int position) {

    }

    @Override
    public void addCourse(Course course) {
        
    }
}
