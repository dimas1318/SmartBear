package com.example.android.smartbear.courses.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartbear.R;
import com.example.android.smartbear.course_details.CourseDetailsFragment;
import com.example.android.smartbear.courses.data.CourseListItem;
import com.example.android.smartbear.courses.holder.AllCourseListViewHolder;
import com.example.android.smartbear.courses.holder.CourseListViewHolder;

import java.util.List;

/**
 * Created by parsh on 01.11.2017.
 */

public class AllCourseListAdapter extends RecyclerView.Adapter {
    private List<CourseListItem> courseList;
    private FragmentManager fragmentManager;

    public AllCourseListAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public AllCourseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_all_courses_item, parent, false);
        return new AllCourseListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AllCourseListViewHolder) holder).courseLogo.setImageResource(courseList.get(position).getCourseLogoId());
        ((AllCourseListViewHolder) holder).nameOfCourse.setText(courseList.get(position).getCourseName());
        ((AllCourseListViewHolder) holder).details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCourseDetails();
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void showCourseDetails() {
        FragmentManager fm = fragmentManager;
        Fragment fragment = new CourseDetailsFragment();
        fm
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_container, fragment)
                .commit();
    }

    public void set(List<CourseListItem> courses) {
        courseList = courses;
        notifyDataSetChanged();
    }
}
