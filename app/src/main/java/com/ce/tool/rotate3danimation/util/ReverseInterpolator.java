package com.ce.tool.rotate3danimation.util;

import android.view.animation.Interpolator;

/**
 * Created by KyleCe on 2016/4/28.
 *
 * @author: KyleCe
 */
public class ReverseInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float paramFloat) {
        return Math.abs(paramFloat -1f);
    }
}