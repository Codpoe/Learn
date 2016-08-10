package com.app.learn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SharedActivity extends AppCompatActivity {

    private ImageView mSharedImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        mSharedImg = (ImageView) findViewById(R.id.shared_img);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
