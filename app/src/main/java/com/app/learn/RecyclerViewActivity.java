package com.app.learn;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.learn.Adapter.RvAdapter;
import com.app.learn.Util.CircularAnimUtil;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    /**
     * 声明各种控件
     */
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFloatBtn;
    private ImageView mSharedImg;

    /**
     * 数据
     */
    private List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        /**
         * 获取各种控件的引用
         */
        mToolbar = (Toolbar) findViewById(R.id.rv_tool_bar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.coolapsing_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mLayoutManager = new LinearLayoutManager(this);
        mFloatBtn = (FloatingActionButton) findViewById(R.id.float_btn);
//        mSharedImg = (ImageView) findViewById(R.id.rv_shared_img);


        /**
         * 设置 toolbar
         */
        mToolbar.inflateMenu(R.menu.tool_bar_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /**
         * 设置 RecyclerView
         */
        for(int i = 'A'; i <= 'z'; i ++) {
            datas.add("" + (char)i);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final RvAdapter mRvAdapter = new RvAdapter(datas);
        mRecyclerView.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(new RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "click item " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "long click " + position, Toast.LENGTH_SHORT).show();
                mRvAdapter.removeItem(position);
            }
        });

        // 设置 FloatingActionButton
        mFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(RecyclerViewActivity.this, SharedActivity.class);
//                ActivityOptions mActivityOptions = ActivityOptions.makeSceneTransitionAnimation(RecyclerViewActivity.this,
//                        mSharedImg, "test");
//                startActivity(intent, mActivityOptions.toBundle());

                CircularAnimUtil.startActivity(RecyclerViewActivity.this, CircularAnimActivity.class, v, R.color.colorAccent);
            }
        });

    }

}
