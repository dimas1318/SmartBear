package com.example.android.smartbear.courses.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smartbear.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parsh on 27.10.2017.
 */

public class CourseListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.courseLogo)
    public ImageView courseLogo;
    @BindView(R.id.nameOfCourse)
    public TextView nameOfCourse;
    @BindView(R.id.details)
    public Button details;

    public CourseListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
