package com.app.learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.app.learn.UI.PieView;
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
    @BindView(R.id.pie_view)
    PieView mPieView;
    @BindView(R.id.test_1_btn)
    Button mTestBtn;
    @BindView(R.id.test_2_btn)
    Button test2Btn;
    @BindView(R.id.path_btn)
    Button mPathBtn;


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


    @OnClick({R.id.pie_view, R.id.test_1_btn, R.id.test_2_btn,R.id.path_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pie_view:
                break;
            case R.id.test_1_btn:
                PieData pieData1 = new PieData("0", 30);
                PieData pieData2 = new PieData("1", 80);
                PieData pieData3 = new PieData("2", 40);
                PieData pieData4 = new PieData("3", 60);
                mPieDataList.clear();
                mPieDataList.add(pieData1);
                mPieDataList.add(pieData2);
                mPieDataList.add(pieData3);
                mPieDataList.add(pieData4);
                mPieView.setPieDataList(mPieDataList);
                mPieView.setStartAngle(30);
                break;
            case R.id.test_2_btn:
                mPieView.setStartAngle(0);
                break;
            case R.id.path_btn:
                startActivity(new Intent(CircularAnimActivity.this, PathActivity.class));
                break;
        }
    }

}
