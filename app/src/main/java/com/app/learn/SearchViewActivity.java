package com.app.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.app.learn.UI.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchViewActivity extends AppCompatActivity {

    @BindView(R.id.search_view_toolbar)
    Toolbar mSearchViewToolbar;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.search_start_btn)
    Button mSearchStartBtn;
    @BindView(R.id.search_stop_btn)
    Button mSearchStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        ButterKnife.bind(this);

        mSearchViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.search_start_btn, R.id.search_stop_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_start_btn:
                mSearchView.start();
                break;
            case R.id.search_stop_btn:
                mSearchView.stop();
                break;
        }
    }
}
