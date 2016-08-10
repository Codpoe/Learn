package com.app.learn;


import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.learn.Adapter.MyAdapter;
import com.app.learn.Fragment.MyFragment;
import com.app.learn.Fragment.MyFragment2;
import com.app.learn.Service.LearnService;
import com.app.learn.broadcast.HelloBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

//import com.app.learn.Util.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    /**
     * 静态变量
     */
    private static final String HELLO_ACTION = "hello";

    /**
     * 声明各种控件
     */
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TabLayout tabLayout;
    private ViewPager pager;
    private DrawerLayout drawerLayout;
    private NavigationView mNavigationView;
    private HelloBroadcastReceiver mHelloBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_main);

        /**
         * 获取各种引用
         */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        pager = (ViewPager) findViewById(R.id.pager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mHelloBroadcastReceiver = new HelloBroadcastReceiver();

        /**
         * 注册广播
         */
        LocalBroadcastManager.getInstance(this).registerReceiver(mHelloBroadcastReceiver, new IntentFilter(HELLO_ACTION));

        /**
         * 设置 toolbar
         */

        toolbar.setTitle("test");
        toolbar.inflateMenu(R.menu.tool_bar_menu);      //设置 toolbar 上的菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() { //toolbar上菜单的监听事件
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search_btn:
                        // 创建服务，并且发送通知
                        startService(new Intent(MainActivity.this, LearnService.class));
                        Toast.makeText(MainActivity.this, "click search button", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.refresh_btn:
                        // 本地广播
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(new Intent(HELLO_ACTION));
                        Toast.makeText(MainActivity.this, "click refresh button", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_1:
                        // ContentProvider 查询联系人
                        if (Build.VERSION.SDK_INT >= 23) {
                            int checkCallPhonePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS);
                            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
                            }else{
                                Cursor cursor = null;
                                cursor = getContentResolver().query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                                if (cursor.moveToFirst()) {
                                    Toast.makeText(MainActivity.this,
                                            cursor.getString(cursor.getColumnIndex(
                                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                                                    + "\n"
                                                    + cursor.getString(cursor.getColumnIndex(
                                                    ContactsContract.CommonDataKinds.Phone.NUMBER)),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Cursor cursor = null;
                            cursor = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                            if (cursor.moveToNext()) {
                                Toast.makeText(MainActivity.this,
                                        cursor.getString(cursor.getColumnIndex(
                                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                                                + "\n"
                                                + cursor.getString(cursor.getColumnIndex(
                                                ContactsContract.CommonDataKinds.Phone.NUMBER)),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        Uri uri = Uri.parse("content://contacts/people");
                        Toast.makeText(MainActivity.this, "click item1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_item_2:
                        Toast.makeText(MainActivity.this, "click item2", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                refreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
//
//            }
//        });

        /**
         * 设置 tabLayout 和 viewpager 的联动
         */
        List<String> tabList = new ArrayList<>();
        tabList.add("Tab1");
        tabList.add("Tab2");

        List<Fragment> fragmentList = new ArrayList<>();
        MyFragment fragment_1 = new MyFragment();
        MyFragment2 fragment_2 = new MyFragment2();
        fragmentList.add(fragment_1);
        fragmentList.add(fragment_2);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), fragmentList, tabList);
        pager.setAdapter(myAdapter);
        tabLayout.setTabsFromPagerAdapter(myAdapter);
        tabLayout.setupWithViewPager(pager);

//        getSupportActionBar().setHomeButtonEnabled(true);   //设置返回键可用
        /**
         * 创建返回键，并实现监听
         */
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);


        /**
         * 对 NavigationView 实现监听
         */
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "click home", Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_location:
                        Toast.makeText(MainActivity.this, "click location", Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_favorite:
                        Toast.makeText(MainActivity.this, "click favorite", Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "click settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_more:
                        Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHelloBroadcastReceiver);
        super.onDestroy();
    }
}
