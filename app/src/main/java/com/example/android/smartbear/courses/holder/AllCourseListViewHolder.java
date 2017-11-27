package com.example.android.smartbear.courses.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smartbear.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parsh on 01.11.2017.
 */

public class AllCourseListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.courseLogo)
    public ImageView courseLogo;
    @BindView(R.id.nameOfCourse)
    public TextView nameOfCourse;
    @BindView(R.id.details)
    public Button details;
    @BindView(R.id.btn_add_course)
    public Button addCourseButton;

    public AllCourseListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
