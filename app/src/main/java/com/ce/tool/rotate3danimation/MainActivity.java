package com.ce.tool.rotate3danimation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import com.ce.tool.rotate3danimation.util.Rotate3dAnimation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MainActivity extends Activity {

    private TextView mTV2Rotate;

    private volatile int mShowingStringId = R.string.default_text;
    private volatile int mShowingColorId = R.color.white;

    private static final int ROTATE_X = 390;
    private static final int ROTATE_Y = 333;
    private static final int ROTATE_XY = 590;

    @IntDef({ROTATE_X, ROTATE_Y, ROTATE_XY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RotateDirection {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ce.tool.rotate3danimation.R.layout.activity_main);

        mTV2Rotate = (TextView) findViewById(R.id.text_view_to_rotate);

        findViewById(R.id.rotate_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate3DTry(ROTATE_X);
            }
        });
        findViewById(R.id.rotate_y).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate3DTry(ROTATE_Y);
            }
        });
        findViewById(R.id.rotate_x_y).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate3DTry(ROTATE_XY);
            }
        });
    }

    private void rotate3DTry(@RotateDirection final int direction) {

        AnimationSet firstSet = new AnimationSet(true);

        final long dur = 1000;

        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 0, true);
        setRotateAnimDirection(rotate3dAnimation, direction);
        rotate3dAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                AnimationSet secondSet = new AnimationSet(true);
                Rotate3dAnimation rotate3dAnimationSecondPart = new Rotate3dAnimation(270, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 0, true);
                setRotateAnimDirection(rotate3dAnimationSecondPart, direction);

                secondSet.addAnimation(rotate3dAnimationSecondPart);
                secondSet.setInterpolator(new AccelerateInterpolator());

                secondSet.setDuration(dur >> 1);

                secondSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mTV2Rotate.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mShowingStringId = mShowingStringId == R.string.default_text ?
                                        R.string.rotate_text : R.string.default_text;
                                mShowingColorId = mShowingColorId == R.color.white ?
                                        R.color.green : R.color.white;
                                mTV2Rotate.setText(mShowingStringId);
                                mTV2Rotate.setTextColor(getResources().getColor(mShowingColorId));
                            }
                        }, 0);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        mTV2Rotate.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, 10);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                mTV2Rotate.startAnimation(secondSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        firstSet.addAnimation(rotate3dAnimation);
        firstSet.setDuration(dur >> 1);

        mTV2Rotate.startAnimation(firstSet);
    }

    private void setRotateAnimDirection(Rotate3dAnimation rotate3dAnimation, @RotateDirection int rotateAnimDirection) {
        switch (rotateAnimDirection) {
            case ROTATE_X:
                rotate3dAnimation.rotateX();
                break;
            case ROTATE_Y:
                rotate3dAnimation.rotateY();
                break;
            case ROTATE_XY:
                rotate3dAnimation.rotateXY();
                break;
        }
    }
}
