package com.example.android.smartbear.courses.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smartbear.R;
import com.example.android.smartbear.courses.course_details.CourseDetailsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Samsung on 26.10.2017.
 */

public class CourseListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<CourseListItem> courseList;
    private FragmentManager fragmentManager;

    public CourseListAdapter(List<CourseListItem> courseList, FragmentManager fragmentManager) {
        this.courseList = courseList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public CourseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_course_item, parent, false);
        return new CourseListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CourseListViewHolder) holder).courseLogo.setImageResource(courseList.get(position).getCourseLogoId());
        ((CourseListViewHolder) holder).nameOfCourse.setText(String.valueOf(courseList.get(position).getNameOfCourse()));
        ((CourseListViewHolder) holder).details.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = fragmentManager;
        Fragment fragment = new CourseDetailsFragment();
        fm
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_container, fragment)
                .commit();
    }

    public static class CourseListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.courseLogo) ImageView courseLogo;
        @BindView(R.id.nameOfCourse) TextView nameOfCourse;
        @BindView(R.id.details) Button details;

        public CourseListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
