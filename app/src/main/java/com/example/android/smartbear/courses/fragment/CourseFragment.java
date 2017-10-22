package com.example.android.smartbear.courses.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartbear.R;

/**
 * Created by parsh on 12.10.2017.
 */

public class CourseFragment extends android.support.v4.app.Fragment {
    public CourseFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course, container, false);
    }
}
