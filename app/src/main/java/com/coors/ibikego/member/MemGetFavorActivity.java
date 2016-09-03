package com.coors.ibikego.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.SqlSearchVO;
import com.coors.ibikego.search.SearchAllTask;

import java.util.List;

public class MemGetFavorActivity extends AppCompatActivity {
    private static final String TAG = "SearchAll";
    private RecyclerView recyclerView;
    String url = Common.URL + "blog/blogApp";
//    private SearchAllAdapter allAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_get_favor);
//        initToolbar();
        initDate();
    }

    private void initDate() {
        recyclerView = (RecyclerView) findViewById(R.id.SAllRecycleView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.VERTICAL));






        String category_blog = "blog";
        String category_travel = "travel";
        try {
            List<SqlSearchVO> searchVOs =null,blogs = null, travels =null;

//            searchVOs = new SearchAllTask().execute(url,keyword.toString(),category_blog).get();
//            travels = new SearchAllTask().execute(url,keyword.toString(),category_travel).get();

            for(SqlSearchVO sqlSearchVO : travels){
                searchVOs.add(sqlSearchVO);
            }

            if(searchVOs == null || searchVOs.isEmpty()){

                Common.showToast(this,"目前沒有查詢到資料");
            }else {
//                allAdapter = new SearchAllAdapter(this, searchVOs);
//                recyclerView.setAdapter(allAdapter);
//                allAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
