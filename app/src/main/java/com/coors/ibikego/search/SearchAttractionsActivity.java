package com.coors.ibikego.search;

import android.content.Context;
import android.content.Intent;
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

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.TravelVO;
import com.coors.ibikego.travel.TravelDetailActivity;
import com.coors.ibikego.travel.TravelGetImageTask;

import java.util.List;

public class SearchAttractionsActivity extends AppCompatActivity {
    private static final String TAG = "AttractionsSearch";
    private List<TravelVO> list = null;
    private RecyclerView recyclerView;
    String url = Common.URL + "travel/travelApp";
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_attractions);
        initToolbar();
        initGetDate();
    }

    private void initGetDate() {
        String action = "searchKeyAtt";
        keyword = (String) getIntent().getExtras().getString("keyword1");
        recyclerView = (RecyclerView) findViewById(R.id.TrackListRecycleView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.VERTICAL));
        try {
            list = new TravelSearchAllTask().execute(url,action,keyword).get();
            recyclerView.setAdapter(new SearchTravelAdapter(this, list));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private class SearchTravelAdapter extends RecyclerView.Adapter<SearchTravelAdapter.ViewHolder> {
        private Context context;
        private List<TravelVO> list;
        private LayoutInflater inflater;

        public SearchTravelAdapter(Context context, List<TravelVO> list) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            TextView tvTime, tvTitle,  tvTel,tvAdd;
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
            final TravelVO travelVO = list.get(position);
//            String url = Common.URL + "blog/blogApp.do";
            int tra_no = travelVO.getTra_no();
            int imageSize = 250;
            new TravelGetImageTask(viewHolder.ivRecycleView).execute(url, tra_no, imageSize);
            viewHolder.tvTitle.setText(travelVO.getTra_name());
            viewHolder.tvTime.setText(travelVO.getTra_cre().toString());
            viewHolder.tvTel.setText(travelVO.getTra_tel());
            viewHolder.tvAdd.setText(travelVO.getTra_add());
            //點選cardview 轉頁
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchAttractionsActivity.this, TravelDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("travelVO", travelVO);
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
