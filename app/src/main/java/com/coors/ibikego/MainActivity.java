package com.coors.ibikego;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coors.ibikego.attractions.AttractionsFragment;
import com.coors.ibikego.bikemode.BikeModeMainActivity;
import com.coors.ibikego.bikemode.GeocoderActivity;
import com.coors.ibikego.blog.BlogFragment;
import com.coors.ibikego.blog.BlogInsertActivity;
import com.coors.ibikego.breaks.BreakFragment;
import com.coors.ibikego.member.MemberBlogManageActivity;
import com.coors.ibikego.member.MemberGetImageTask;
import com.coors.ibikego.member.MemberLoginActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView view;
    private  Menu nav_Menu;
    private SharedPreferences pref;
    private TextView tvNav_UserName,tvNav_UserMail;
    private ImageView ivNav_UserPhoto;
    private String userName, userMail, userAcc,userPw;
    private Integer memno;
    public static final int FUNC_LOGIN = 1;
    private final static String TAG = "chkLoginStatus";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViewPager();
        initToolbar();
        initDrawer();
        askPermissions();


    }

    @Override
    protected void onStart() {
        super.onStart();
        initDrawer();

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // home icon will keep still without calling syncState()
        mDrawerToggle.syncState();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("toolbar");
        //將左側DrawerLayout 跟 toolbar 綁定
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //讓左上角的App Icon的左邊加上一個返回的圖示
        getSupportActionBar().setHomeButtonEnabled(true);   //讓左上角的App Icon顯示

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    //fab 新增單車日誌功能
    public void onCreateBlog(View view) {
        boolean islogin = pref.getBoolean("login", false);
        if (islogin) {
            startActivity(new Intent(this, BlogInsertActivity.class));
        }
        else {
            Intent intent = new Intent(MainActivity.this,MemberLoginActivity.class);
            startActivityForResult(intent, FUNC_LOGIN);
        }
    }

    //tablayout viewpager
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
            startActivity(new Intent(this,SearchActivity.class));
            return true;
        }

        if (id == R.id.menu_setting) {
            boolean login = pref.getBoolean("login", false);
            String status = String.valueOf(login);
            Log.d(TAG,status);
            return true;
        }
        //按下任意ActionBar選單時觸發
        // 如果ActionBarDrawerToggle的onOptionItemSelected回傳true，
        // 則處理App Icon 點擊事件
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //左邊列開合
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_setting);
        if (drawer.isDrawerOpen(GravityCompat.START) || drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.START);
            drawer.closeDrawer(GravityCompat.END);
        }else{
            super.onBackPressed();
        }
    }

    //登入後返回
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
                initDrawer();
            }else {
                finish();
            }
        }
    }

    private void initDrawer()
    {
        //左側使用者導覽列
        view = (NavigationView) findViewById(R.id.navigation_view);
        //取得navigate的menu
        nav_Menu = view.getMenu();
        //取得naviate header view 上的textView
        View headerLayout = view.getHeaderView(0);
        tvNav_UserName = (TextView) headerLayout.findViewById(R.id.tvNaviUserName);
        tvNav_UserMail = (TextView) headerLayout.findViewById(R.id.tvNaviUserEmail);
        ivNav_UserPhoto = (ImageView) headerLayout.findViewById(R.id.ivNaviUser);
        pref = getSharedPreferences(Common.PREF_FILE,
                MODE_PRIVATE);
        String url = Common.URL + "member/memberApp.do";

        // 從偏好設定檔中取得登入狀態來決定是否顯示「登出」按鈕
        boolean islogin = pref.getBoolean("login", false);
        if (islogin) {
            logInVisible();

            new MemberGetImageTask(ivNav_UserPhoto).execute(url, memno, 50);
        }else {
//            logOutUnVisible();
//            nav_Menu.findItem(R.id.item_login).setVisible(true);
//            nav_Menu.findItem(R.id.item_logout).setVisible(false);
//            nav_Menu.findItem(R.id.item_MemSetting).setVisible(false);
//            nav_Menu.findItem(R.id.item_MemFriend).setVisible(false);
//            nav_Menu.findItem(R.id.item_BikeMode).setVisible(false);
//            nav_Menu.findItem(R.id.item_MemberMenu).setVisible(false);


        }

        //navigateView item 監聽器
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawer.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.item_Blog:
                        Common.showToast(MainActivity.this,"camera");
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

                    case R.id.item_MemBlog:

                        boolean islogin = pref.getBoolean("login", false);
                        if (islogin) {
//                            String resule = new LoginStatusChkTask().execute(url, mem_acc, mem_pw).get();
//                            re
//                            if(resu)
                            startActivity(new Intent(MainActivity.this, MemberBlogManageActivity.class));
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this,MemberLoginActivity.class);
                            startActivityForResult(intent, FUNC_LOGIN);
                        }
