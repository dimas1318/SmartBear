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
    private CourseInfo CourseInfo;
    private String CourseLogo;
    private String Name;
    private List<Lesson> Lessons;


    public Course() {
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

    public Course(int courseLogo, String Name, List<Lesson> lessons, CourseInfo courseInfo) {
        this.CourseID = courseLogo;
        this.Name = Name;
        this.Lessons = lessons;
        this.CourseInfo = courseInfo;
    }

    public CourseInfo getCourseInfo() {
        return CourseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.CourseInfo = courseInfo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return Name.equals(course.Name);

    }

    @Override
    public int hashCode() {
        return Name.hashCode();
    }

}
