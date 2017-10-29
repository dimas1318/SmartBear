package com.example.android.smartbear.course_all;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartbear.R;
import com.example.android.smartbear.courses.data.CourseListCache;
import com.example.android.smartbear.courses.data.CourseListItem;
import com.example.android.smartbear.courses.view.adapter.CourseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parsh on 29.10.2017.
 */

public class AllCoursesFragment extends Fragment {
    private List<CourseListItem> courses;

    public static AllCoursesFragment newInstance() {
        return new AllCoursesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course, container, false);

        //проверка работоспособности
        courses = new ArrayList<>();
        courses.add(new CourseListItem(R.drawable.logo, "Какой то курс Лехи"));
        courses.add(new CourseListItem(R.drawable.logo, "Еще какой то курс Лехи"));
        courses.add(new CourseListItem(R.drawable.logo, "Очередной курс Лехи"));
        //конец проверки

        createAdapterForCourseList(v);
        return v;
    }

    private void createAdapterForCourseList(View v) {
        RecyclerView rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        RecyclerView.Adapter adapterForCourseList = new CourseListAdapter(courses,
                getActivity().getSupportFragmentManager());
        rv.setAdapter(adapterForCourseList);
    }
}
