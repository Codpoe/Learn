package com.app.learn.Util;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Codpoe on 2016/5/10.
 */
public class AlphaPageTransformer implements ViewPager.PageTransformer {

    // 数据
    private float minAlpha = 0.2f;

    @Override
    public void transformPage(View page, float position) {
        if(position < -1) {
            page.setAlpha(minAlpha);
        }else if(position <= 1) {
            if(position < 0) {
                page.setAlpha(minAlpha + (1 - minAlpha) * (1 + position));
            }else {
                page.setAlpha(minAlpha + (1 - minAlpha) * (1 - position));
            }
        }else {
            page.setAlpha(minAlpha);
        }
    }
}
