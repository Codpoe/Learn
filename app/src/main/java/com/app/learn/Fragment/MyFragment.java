package com.app.learn.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.learn.R;

/**
 * Created by Codpoe on 2016/3/21.
 */
public class MyFragment extends Fragment {

    /**
     * 声明各种控件
     */
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_layout, container, false);

        /**
         * 获取各种控件的引用
         */


        /**
         * 设置EditText
         */


        /**
         * 设置下拉刷新事件
         */


        return view;
    }

}
