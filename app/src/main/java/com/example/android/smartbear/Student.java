package com.example.android.smartbear;

import java.util.List;

/**
 * Created by parsh on 19.11.2017.
 */

public class Student {
    private String StudentID;
    private List<AvailableCourse> AvailableCourses;

    public Student() {
    }

    public String getStudentId() {
        return StudentID;
    }

    public void setStudentId(String StudentID) {
        this.StudentID = StudentID;
    }

    public List<AvailableCourse> getAvailableCourses() {
        return AvailableCourses;
    }

    public void setAvailableCourses(List<AvailableCourse> AvailableCourses) {
        this.AvailableCourses = AvailableCourses;
    }
}
