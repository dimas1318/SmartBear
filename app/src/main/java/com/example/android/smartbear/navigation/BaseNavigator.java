package com.example.android.smartbear.navigation;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.inputmethod.InputMethodManager;

import com.example.android.smartbear.R;

/**
 * Created by parsh on 02.01.2018.
 */

public abstract class BaseNavigator {

    private final static int FRAGMENT_START_DELAY = 100;

    private Handler mFragmentsHandler;
    protected Fragment mCurrentFragment;

    public <T extends Fragment> void startFragment(AppCompatActivity activity, int content_frame, final T fragment, boolean addToStack, String tag, boolean shouldBeAdded, FragmentAnimation animation, boolean withDelay) {
        final FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        if (animation != null) {
            transaction.setCustomAnimations(
                    animation.getInAnimation1(), animation.getInAnimation2(),
                    animation.getOutAnimation1(), animation.getOutAnimation2());
        }

        if (shouldBeAdded) {
            if (tag == null) {
                transaction.add(content_frame, fragment);
            } else {
                transaction.add(content_frame, fragment, tag);
            }
        } else {
            if (tag == null) {
                transaction.replace(content_frame, fragment);
            } else {
                transaction.replace(content_frame, fragment, tag);
            }
        }

        if (addToStack) {
            transaction.addToBackStack(tag);
        }

        // do with delay
        if (withDelay) {
            if (mFragmentsHandler == null) {
                mFragmentsHandler = new Handler();
            }
            hideKeyboard(activity);
            mFragmentsHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    transaction.commitAllowingStateLoss();

                    mCurrentFragment = fragment;
                }
            }, FRAGMENT_START_DELAY);
        } else {
            transaction.commitAllowingStateLoss();

            mCurrentFragment = fragment;
        }
    }

    public <T extends Fragment> void startFragment(AppCompatActivity activity, int content_frame, T fragment, boolean withDelay) {
        String tag = fragment.getClass().getSimpleName();
        startFragment(activity, content_frame, fragment, true, tag, false, null, withDelay);
    }

    public <T extends Fragment> void startFragment(AppCompatActivity activity, int content_frame, T fragment, boolean addToBackStack, boolean withDelay) {
        String tag = fragment.getClass().getSimpleName();
        startFragment(activity, content_frame, fragment, addToBackStack, tag, false, null, withDelay);
    }

    public <T extends Fragment> void startFragment(AppCompatActivity activity, int content_frame, T fragment, boolean addToBackStack, FragmentAnimation anim, boolean withDelay) {
        String tag = fragment.getClass().getSimpleName();
        startFragment(activity, content_frame, fragment, addToBackStack, tag, false, anim, withDelay);
    }

    public <T extends Fragment> void startFragment(AppCompatActivity activity, int content_frame, T fragment, FragmentAnimation anim, boolean withDelay) {
        String tag = fragment.getClass().getSimpleName();
        startFragment(activity, content_frame, fragment, true, tag, false, anim, withDelay);
    }

    public <T extends Fragment> void replaceFragment(AppCompatActivity activity, int content_frame, T fragment, boolean withDelay) {
        String tag = fragment.getClass().getSimpleName();
        startFragment(activity, content_frame, fragment, true, tag, false, null, withDelay);
    }

    public <T extends Fragment> void replaceFragment(AppCompatActivity activity, int content_frame, T fragment, boolean addToBackStack, boolean withDelay) {
        String tag = fragment.getClass().getSimpleName();
        startFragment(activity, content_frame, fragment, addToBackStack, tag, false, null, withDelay);
    }

    public <T extends Fragment> void replaceFragment(AppCompatActivity activity, int content_frame, T fragment, FragmentAnimation anim, boolean withDelay) {
        String tag = fragment.getClass().getSimpleName();
        startFragment(activity, content_frame, fragment, true, tag, false, anim, withDelay);
    }

    public <T extends Fragment> void addFragment(AppCompatActivity activity, int content_frame, T fragment, boolean withDelay) {
        String tag = fragment.getClass().getSimpleName();
        startFragment(activity, content_frame, fragment, true, tag, true, null, withDelay);
    }

    public <T extends Fragment> void addFragment(AppCompatActivity activity, int content_frame, T fragment, FragmentAnimation anim, boolean withDelay) {
        String tag = fragment.getClass().getSimpleName();
        startFragment(activity, content_frame, fragment, true, tag, true, anim, withDelay);
    }

    protected <T extends AppCompatDialogFragment> void showDialog(AppCompatActivity activity, T fragment, String tag) {
        fragment.show(activity.getSupportFragmentManager(), tag);
    }

    public void hideKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void removeFragmentsExceptLast(AppCompatActivity activity, int content_frame) {
        int entryCount = activity.getSupportFragmentManager().getBackStackEntryCount();
        if (entryCount > 1)
            for (int i = 1; i < entryCount; i++) {
                removeFragment(activity, getCurrentFragment(activity, content_frame));
            }
    }

    public void clearStack(AppCompatActivity activity, int content_frame) {
        int entryCount = activity.getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 1; i < entryCount; i++) {
            removeFragment(activity, getCurrentFragment(activity, content_frame));
        }
    }

    protected void removeFragmentsExceptTag(AppCompatActivity activity, int content_frame, String tag) {
        int entryCount = activity.getSupportFragmentManager().getBackStackEntryCount();
        if (entryCount > 1) {
            for (int i = 1; i < entryCount; i++) {
                if (!getCurrentFragment(activity, content_frame).getTag().equals(tag))
                    removeFragment(activity, getCurrentFragment(activity, content_frame));
            }
        }
    }

    protected void removeFragmentsWithTag(AppCompatActivity activity, int content_frame, String tag) {
        int entryCount = activity.getSupportFragmentManager().getBackStackEntryCount();
        if (entryCount > 1) {
            for (int i = 1; i < entryCount; i++) {
                if (getCurrentFragment(activity, content_frame).getTag().equals(tag)) {
                    removeFragment(activity, getCurrentFragment(activity, content_frame));
                }
            }
        }
    }

    public Fragment getFragmentByTag(AppCompatActivity activity, String tag) {
        return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    public Fragment getCurrentFragment(AppCompatActivity activity, int content_frame) {
        return activity.getSupportFragmentManager().findFragmentById(content_frame);
    }

    public void removeFragment(AppCompatActivity activity, Fragment fragment) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitNow();
        manager.popBackStack();
    }

    public void clearBackStack(AppCompatActivity activity) {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void popBackStack(AppCompatActivity activity) {
        activity.getSupportFragmentManager().popBackStack();
    }

    public enum FragmentAnimation {
        ANIMATION;

        public int getInAnimation1() {
            return R.anim.fragment_alpha_in_1;
        }

        public int getInAnimation2() {
            return R.anim.fragment_alpha_in_2;
        }

        public int getOutAnimation1() {
            return R.anim.fragment_alpha_out_1;
        }

        public int getOutAnimation2() {
            return R.anim.fragment_alpha_out_2;
        }
    }
}
