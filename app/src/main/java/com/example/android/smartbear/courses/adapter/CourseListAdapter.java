package com.example.android.smartbear.courses.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.smartbear.R;
import com.example.android.smartbear.course_details.CourseDetailsFragment;
import com.example.android.smartbear.courses.data.Course;
import com.example.android.smartbear.courses.data.CourseListCache;
import com.example.android.smartbear.courses.holder.CourseListViewHolder;
import com.example.android.smartbear.database.CourseManager;
import com.example.android.smartbear.database.CourseManagerFirebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samsung on 26.10.2017.
 */

public class CourseListAdapter extends RecyclerView.Adapter {
    private List<Course> courseList;
    private FragmentManager fragmentManager;
    private Context context;

    private CourseManager courseManager = new CourseManagerFirebase();

//    public CourseListAdapter(FragmentManager fragmentManager) {
//        this.fragmentManager = fragmentManager;
//        this.courseList = new ArrayList<>();
//    }

    public CourseListAdapter(FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.courseList = new ArrayList<>();
    }

    @Override
    public CourseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_course_item, parent, false);
        return new CourseListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((CourseListViewHolder) holder).courseLogo.setImageResource(courseList.get(position).getCourseId());
        ((CourseListViewHolder) holder).nameOfCourse.setText(courseList.get(position).getName());
        ((CourseListViewHolder) holder).details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCourseDetails(position);
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

    private void showCourseDetails(int position) {

        FragmentManager fm = fragmentManager;
        Fragment fragment = CourseDetailsFragment.newInstance(courseList.get(position));
        fm
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_container, fragment)
                .commit();
    }

    private void deleteCourseFromList(int position) {
        courseManager.deleteCourse(courseList.get(position), position);
    }

    public void set(List<Course> courses) {
        // first remove
        if (courseList.size() > 0)
            notifyItemRangeRemoved(0, courseList.size());
        // than set new data
        courseList = new ArrayList<>(courses);
        notifyItemRangeInserted(0, courseList.size());
    }

    public void deleteCourse(int position) {
        CourseListCache.getInstance().getCourseList().remove(courseList.get(position));
        courseList.remove(position);
        notifyItemRemoved(position);

        Toast.makeText(context, "Course was deleted from your course list", Toast.LENGTH_LONG).show();
    }
}
