package com.coors.ibikego.member;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.BlogVO;
import com.coors.ibikego.Common;
import com.coors.ibikego.LoginStatusChkTask;
import com.coors.ibikego.R;
import com.coors.ibikego.blog.BlogDetailActivity;
import com.coors.ibikego.blog.BlogGetImageTask;
import com.coors.ibikego.search.BlogSearchAllTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MemberBlogManageActivity extends AppCompatActivity {
    private static final String TAG = "BlogManage";
    private List<BlogVO> blogList = null;
    private RecyclerView recyclerView;
    private SharedPreferences pref;
    String url = Common.URL + "blog/blogApp.do";
    public static final int FUNC_LOGIN = 1;

//    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_blog_manage);
        initToolbar();
        initGetDate();
    }

    private void initGetDate() {
        boolean isChkOk=false;
        pref = getSharedPreferences(Common.PREF_FILE,
                MODE_PRIVATE);
        String mem_acc = pref.getString("pref_acc", "");
        String mem_pw = pref.getString("pref_pw", "");
        boolean islogin = pref.getBoolean("login", false);
        //先確認偏好設定是否有登入過，假如有會在檢查一次目前的偏好設定檔內，帳號密碼是否跟DB一樣
        if(islogin){
            try {
                String accPwChk =  new LoginStatusChkTask().execute(url, mem_acc, mem_pw).get();
                isChkOk = Boolean.valueOf(accPwChk);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //假如跟DB不一樣導頁到登入頁面
            if(!isChkOk){
                startActivityForResult(new Intent(MemberBlogManageActivity.this,MemberLoginActivity.class),FUNC_LOGIN);
            }
        }
        //偏好設定檔登入為false，重新導頁到登入頁面

        else {
            startActivityForResult(new Intent(MemberBlogManageActivity.this,MemberLoginActivity.class),FUNC_LOGIN);
        }

        String mem_no = String.valueOf(pref.getInt("pref_memno", 0));
        recyclerView = (RecyclerView) findViewById(R.id.SBlogRecycleView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.VERTICAL));
        try {
            blogList = new MemberBlogManageTask().execute(url,mem_no).get();
            recyclerView.setAdapter(new SearchBlogAdapter(this, blogList));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private class SearchBlogAdapter extends RecyclerView.Adapter<SearchBlogAdapter.ViewHolder> {
        private Context context;
        private List<BlogVO> blogList;
        private LayoutInflater inflater;

        public SearchBlogAdapter(Context context, List<BlogVO> blogList) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.blogList = blogList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            TextView tvTime, tvTitle, tvMem_no;
            ImageView ivRecycleView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                tvMem_no = (TextView) itemView.findViewById(R.id.tvMem_no);
                ivRecycleView = (ImageView) itemView.findViewById(R.id.ivRecycleView);
            }
        }

        @Override
        public int getItemCount() {
            return blogList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = inflater.inflate(R.layout.blog_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final BlogVO blog = blogList.get(position);
            int blog_no = blog.getBlog_no();
            int imageSize = 250;
            new BlogGetImageTask(viewHolder.ivRecycleView).execute(url, blog_no, imageSize);
            viewHolder.tvTitle.setText(blog.getBlog_title());
            viewHolder.tvTime.setText(blog.getBlog_cre().toString());
            viewHolder.tvMem_no.setText(String.valueOf(blog.getMem_no()));
//            viewHolder.imageView.setImageResource(blog.getImageId());
            //點選cardview 轉頁
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MemberBlogManageActivity.this, BlogDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("blog", blog);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

    }


    //toolbar
    private void initToolbar() {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    //toolbar 返回鍵
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
