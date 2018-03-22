package com.example.itadmin.projectdapa.maps.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

    public class PopUpRelativeLayout extends RelativeLayout {

        private float yFraction = 0;

        public PopUpRelativeLayout(Context context) {
            super(context);
        }

        public PopUpRelativeLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public PopUpRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        private ViewTreeObserver.OnPreDrawListener preDrawListener = null;

        public void setYFraction(float fraction) {

            this.yFraction = fraction;

            if (getHeight() == 0) {
                if (preDrawListener == null) {
                    preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
                            setYFraction(yFraction);
                            return true;
                        }
                    };
                    getViewTreeObserver().addOnPreDrawListener(preDrawListener);
                }
                return;
            }

            float translationY = getHeight() * 2f;
            setTranslationY(translationY);
        }

        public float getYFraction() {
            return this.yFraction;
        }
    }
