package com.example.android.smartbear.courses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samsung on 26.10.2017.
 */

public class CourseListCache {

    private List<CourseListItem> courseList = new ArrayList<>();

    public List<CourseListItem> getCourseList() {
        return courseList;
    }

    private static volatile CourseListCache s;

    private CourseListCache(){}

    public static CourseListCache getInstance(){
        if (s != null ) return s;
        synchronized(CourseListCache.class){
            if (s == null ) {
                s = new CourseListCache();
            }
        }
        return s;
    }
}
