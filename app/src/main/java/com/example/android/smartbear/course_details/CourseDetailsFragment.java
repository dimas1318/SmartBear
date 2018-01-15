package com.example.android.smartbear.course_details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.smartbear.BaseFragment;
import com.example.android.smartbear.R;
import com.example.android.smartbear.course_material.CourseMaterialFragment;
import com.example.android.smartbear.courses.data.Course;
import com.example.android.smartbear.lessons.data.Lesson;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Samsung on 26.10.2017.
 */

public class CourseDetailsFragment extends BaseFragment {

    public static CourseDetailsFragment newInstance(Course course) {
        CourseDetailsFragment fragment = new CourseDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable("COURSE", course);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_details, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.lesson_layout);

        Course course = (Course) getArguments().getSerializable("COURSE");
        if (course.getLessons() != null) {

            view.findViewById(R.id.no_data).setVisibility(View.GONE);

            for (final Lesson lesson : course.getLessons()) {
                View lessonLayout = getLayoutInflater().inflate(R.layout.item_lesson, null);
                ((TextView) (lessonLayout.findViewById(R.id.lesson_name))).setText(lesson.getName());
                ((TextView) (lessonLayout.findViewById(R.id.lesson_order))).setText(String.valueOf(lesson.getOrder()));

                if (lesson.getMaterials() != null) {
                    TextView materialLabel = lessonLayout.findViewById(R.id.material_title);
                    materialLabel.setVisibility(View.VISIBLE);
                    materialLabel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentManager fm = getFragmentManager();
                            Fragment fragment = CourseMaterialFragment.newInstance(lesson.getMaterials());
                            fm
                                    .beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.main_container, fragment)
                                    .commit();
                        }
                    });
                }
                linearLayout.addView(lessonLayout);
            }
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
