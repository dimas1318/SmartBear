package com.example.android.smartbear.course_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.smartbear.CourseInfo;
import com.example.android.smartbear.R;
import com.example.android.smartbear.base.fragments.BaseFragment;
import com.example.android.smartbear.courses.data.Course;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Samsung on 12.12.2017.
 */

public class ShortCourseDetailsFragment extends BaseFragment {

    @BindView(R.id.lecturer)
    TextView lecturer;
    @BindView(R.id.numberOfLessons)
    TextView numberOfLessons;
    @BindView(R.id.description)
    TextView description;

    public static ShortCourseDetailsFragment newInstance(Course course) {
        ShortCourseDetailsFragment fragment = new ShortCourseDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable("COURSE", course);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short_course_details, container, false);
        ButterKnife.bind(this, view);

        Course course = (Course) getArguments().getSerializable("COURSE");
        CourseInfo courseInfo = course.getCourseInfo();
        if (courseInfo != null) {
            lecturer.setText(courseInfo.getLecturer());
            numberOfLessons.setText(String.valueOf(course.getLessons().size()));
            description.setText(courseInfo.getBriefInfo());
        } else {
            lecturer.setText("неизвестно");
            numberOfLessons.setText("неизвестно");
            description.setText("неизвестно");
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOnline();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOffline();
        }
    }
}
