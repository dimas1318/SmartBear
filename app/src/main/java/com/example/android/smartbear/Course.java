package com.example.android.smartbear;

import com.example.android.smartbear.lessons.data.Lesson;

import java.util.List;

/**
 * Created by parsh on 19.11.2017.
 */

public class Course {
    private int CourseID;
    private String Name;
    private List<Lesson> Lessons;

    public Course() {
    }

    public int getCourseId() {
        return CourseID;
    }

    public void setCourseId(int CourseID) {
        this.CourseID = CourseID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public List<Lesson> getLessons() {
        return Lessons;
    }

    public void setLessons(List<Lesson> Lessons) {
        this.Lessons = Lessons;
    }
}
