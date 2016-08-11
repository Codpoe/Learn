package com.app.learn;

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

public class PieViewActivity extends AppCompatActivity {

    @BindView(R.id.pie_view_toolbar)
    Toolbar mPieViewToolbar;
    @BindView(R.id.pie_view)
    PieView mPieView;
    @BindView(R.id.set_data_btn)
    Button mSetDataBtn;
    @BindView(R.id.set_angle_btn)
    Button mSetAngleBtn;

    List<PieData> mPieDataList = new ArrayList<PieData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_view);
        ButterKnife.bind(this);

        mPieViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.set_data_btn, R.id.set_angle_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_data_btn:
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
            case R.id.set_angle_btn:
                mPieView.setStartAngle(0);
                break;
        }
    }
}
