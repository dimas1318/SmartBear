package com.example.android.smartbear.tools;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.smartbear.R;

import java.util.Locale;

/**
 * Created by parsh on 12.10.2017.
 */

public class ToolsFragment extends android.support.v4.app.Fragment {
    public ToolsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        view.findViewById(R.id.radio_btn_eng_language).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                String languageToLoad = "en";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                Toast.makeText(getActivity().getApplicationContext(), "english is set", Toast.LENGTH_SHORT).show();
                getActivity().getBaseContext().getResources().updateConfiguration(configuration, getActivity().getBaseContext().getResources().getDisplayMetrics());
            }
        });

        view.findViewById(R.id.radio_btn_rus_language).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                String languageToLoad = "ru";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                Toast.makeText(getActivity().getApplicationContext(), "russian is set", Toast.LENGTH_SHORT).show();
                getActivity().getBaseContext().getResources().updateConfiguration(configuration, getActivity().getBaseContext().getResources().getDisplayMetrics());
            }
        });

        return view;
    }
}
