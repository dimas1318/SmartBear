package com.example.android.smartbear.courses.presenter;

import com.example.android.smartbear.MainActivity;
import com.example.android.smartbear.courses.data.Course;
import com.example.android.smartbear.courses.view.CourseView;
import com.example.android.smartbear.courses.view.fragment.AllCoursesFragment;
import com.example.android.smartbear.database.CourseManager;
import com.example.android.smartbear.database.CourseManagerFirebase;
import com.example.android.smartbear.events.CourseStateChangedEvent;
import com.example.android.smartbear.events.ListOfAllCoursesDownloadedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parsh on 01.11.2017.
 */

public class AllCoursesPresenter {

    private List<Course> courses;
    private CourseView view;
    private final Bus bus;

    public AllCoursesPresenter() {
        bus = MainActivity.bus;
        bus.register(this);

        CourseManager courseManager = new CourseManagerFirebase();
        courses = courseManager.getAllCourses();
    }

    public void requestSearch(String template) {
        ArrayList<Course> localCourses = new ArrayList<>();

        for (Course courseItem : courses) {
            if (isTargetStartsWithTemplate(template, courseItem.getName())) {
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

    public List<Course> getCourses() {
        return courses;
    }

    @Subscribe
    public void refreshData(ListOfAllCoursesDownloadedEvent event) {
        view.refreshData(event.getCourses());
    }

    @Subscribe
    public void changeCourseState(CourseStateChangedEvent event) {
        ((AllCoursesFragment) view).update();
    }
}
