package com.moviesapp.amrelmasry.popular_movies_app.utilities;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by AmrELmasry on 10/5/2015.
 */

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                        nestedScrollAxes);
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxUnconsumed, dyUnconsumed, dxConsumed,
                dyConsumed);

//        original behavior
//        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
//            child.hide();
//        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
//            child.show();
//        }


        if (dyConsumed < 0) {
            child.show();
            Log.i("SCROLL", String.valueOf(dyConsumed));
        } else if (dyConsumed > 0) {
            child.hide();
            Log.i("SCROLL", String.valueOf(dyConsumed));

        }
    }

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

}