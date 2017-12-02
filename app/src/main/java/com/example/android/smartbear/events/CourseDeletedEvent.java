package com.example.android.smartbear.events;

/**
 * Created by parsh on 02.12.2017.
 */

public class CourseDeletedEvent {
    private final int position;

    public CourseDeletedEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
