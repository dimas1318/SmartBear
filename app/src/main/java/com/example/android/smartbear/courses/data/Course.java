package com.example.android.smartbear.courses.data;

import com.example.android.smartbear.CourseInfo;
import com.example.android.smartbear.lessons.data.Lesson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Samsung on 26.10.2017.
 */

public class Course implements Serializable {

    private int CourseID;
    private CourseInfo courseInfo;
    private String Name;
    private List<Lesson> Lessons;


    public Course() {
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }


    public Course(int courseLogo, String Name) {
        this.CourseID = courseLogo;
        this.Name = Name;
    }

    public Course(int courseLogo, String Name, List<Lesson> lessons) {
        this.CourseID = courseLogo;
        this.Name = Name;
        this.Lessons = lessons;
    }

    public int getCourseId() {
        return CourseID;
    }

    public String getName() {
        return Name;
    }

    public List<Lesson> getLessons() {
        return Lessons;
    }
    public void setCourseId(int courseId) {
        this.CourseID = courseId;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setLessons(List<Lesson> lessons) {
        this.Lessons = lessons;
    }
}
