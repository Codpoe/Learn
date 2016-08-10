package com.app.learn.Util;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Codpoe on 2016/5/10.
 */
public class ScalePageTransformer implements ViewPager.PageTransformer{
    @Override
    public void transformPage(View page, float position) {
        if(position < -1) { // position < -1
            page.setScaleX(0.8f);
            page.setScaleY(0.8f);
        }else if(position <= 1) { // -1 <= position <= 1
            if(position < 0) { // -1 <= position < 0
                page.setScaleX(1 + (1 - 0.8f) * (position));
                page.setScaleY(1 + (1 - 0.8f) * (position));
            }else { // 0 <= position <= 1
                page.setScaleX(1 + (1 - 0.8f) * (-position));
                page.setScaleY(1 + (1 - 0.8f) * (-position));
            }
        }else { // position > 1
            page.setScaleX(0.8f);
            page.setScaleY(0.8f);
        }
    }
}
