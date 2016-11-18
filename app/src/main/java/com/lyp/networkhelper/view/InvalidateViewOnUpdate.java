package com.lyp.networkhelper.view;

import android.animation.ValueAnimator;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by lyp on 2016/11/18.
 */
public class InvalidateViewOnUpdate implements ValueAnimator.AnimatorUpdateListener {
    private final WeakReference<View> viewRef;

    public InvalidateViewOnUpdate(View view) {
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        final View view = viewRef.get();

        if (view == null) {
            return;
        }

        view.invalidate();
    }
}