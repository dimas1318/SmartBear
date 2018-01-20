package com.example.android.smartbear.base.fragments;

import android.app.Service;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.smartbear.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by parsh on 02.01.2018.
 */

public abstract class BaseFragment extends Fragment {

    private int backstackPosition;
    private Unbinder unbinder;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

//        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
//            if (getBaseActivity().getSupportActionBar() != null) {
//                settingToolbar();
//                getBaseActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);
//                setDisplayHomeButton();
//            }
//        }

        backstackPosition = getActivity().getSupportFragmentManager().getBackStackEntryCount();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

//    protected void clearMenu() {
//        getBaseActivity().getToolbar().getMenu().clear();
//    }
//
//    public BaseActivity getBaseActivity() {
//        return (BaseActivity) getActivity();
//    }
//
//    public void showProgress() {
//        if (getActivity() != null) {
//            ((BaseActivity) getActivity()).showProgress();
//        }
//    }
//
//    public void hideProgress() {
//        if (getActivity() != null) {
//            ((BaseActivity) getActivity()).hideProgress();
//        }
//    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int message) {
        Toast.makeText(getContext(), getString(message), Toast.LENGTH_SHORT).show();
    }

    protected void showAlertWith(String title, String message, DialogInterface.OnClickListener positiveClick) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), positiveClick)
                .setCancelable(false)
                .create().show();
    }

//    protected void setToolbarVisibility(boolean visible) {
//        getBaseActivity().setToolbarVisibility(visible ? View.VISIBLE : View.GONE);
//    }
//
//    protected void setToolbarCheckAllVisibility(boolean visible) {
//        getBaseActivity().setToolbarCheckAllVisibility(visible ? View.VISIBLE : View.GONE);
//    }
//
//    protected void setCheckAllOnClickListener(View.OnClickListener onClickListener) {
//        getBaseActivity().setCheckAllOnClick(onClickListener);
//    }
//
//    rotected void setToolbarTitle(String title) {
//        getBaseActivity().setToolbarTitle(title);
//    }
//
//    protected void setToolbarTitle(int resId) {
//        getBaseActivity().setToolbarTitle(getString(resId));
//    }
//
//    protected void setToolbarTitleVisibility(boolean visibility) {
//        getBaseActivity().setToolbarTitleVisibility(visibility);
//    }
//
//    protected void setToolbarTitle(String title, int colorId) {
//        getBaseActivity().setToolbarTitle(title, colorId);
//    }
//
//    protected void setToolbarTitleGravity(int gravity) {
//        getBaseActivity().setToolbarTitleGravity(gravity);
//    }

    public boolean onBackPressed() {
        return false;
    }

//    protected <C> C getComponent(Class<C> componentType) {
//        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
//    }

    protected void showKeyboard(EditText edit) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        edit.setSelection(edit.getText().length());
        edit.requestFocus();
    }

    protected boolean isTopOnStack() {
        return getActivity().getSupportFragmentManager().getBackStackEntryCount() == backstackPosition;
    }

//    protected abstract void setDisplayHomeButton();
//
//    protected abstract void settingToolbar();
//
//    public abstract String CLASS_TAG();
}
