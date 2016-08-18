package com.app.learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.app.learn.model.PieData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircularAnimActivity extends AppCompatActivity {

    List<PieData> mPieDataList = new ArrayList<PieData>();

    @BindView(R.id.circular_anim_toolbar)
    Toolbar circularAnimToolbar;
    @BindView(R.id.pie_view_btn)
    Button mPieViewBtn;
    @BindView(R.id.path_btn)
    Button mPathBtn;
    @BindView(R.id.radar_view_btn)
    Button mRadarViewBtn;
    @BindView(R.id.bezier_view_btn)
    Button mBezierViewBtn;
    @BindView(R.id.bezier2_view_btn)
    Button mBezier2ViewBtn;
    @BindView(R.id.circle_to_heart_view_btn)
    Button mCircleToHeartViewBtn;
    @BindView(R.id.tai_ji_view_btn)
    Button mTaiJiViewBtn;
    @BindView(R.id.arrow_view_btn)
    Button mArrowViewBtn;
    @BindView(R.id.search_view_btn)
    Button mSearchViewBtn;
    @BindView(R.id.smiling_face_view_btn)
    Button mSmilingFaceViewBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_anim);
        ButterKnife.bind(this);

        // 设置Toolbar
        circularAnimToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @OnClick({
            R.id.pie_view_btn,
            R.id.path_btn,
            R.id.radar_view_btn,
            R.id.bezier_view_btn,
            R.id.bezier2_view_btn,
            R.id.circle_to_heart_view_btn,
            R.id.tai_ji_view_btn,
            R.id.arrow_view_btn,
            R.id.search_view_btn,
            R.id.smiling_face_view_btn
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pie_view_btn:
                startActivity(new Intent(CircularAnimActivity.this, PieViewActivity.class));
                break;
            case R.id.path_btn:
                startActivity(new Intent(CircularAnimActivity.this, PathActivity.class));
                break;
            case R.id.radar_view_btn:
                startActivity(new Intent(CircularAnimActivity.this, RadarViewActivity.class));
                break;
            case R.id.bezier_view_btn:
                startActivity(new Intent(CircularAnimActivity.this, BezierViewActivity.class));
                break;
            case R.id.bezier2_view_btn:
                startActivity(new Intent(CircularAnimActivity.this, Bezier2ViewActivity.class));
                break;
            case R.id.circle_to_heart_view_btn:
                startActivity(new Intent(CircularAnimActivity.this, CircleToHeartViewActivity.class));
                break;
            case R.id.tai_ji_view_btn:
                startActivity(new Intent(CircularAnimActivity.this, TaiJiViewActivity.class));
                break;
            case R.id.arrow_view_btn:
                startActivity(new Intent(CircularAnimActivity.this, ArrowViewActivity.class));
                break;
            case R.id.search_view_btn:
                startActivity(new Intent(CircularAnimActivity.this, SearchViewActivity.class));
                break;
            case R.id.smiling_face_view_btn:
                startActivity(new Intent(CircularAnimActivity.this, SmilingFaceViewActivity.class));
        }
    }

}
