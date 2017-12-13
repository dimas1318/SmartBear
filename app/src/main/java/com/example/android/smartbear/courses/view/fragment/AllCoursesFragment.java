package com.example.android.smartbear.courses.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.android.smartbear.R;
import com.example.android.smartbear.courses.adapter.AllCourseListAdapter;
import com.example.android.smartbear.courses.data.Course;
import com.example.android.smartbear.courses.presenter.AllCoursesPresenter;
import com.example.android.smartbear.courses.view.CourseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parsh on 29.10.2017.
 */

public class AllCoursesFragment extends Fragment implements CourseView {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.no_courses_tv)
    TextView noCoursesTextView;

    private List<Course> courses;

    private AllCoursesPresenter presenter;
    private AllCourseListAdapter adapter;

    public static AllCoursesFragment newInstance() {
        AllCoursesFragment fragment = new AllCoursesFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        presenter = new AllCoursesPresenter();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        initView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (android.widget.SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.requestSearch(newText);
                return false;
            }
        });
    }

    @Override
    public void refreshData(List<Course> courses) {
        adapter.set(courses);
        if (courses == null || courses.isEmpty()) {
            noCoursesTextView.setVisibility(View.VISIBLE);
        } else {
            noCoursesTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteCourse(int position) {

    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AllCourseListAdapter(getActivity().getSupportFragmentManager());
        List<Course> courseList = presenter.getCourses();
        if (courseList == null || courseList.isEmpty()) {
            noCoursesTextView.setVisibility(View.VISIBLE);
        } else {
            noCoursesTextView.setVisibility(View.GONE);
        }
        adapter.set(courseList);
        recyclerView.setAdapter(adapter);

        presenter.setView(this);
    }
}
