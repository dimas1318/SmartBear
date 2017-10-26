package com.example.android.smartbear.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartbear.R;

import butterknife.ButterKnife;

/**
 * Created by parsh on 12.10.2017.
 */

public class ToolsFragment extends android.support.v4.app.Fragment {
    public ToolsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
