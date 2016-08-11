package com.app.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BezierViewActivity extends AppCompatActivity {

    @BindView(R.id.bezier_view_toolbar)
    Toolbar bezierViewToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_view);
        ButterKnife.bind(this);

        bezierViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
