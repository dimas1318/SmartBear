package com.example.android.smartbear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartbear.courses.view.fragment.AllCoursesFragment;
import com.example.android.smartbear.courses.view.fragment.CourseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parsh on 29.10.2017.
 */

public class FeedPagerFragment extends Fragment {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.pager_tabs_layout)
    TabLayout pagerTabs;

    private FeedPagerAdapter adapter;

    public static FeedPagerFragment newInstance() {
        FeedPagerFragment fragment = new FeedPagerFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager_with_tabs, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initPagerAndTabs();
    }

    private void initPagerAndTabs() {
        setupPager();
        pagerTabs.setupWithViewPager(pager);
    }

    private void setupPager() {
        adapter = new FeedPagerAdapter(getChildFragmentManager());
        adapter.addFragment(CourseFragment.newInstance(), "My courses");
        adapter.addFragment(AllCoursesFragment.newInstance(), "All courses");
        pager.setAdapter(adapter);
    }
}