//                        Common.showToast(MainActivity.this,"manage");
                        break;

                    case R.id.item_MemFriend:
//                        initDrawer();
                        Common.showToast(MainActivity.this,"item_MemFriend");
                        break;
//                    case R.id.item_Activity:
//                        Common.showToast(MainActivity.this,"send");
//                        break;
                    case R.id.item_BikeMode:
                        startActivity(new Intent(MainActivity.this,BikeModeMainActivity.class));

                        Common.showToast(MainActivity.this,"item_BikeMode");
                        break;
                    case R.id.item_logout:
                        pref.edit().putBoolean("login", false)
                                .remove("pref_acc")
                                .remove("pref_pw")
                                .remove("pref_name")
                                .remove("pref_email")
                                .remove("pref_key")
                                .remove("pref_memno")
                                .apply();
                        logOutUnVisible();


                        break;
                    case R.id.item_login:
                        Intent intent = new Intent(MainActivity.this,MemberLoginActivity.class);
                        startActivityForResult(intent, FUNC_LOGIN);
//                        Common.animSlideAnimRight(MainActivity.this);//                        (new Intent(MainActivity.this, MemberLoginActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    //登入後，顯示會員可以使用的功能項目
    private void logInVisible() {
        nav_Menu.findItem(R.id.item_login).setVisible(false);
        nav_Menu.findItem(R.id.item_logout).setVisible(true);
        nav_Menu.findItem(R.id.item_MemSetting).setVisible(true);
        nav_Menu.findItem(R.id.item_MemFriend).setVisible(true);
        nav_Menu.findItem(R.id.item_BikeMode).setVisible(true);
        nav_Menu.findItem(R.id.item_MemberMenu).setVisible(true);
        nav_Menu.findItem(R.id.item_MemBlog).setVisible(true);

        userName =pref.getString("pref_name","");
        userMail =pref.getString("pref_mail","");
        memno =pref.getInt("pref_memno",0);

        tvNav_UserName.setText(userName);
        tvNav_UserMail.setText(userMail);
    }

    //登出後，顯示會員可以使用的功能項目隱藏，並修改會員大頭貼的照片跟名稱
    private void logOutUnVisible() {
        nav_Menu.findItem(R.id.item_logout).setVisible(false);
        nav_Menu.findItem(R.id.item_login).setVisible(true);
        nav_Menu.findItem(R.id.item_MemSetting).setVisible(false);
        nav_Menu.findItem(R.id.item_MemFriend).setVisible(false);
        nav_Menu.findItem(R.id.item_BikeMode).setVisible(false);
        nav_Menu.findItem(R.id.item_MemberMenu).setVisible(false);
        tvNav_UserName.setText("未登入");
        tvNav_UserMail.setText("");
        ivNav_UserPhoto.setImageResource(R.mipmap.ic_launcher);
    }

    //安裝的權限詢問
    private static final int REQ_PERMISSIONS = 0;

    // New Permission see Appendix A
    private void askPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
//                ,Manifest.permission.CALL_PHONE
        };

        Set<String> permissionsRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if (!permissionsRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    REQ_PERMISSIONS);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSIONS:
                String text = "";
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        text += permissions[i] + "\n";
                    }
                }
                if (!text.isEmpty()) {
                    text += getString(R.string.text_NotGranted);
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

