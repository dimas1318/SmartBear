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
import com.example.android.smartbear.course_details.ShortCourseDetailsFragment;
import com.example.android.smartbear.courses.data.Course;
import com.example.android.smartbear.courses.data.CourseListCache;
import com.example.android.smartbear.courses.holder.AllCourseListViewHolder;
import com.example.android.smartbear.database.CourseManager;
import com.example.android.smartbear.database.CourseManagerFirebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parsh on 01.11.2017.
 */

public class AllCourseListAdapter extends RecyclerView.Adapter {
    private List<Course> allCourseList;
    private FragmentManager fragmentManager;
    private Context context;

    private List<Course> userCourseList = CourseListCache.getInstance().getCourseList();

//    public AllCourseListAdapter(FragmentManager fragmentManager) {
//        this.fragmentManager = fragmentManager;
//    }

    public AllCourseListAdapter(FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.allCourseList = new ArrayList<>();
    }


    @Override
    public AllCourseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_all_courses_item, parent, false);
        return new AllCourseListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((AllCourseListViewHolder) holder).courseLogo.setImageResource(allCourseList.get(position).getCourseId());
        ((AllCourseListViewHolder) holder).nameOfCourse.setText(allCourseList.get(position).getName());
        ((AllCourseListViewHolder) holder).details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCourseDetails(position);
            }
        });
        ((AllCourseListViewHolder) holder).addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourseToList(position);
                Toast.makeText(context, "Course was added to your course list", Toast.LENGTH_LONG).show();
            }
        });
        if (userCourseList.contains(allCourseList.get(position))) {
            ((AllCourseListViewHolder) holder).addCourseButton.setText("You have already added this course");
            ((AllCourseListViewHolder) holder).addCourseButton.setClickable(false);
        } else {
            ((AllCourseListViewHolder) holder).addCourseButton.setText("Add course");
            ((AllCourseListViewHolder) holder).addCourseButton.setClickable(true);
        }
    }

    @Override
    public int getItemCount() {
        return allCourseList.size();
    }

    private void addCourseToList(int position) {
        CourseManager courseManager = new CourseManagerFirebase();
        courseManager.addCourse(allCourseList.get(position));
    }

    public void showCourseDetails(int position) {
        FragmentManager fm = fragmentManager;
        Fragment fragment = ShortCourseDetailsFragment.newInstance(allCourseList.get(position));
        fm
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_container, fragment)
                .commit();
    }

    public void set(List<Course> courses) {
        // first remove
        if (allCourseList.size() > 0)
            notifyItemRangeRemoved(0, allCourseList.size());
        // than set new data
        allCourseList = new ArrayList<>(courses);
        notifyItemRangeInserted(0, allCourseList.size());
    }
}
