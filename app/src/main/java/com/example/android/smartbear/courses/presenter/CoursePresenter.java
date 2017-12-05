package com.example.android.smartbear.courses.presenter;

import com.example.android.smartbear.MainActivity;
import com.example.android.smartbear.courses.data.CourseListItem;
import com.example.android.smartbear.courses.view.CourseView;
import com.example.android.smartbear.database.CourseManager;
import com.example.android.smartbear.database.CourseManagerFirebase;
import com.example.android.smartbear.events.CourseDeletedEvent;
import com.example.android.smartbear.events.ListOfCoursesDownloadedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parsh on 29.10.2017.
 */

public class CoursePresenter {

    private List<CourseListItem> courses;
    private CourseView view;
    private final Bus bus;

    public CoursePresenter() {
        bus = MainActivity.bus;
        bus.register(this);

        CourseManager courseManager = new CourseManagerFirebase();
        courses = courseManager.getUserCourses();
    }

    public void requestSearch(String template) {
        ArrayList<CourseListItem> localCourses = new ArrayList<>();

        for (CourseListItem courseItem : courses) {
            if (isTargetStartsWithTemplate(template, courseItem.getCourseName())) {
                localCourses.add(courseItem);
            }
        }

        view.refreshData(localCourses);
    }

    private boolean isTargetStartsWithTemplate(String template, String target) {
        template = template.toLowerCase();
        target = target.toLowerCase();
        return target.startsWith(template);
    }

    public void setView(CourseView view) {
        this.view = view;
    }

    public List<CourseListItem> getCourses() {
        return courses;
    }

    @Subscribe
    public void refreshData(ListOfCoursesDownloadedEvent event) {
        view.refreshData(event.getCourses());
    }

    @Subscribe
    public void deleteCourse(CourseDeletedEvent event) {
        view.deleteCourse(event.getPosition());
    }
}
