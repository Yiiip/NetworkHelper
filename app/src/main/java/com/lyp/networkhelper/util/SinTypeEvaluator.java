package com.lyp.networkhelper.util;

import android.animation.TypeEvaluator;

/**
 * Created by lyp on 2016/11/18.
 */
public class SinTypeEvaluator implements TypeEvaluator<Number> {
    @Override
    public Number evaluate(float fraction, Number from, Number to) {
        return Math.max(0, Math.sin(fraction * Math.PI * 2)) * (to.floatValue() - from.floatValue());
    }
}