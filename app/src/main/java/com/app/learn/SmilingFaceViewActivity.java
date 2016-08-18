package com.app.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.app.learn.UI.SmilingFaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmilingFaceViewActivity extends AppCompatActivity {

    @BindView(R.id.smiling_face_view_toolbar)
    Toolbar mSmilingFaceViewToolbar;
    @BindView(R.id.smiling_face_view)
    SmilingFaceView mSmilingFaceView;
    @BindView(R.id.smiling_start_btn)
    Button mSmilingStartBtn;
    @BindView(R.id.smiling_stop_btn)
    Button mSmilingStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smiling_face_view);
        ButterKnife.bind(this);

        mSmilingFaceViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.smiling_start_btn, R.id.smiling_stop_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.smiling_start_btn:
                mSmilingFaceView.start();
                break;
            case R.id.smiling_stop_btn:
                mSmilingFaceView.stop();
                break;
        }
    }
}
