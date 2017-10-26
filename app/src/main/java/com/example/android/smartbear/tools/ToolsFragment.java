package com.example.android.smartbear.tools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.android.smartbear.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parsh on 12.10.2017.
 */

public class ToolsFragment extends android.support.v4.app.Fragment {

    @BindView(R.id.radio_btn_eng_language)
    RadioButton englishLanguageButton;
    @BindView(R.id.radio_btn_rus_language)
    RadioButton russianLanguageButton;

    public ToolsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        ButterKnife.bind(getActivity(), view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        englishLanguageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "HELLO", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
