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
import com.example.android.smartbear.courses.holder.CourseListViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Samsung on 26.10.2017.
 */

public class CourseListAdapter extends RecyclerView.Adapter {
    private List<CourseListItem> courseList;
    private FragmentManager fragmentManager;

    public CourseListAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public CourseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_course_item, parent, false);
        return new CourseListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((CourseListViewHolder) holder).courseLogo.setImageResource(courseList.get(position).getCourseLogoId());
        ((CourseListViewHolder) holder).nameOfCourse.setText(courseList.get(position).getCourseName());
        ((CourseListViewHolder) holder).details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCourseDetails();
            }
        });
        ((CourseListViewHolder) holder).deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourseFromList(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    private void showCourseDetails() {
        FragmentManager fm = fragmentManager;
        Fragment fragment = new CourseDetailsFragment();
        fm
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_container, fragment)
                .commit();
    }

    private void deleteCourseFromList(int position) {
        courseList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, courseList.size());
    }

    public void set(List<CourseListItem> courses) {
        courseList = courses;
        notifyDataSetChanged();
    }
}
