package com.example.android.smartbear;

import android.support.v4.app.Fragment;

/**
 * Created by parsh on 02.01.2018.
 */

public abstract class BaseFragment extends Fragment {
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
