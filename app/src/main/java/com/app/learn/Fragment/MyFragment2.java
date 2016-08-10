package com.app.learn.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.learn.HttpUtil.HttpMethods;
import com.app.learn.R;
import com.app.learn.Util.ScalePageTransformer;
import com.app.learn.model.ChinaCalendar;

import java.util.Calendar;

import rx.Subscriber;

/**
 * Created by Codpoe on 2016/3/21.
 */
public class MyFragment2 extends Fragment implements View.OnClickListener{

    /**
     * 声明各种控件
     */
    private View view;
    private ViewPager banner;
    private PagerAdapter mPagerAdapter;
    private TextView yiText;
    private TextView jiText;
    private Button refreshBtn;
    private ImageView[] mImageViews;

    // 图片资源
    int[] imgRes = {R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3,
                R.drawable.banner_4, R.drawable.banner_5};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_2_layout, container, false);

        //获取各种控件的引用
        findViews();

        mImageViews = new ImageView[imgRes.length + 2];
        mImageViews[0] = new ImageView(getContext());
        mImageViews[0].setScaleType(ImageView.ScaleType.FIT_CENTER);
        mImageViews[0].setClickable(true);
        mImageViews[0].setImageResource(imgRes[4]);
        mImageViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        mImageViews[6] = new ImageView(getContext());
        mImageViews[6].setScaleType(ImageView.ScaleType.FIT_CENTER);
        mImageViews[6].setClickable(true);
        mImageViews[6].setImageResource(imgRes[0]);
        mImageViews[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        for(int i = 1; i < mImageViews.length - 1; i ++) {
            mImageViews[i] = new ImageView(getContext());
            mImageViews[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            mImageViews[i].setClickable(true);
            mImageViews[i].setImageResource(imgRes[i - 1]);
            mImageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // 轮播 Banner
        // 设置 page 间隔
        banner.setPageMargin(10);
        banner.setOffscreenPageLimit(7);
        // 设置 adapter
        banner.setAdapter(mPagerAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                container.addView(mImageViews[position]);
                return mImageViews[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public int getCount() {
                return mImageViews.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        // 设置 page 切换特效
        banner.setPageTransformer(true, new ScalePageTransformer());
        // 设置 page change 监听
        banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int pos;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(getContext(), "" + position % imgRes.length, Toast.LENGTH_SHORT).show();
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if(pos == mImageViews.length - 1) {
                        banner.setCurrentItem(1, false);
                    }else if(pos == 0) {
                        banner.setCurrentItem(mImageViews.length - 2, false);
                    }
                }
            }
        });
        banner.setCurrentItem(1);

        // 点击以获取数据
        refreshBtn.setOnClickListener(this);

        return view;

    }

    //获取各种控件的引用
    public void findViews() {
        banner = (ViewPager) view.findViewById(R.id.banner);
        yiText = (TextView) view.findViewById(R.id.yi_text);
        jiText = (TextView) view.findViewById(R.id.ji_text);
        refreshBtn = (Button) view.findViewById(R.id.refresh_btn);
    }

    //设置点击时间
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh_btn:
                getChinaCalendar();
                break;
        }
    }

    //获取老黄历
    public void getChinaCalendar() {
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH);
        HttpMethods.getInstance().getChinaCalendar(new Subscriber<ChinaCalendar>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ChinaCalendar chinaCalendar) {
                yiText.setText(chinaCalendar.getResult().getYi());
                jiText.setText(chinaCalendar.getResult().getJi());
            }
        }, date);
    }
}
