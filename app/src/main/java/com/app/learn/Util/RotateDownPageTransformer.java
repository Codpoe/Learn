package com.app.learn.Util;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Codpoe on 2016/5/10.
 */
public class RotateDownPageTransformer implements ViewPager.PageTransformer {

    // 数据
    private float minAlpha = 0.5f;

    @Override
    public void transformPage(View page, float position) {
        if(position < -1) { // position < -1
            page.setPivotX(page.getWidth() * (0.5f + 0.5f * (-position)));
            page.setPivotY(page.getWidth());
            page.setRotation(15 * position);
        }else if(position <= 1) { // -1 <= position <= 1
            if(position < 0) { // -1 <= position < 0
                page.setPivotX(page.getWidth() * (0.5f + 0.5f * (-position)));
                page.setPivotY(page.getWidth());
                page.setRotation(15 * position);
            }else { // 0 <= position <= 1
                page.setPivotX(page.getWidth() * (0.5f + 0.5f * (-position)));
                page.setPivotY(page.getWidth());
                page.setRotation(15 * position);
            }
        }else { // position > 1
            page.setPivotX(page.getWidth() * (0.5f + 0.5f * (-position)));
            page.setPivotY(page.getWidth());
            page.setRotation(15 * position);
        }
    }
}
