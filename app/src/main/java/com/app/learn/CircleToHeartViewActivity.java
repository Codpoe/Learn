package com.app.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleToHeartViewActivity extends AppCompatActivity {

    @BindView(R.id.circle_to_heart_view_toolbar)
    Toolbar circleToHeartViewToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_to_heart_view);
        ButterKnife.bind(this);

        circleToHeartViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
