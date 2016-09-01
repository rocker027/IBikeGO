package com.coors.ibikego;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.coors.ibikego.bikemode.BikeModeMainActivity;
import com.coors.ibikego.bikemode.BikeTrackActivity;
import com.coors.ibikego.blog.BlogFragment;
import com.coors.ibikego.blog.BlogInsertActivity;
import com.coors.ibikego.member.MemberCenterActivity;
import com.coors.ibikego.member.MemberLoginActivity;
import com.coors.ibikego.search.SearchAllActivity;
import com.coors.ibikego.travel.AttractionsFragment;
import com.coors.ibikego.travel.BreakFragment;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SpaceNavigationView spaceNavigationView;
    public static final int FUNC_LOGIN = 1;
    private final static String TAG = "chkLoginStatus";
    private String userName, userMail, userAcc,userPw;
    private Integer memno;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //取得偏好設定
        pref = getSharedPreferences(Common.PREF_FILE,
                MODE_PRIVATE);

        //底部導覽列
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("首頁", R.drawable.ic_home_while_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem("發文", R.drawable.ic_navi_blog_w_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem("搜尋", R.drawable.ic_open_search));
        spaceNavigationView.addSpaceItem(new SpaceItem("會員中心", R.drawable.ic_navi_member_w_24dp));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                boolean islogin = pref.getBoolean("login", false);
                if (islogin) {
                    startActivity(new Intent(MainActivity.this, BikeModeMainActivity.class));
                }
                else {
                    Intent intent = new Intent(MainActivity.this,MemberLoginActivity.class);
                    startActivityForResult(intent, FUNC_LOGIN);
                }
                startActivity(new Intent(MainActivity.this, BikeModeMainActivity.class));
                Toast.makeText(MainActivity.this, "onCentreButtonClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                //首頁
                if(itemIndex == 0){
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                }
                //發日誌
                if(itemIndex == 1){
                    startActivity(new Intent(MainActivity.this,BlogInsertActivity.class));
                }
                //搜尋
                if(itemIndex == 2){
                    startActivity(new Intent(MainActivity.this,SearchAllActivity.class));
                }
                //會員中心
                if(itemIndex == 3){
                    boolean islogin = pref.getBoolean("login", false);
                    if (islogin) {
                        startActivity(new Intent(MainActivity.this, MemberCenterActivity.class));
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this,MemberLoginActivity.class);
                        startActivityForResult(intent, FUNC_LOGIN);
                    }
                    startActivity(new Intent(MainActivity.this,MemberCenterActivity.class));
                }

                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FUNC_LOGIN){
            if(resultCode == RESULT_OK){

                //若登入完成，將登入頁面所寫入偏好設定檔的值讀出
                if(!pref.getString("pref_acc","").equals(""))
                {
                    userAcc = pref.getString("pref_acc", "");
                }
                if(!pref.getString("pref_pw","").equals(""))
                {
                    userPw = pref.getString("pref_pw", "");
                }
                if(!pref.getString("pref_name","").equals(""))
                {
                    userName = pref.getString("pref_name", "");
                }
                if(!pref.getString("pref_email","").equals(""))
                {
                    userMail = pref.getString("pref_mail", "");
                }
            }else {
                finish();
            }
        }
    }
}
