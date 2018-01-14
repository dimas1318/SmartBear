package com.example.android.smartbear;

import java.util.List;

/**
 * Created by parsh on 19.11.2017.
 */

public class Student {
    private String studentId;
    private List<AvailableCourse> availableCourses;

    public Student() {
    }

    public Student(String studentId, List<AvailableCourse> availableCourses) {
        this.studentId = studentId;
        this.availableCourses = availableCourses;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String StudentID) {
        this.studentId = StudentID;
    }

    public List<AvailableCourse> getAvailableCourses() {
        return availableCourses;
    }

    public void setAvailableCourses(List<AvailableCourse> availableCourses) {
        this.availableCourses = availableCourses;
    }
}
