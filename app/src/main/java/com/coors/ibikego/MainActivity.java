package com.coors.ibikego;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.coors.ibikego.attractions.AttractionsFragment;
import com.coors.ibikego.blog.BlogFragment;
import com.coors.ibikego.breaks.BreakFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViewPager();
//        initDrawer();
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("toolbar");
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        List<Page> pageList;

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            pageList = new ArrayList<>();
            pageList.add(new Page(new BlogFragment(), "單車日誌"));
            pageList.add(new Page(new AttractionsFragment(), "景點"));
            pageList.add(new Page(new BreakFragment(), "休息點"));
        }

        @Override
        public Fragment getItem(int position) {
            return pageList.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageList.get(position).getTitle();
        }
    }

    //載入右上角OptionsMenu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    //載入右上角OptionsMenu，選擇時做的動作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {
            return true;
        }

        if (id == R.id.menu_setting) {
            return true;
        }
        //按下任意ActionBar選單時觸發
        // 如果ActionBarDrawerToggle的onOptionItemSelected回傳true，
        // 則處理App Icon 點擊事件
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }

        return super.onOptionsItemSelected(item);
    }


    //左邊列開合
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START) || drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.START);
            drawer.closeDrawer(GravityCompat.END);
        }else{
            super.onBackPressed();
        }
    }

    private void initDrawer()
    {
        //left Drawer


//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//
//            }
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//        };
//        mDrawerToggle.syncState();
//        drawer.setDrawerListener(mDrawerToggle);

//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(mDrawerToggle);
//        getActionBar().setDisplayHomeAsUpEnabled(true); //讓左上角的App Icon的左邊加上一個返回的圖示
//        getActionBar().setHomeButtonEnabled(true); //讓左上角的App Icon顯示
//        mDrawerToggle.syncState();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                return true;
            }
        });
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawer.setDrawerListener(mDrawerToggle);



        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawer.closeDrawers();
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.item_Blog:
//                        Common.showToast(MainActivity.this,"camera");
                        break;
                    case R.id.item_Attractions:
//                        Common.showToast(MainActivity.this,"gallery");
                        break;
                    case R.id.item_Break:
//                        Common.showToast(MainActivity.this,"sldesshow");
                        break;
                    case R.id.item_MemSetting:
//                        Common.showToast(MainActivity.this,"manage");
                        break;

                    case R.id.item_MemFriend:
//                        Common.showToast(MainActivity.this,"share");
                        break;
                    case R.id.item_Activity:
//                        Common.showToast(MainActivity.this,"send");
                        break;
                    case R.id.item_BikeMode:
//                        Common.showToast(MainActivity.this,"send");
                        break;
                    case R.id.item_logout:
//                        Common.showToast(MainActivity.this,"send");
                        break;
                }
                return true;
            }
        });


    }


}

