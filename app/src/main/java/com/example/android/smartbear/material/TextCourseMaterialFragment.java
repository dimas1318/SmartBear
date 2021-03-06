package com.example.android.smartbear.material;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.smartbear.R;
import com.example.android.smartbear.base.fragments.BaseFragment;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by dmitryparshin on 11.12.17.
 */

public class TextCourseMaterialFragment extends BaseFragment {

    private static final String TAG = TextCourseMaterialFragment.class.getName();

    public static TextCourseMaterialFragment newInstance(String text) {
        TextCourseMaterialFragment fragment = new TextCourseMaterialFragment();

        Bundle args = new Bundle();
        args.putString("TEXT", text);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_material, container, false);

        TextView textMaterial = view.findViewById(R.id.text_material);
        String text = getArguments().getString("TEXT");
        textMaterial.setText(text);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOnline();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOffline();
        }
    }
}
