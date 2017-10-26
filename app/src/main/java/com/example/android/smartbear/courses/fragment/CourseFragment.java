package com.example.android.smartbear.courses.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartbear.R;

/**
 * Created by parsh on 12.10.2017.
 */

public class CourseFragment extends android.support.v4.app.Fragment {

    public CourseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course, container, false);
        //проверка работоспособности
        if (CourseListCache.getInstance().getCourseList().isEmpty()) {
            CourseListCache.getInstance().getCourseList().add(new CourseListItem(R.drawable.logo, "Какой то курс"));
            CourseListCache.getInstance().getCourseList().add(new CourseListItem(R.drawable.logo, "Еще какой то курс"));
            CourseListCache.getInstance().getCourseList().add(new CourseListItem(R.drawable.logo, "Очередной курс"));
            CourseListCache.getInstance().getCourseList().add(new CourseListItem(R.drawable.logo, "Очередной курс"));
            CourseListCache.getInstance().getCourseList().add(new CourseListItem(R.drawable.logo, "Интересный курс"));
            CourseListCache.getInstance().getCourseList().add(new CourseListItem(R.drawable.logo, "Неинтересный курс jsdvnsdjkn sk vnsdknv d vsdksdnklsd fv"));
        }
        //конец проверки
        createAdapterForCourseList(v);
        return v;
    }

    private void createAdapterForCourseList(View v) {
        RecyclerView rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        RecyclerView.Adapter adapterForCourseList = new CourseListAdapter(CourseListCache.getInstance().getCourseList(),
                getActivity().getSupportFragmentManager());
        rv.setAdapter(adapterForCourseList);
    }
}
