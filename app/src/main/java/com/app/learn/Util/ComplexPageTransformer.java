package com.app.learn.Util;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Codpoe on 2016/5/10.
 */
public class ComplexPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        if(position < -1) { // position < -1
            page.setTranslationX(500);
            page.setScaleX(0.8f);
            page.setScaleY(0.8f);
            page.setAlpha(0.5f);
        }else if(position <= 1) { // -1 <= position <= 1
            if(position < 0) { // -1 <= position < 0
                page.setTranslationZ(5 * (1 + position));
                page.setTranslationX(500 * (-position));
                page.setScaleX(1 + (1 - 0.8f) * (position));
                page.setScaleY(1 + (1 - 0.8f) * (position));
                page.setAlpha(0.5f + (1 - 0.5f) * (1 + position));
            }else { // 0 <= position <= 1
                page.setTranslationZ(5 * (1 - position));
                page.setTranslationX(500 * (-position));
                page.setScaleX(1 + (1 - 0.8f) * (-position));
                page.setScaleY(1 + (1 - 0.8f) * (-position));
                page.setAlpha(0.5f + (1 - 0.5f) * (1 - position));
            }
        }else { // position > 1
            page.setTranslationX(-500);
            page.setScaleX(0.8f);
            page.setScaleY(0.8f);
            page.setAlpha(0.5f);
        }
    }
}
