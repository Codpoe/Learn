package com.app.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.learn.UI.Bezier2View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Bezier2ViewActivity extends AppCompatActivity {

    @BindView(R.id.bezier2_view_toolbar)
    Toolbar bezier2ViewToolbar;
    @BindView(R.id.bezier2_view)
    Bezier2View bezier2View;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.first_radio_btn)
    RadioButton firstRadioBtn;
    @BindView(R.id.second_radio_btn)
    RadioButton secondRadioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier2_view);
        ButterKnife.bind(this);

        bezier2ViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.first_radio_btn:
                        bezier2View.setIsPoint1(true);
                        break;
                    case R.id.second_radio_btn:
                        bezier2View.setIsPoint1(false);
                        break;
                }
            }
        });

    }


}
