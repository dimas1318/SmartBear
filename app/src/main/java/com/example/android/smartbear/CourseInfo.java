package com.example.android.smartbear;

import java.io.Serializable;

/**
 * Created by dmitryparshin on 11.12.17.
 */

public class CourseInfo implements Serializable {
    private String BriefInfo;
    private String Lecturer;

    public CourseInfo() {

    }

    public String getBriefInfo() {
        return BriefInfo;
    }

    public String getLecturer() {
        return Lecturer;
    }
}
