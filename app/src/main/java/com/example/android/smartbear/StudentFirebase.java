package com.example.android.smartbear;

import java.util.List;

/**
 * Created by parsh on 19.11.2017.
 */

public class StudentFirebase {
    private String studentId;
    private List<AvailableCourse> availableCourses;

    public StudentFirebase() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<AvailableCourse> getAvailableCourses() {
        return availableCourses;
    }

    public void setAvailableCourses(List<AvailableCourse> availableCourses) {
        this.availableCourses = availableCourses;
    }
}
