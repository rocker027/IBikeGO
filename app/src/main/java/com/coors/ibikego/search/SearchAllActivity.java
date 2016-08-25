package com.coors.ibikego.search;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.blog.BlogGetImageTask;
import com.coors.ibikego.daovo.SqlSearchVO;
import com.coors.ibikego.travel.TravelGetImageTask;

import java.util.List;

public class SearchAllActivity extends AppCompatActivity {
    private static final String TAG = "SearchAll";
//    private List<SqlSearchVO> searchVOs = null;
    private RecyclerView recyclerView;
    String url = Common.URL + "blog/blogApp";
//    private String keyword
    private SearchAllAdapter allAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_all);
        initToolbar();
    }


    public void onClickSearchAll(View view) {
        EditText etKeyword = (EditText) findViewById(R.id.etSearchKeyWord);
        StringBuilder keyword = new StringBuilder("%"+etKeyword.getText().toString()+"%");

        recyclerView = (RecyclerView) findViewById(R.id.SAllRecycleView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.VERTICAL));

        String category_blog = "blog";
        String category_travel = "travel";
        try {
            List<SqlSearchVO> searchVOs =null,blogs = null, travels =null;

            searchVOs = new SearchAllTask().execute(url,keyword.toString(),category_blog).get();
            travels = new SearchAllTask().execute(url,keyword.toString(),category_travel).get();

            for(SqlSearchVO sqlSearchVO : travels){
                searchVOs.add(sqlSearchVO);
            }

            if(searchVOs == null || searchVOs.isEmpty()){

                Common.showToast(this,"目前沒有查詢到資料");
            }else {
                allAdapter = new SearchAllAdapter(this, searchVOs);
                recyclerView.setAdapter(allAdapter);
                allAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private class SearchAllAdapter extends RecyclerView.Adapter<SearchAllAdapter.ViewHolder> {
        private Context context;
        private List<SqlSearchVO> list;
        private LayoutInflater inflater;

        public SearchAllAdapter(Context context, List<SqlSearchVO> list) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            TextView tvTime, tvTitle, tvTel,tvAdd;
            ImageView ivRecycleView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                tvTel = (TextView) itemView.findViewById(R.id.tvTel);
                tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
                ivRecycleView = (ImageView) itemView.findViewById(R.id.ivRecycleView);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = inflater.inflate(R.layout.travel_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final SqlSearchVO sqlSearchVO = list.get(position);
            Integer tra_no = sqlSearchVO.getTra_no();
            Integer blog_no = sqlSearchVO.getBlog_no();
            int imageSize = 250;

            //若取回的tra_no 不等於null，表示為景點資料表
            if(tra_no != null){
                //取回旅遊點圖片
                new TravelGetImageTask(viewHolder.ivRecycleView).execute(url, tra_no, imageSize);
                viewHolder.tvAdd.setVisibility(View.VISIBLE);
                viewHolder.tvTitle.setText(sqlSearchVO.getTra_name());
                viewHolder.tvTime.setText(sqlSearchVO.getTra_cre().toString());
                viewHolder.tvTel.setText(sqlSearchVO.getTra_tel());
                viewHolder.tvAdd.setText(sqlSearchVO.getTra_add());
                //點選cardview 轉頁
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(SearchAllAdapter.this, AttractionsDetailActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("travelVO", travelVO);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                });



                return;
            }
            //若取回blog_no 不等於null，表示為單車日至
            else if(blog_no != null){
                //取回單車日誌圖片
                new BlogGetImageTask(viewHolder.ivRecycleView).execute(url, blog_no, imageSize);
                viewHolder.tvTitle.setText(sqlSearchVO.getBlog_title());
                viewHolder.tvTime.setText("發表日期："+sqlSearchVO.getBlog_cre().toString());
                viewHolder.tvTel.setText("發表會員："+sqlSearchVO.getMem_name());
                viewHolder.tvAdd.setVisibility(View.GONE);

                //點選cardview 轉頁
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(SearchBlogActivity.this, BlogDetailActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("blog", blog);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                });
//

                return;
            }





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
