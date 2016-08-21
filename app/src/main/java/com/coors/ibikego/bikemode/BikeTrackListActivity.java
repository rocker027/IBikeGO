package com.coors.ibikego.bikemode;

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

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.RouteVO;

import java.util.List;

public class BikeTrackListActivity extends AppCompatActivity {
    private static final String TAG = "BikeList";
    private List<RouteVO> routeVOs = null;
    private RecyclerView recyclerView;
    String url = Common.URL + "route/routeApp";
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_track_list);
        initToolbar();
        initGetDate();
    }

    private void initGetDate() {
        pref = getSharedPreferences(Common.PREF_FILE,
                MODE_PRIVATE);
        Integer mem_no = pref.getInt("pref_memno", 0);

        recyclerView = (RecyclerView) findViewById(R.id.TrackListRecycleView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.VERTICAL));
        try {
            routeVOs = new BikeTrackListAllTask().execute(url,mem_no).get();
            recyclerView.setAdapter(new BickTrackListAdapter(this, routeVOs));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private class BickTrackListAdapter extends RecyclerView.Adapter<BickTrackListAdapter.ViewHolder> {
        private Context context;
        private List<RouteVO> routeVOs;
        private LayoutInflater inflater;

        public BickTrackListAdapter(Context context, List<RouteVO> routeVOs) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.routeVOs = routeVOs;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            TextView tvTime, tvTitle;
            ImageView ivRecycleView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                ivRecycleView = (ImageView) itemView.findViewById(R.id.TrackListRecycleView);
            }
        }

        @Override
        public int getItemCount() {
            return routeVOs.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = inflater.inflate(R.layout.bike_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final RouteVO routeVO = routeVOs.get(position);
            viewHolder.tvTitle.setText(routeVO.getRoute_name());
            viewHolder.tvTime.setText("路線建立日期："+routeVO.getRoute_cre().toString());
            //點選cardview 轉頁
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BikeTrackListActivity.this, BikeTrackPolylinesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("routeVO", routeVO);
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
