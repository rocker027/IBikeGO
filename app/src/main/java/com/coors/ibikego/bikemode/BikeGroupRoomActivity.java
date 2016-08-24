package com.coors.ibikego.bikemode;

import android.content.Context;
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
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.SqlGroupMemVO;
import com.coors.ibikego.member.MemberGetImageTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BikeGroupRoomActivity extends AppCompatActivity {
    private SharedPreferences pref;
//    private String key;
    private List<SqlGroupMemVO> groupMemVOList;
    private static final String TAG = "GroupMemList";
    private RecyclerView recyclerView;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_group_room);
        initToolbar();
        initGetDate();
    }
    private void initGetDate() {
        pref = getSharedPreferences(Common.PREF_FILE,
                MODE_PRIVATE);
        String key = pref.getString("pref_key","");
        try {
            groupMemVOList = new BikeGetGroupMemsTask().execute(key).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView = (RecyclerView) findViewById(R.id.TrackListRecycleView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.VERTICAL));
        try {
            recyclerView.setAdapter(new GroupMemsListAdapter(this, groupMemVOList));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void onClickAbort(View view) {
        String key = pref.getString("pref_key","");
        Integer groupdetails_no=null;
        SqlGroupMemVO sqlGroupMemVO = new SqlGroupMemVO();
        for(SqlGroupMemVO sql:groupMemVOList){
            if (key.equals(sql.getGroupbike_key())) {
                groupdetails_no = sql.getGroupdetails_no();
            }
        }

        try {
            new BikeGroupAbortTask().execute(groupdetails_no).get();
            pref.edit().remove("pref_key").apply();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GroupMemsListAdapter extends RecyclerView.Adapter<GroupMemsListAdapter.ViewHolder>{
        private Context context;
        private List<SqlGroupMemVO> groupMemVOList;
        private LayoutInflater inflater;
        public GroupMemsListAdapter(Context context, List<SqlGroupMemVO> groupMemVOList) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.groupMemVOList = groupMemVOList;
        }

        @Override
        public int getItemCount() {
            return groupMemVOList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = inflater.inflate(R.layout.bike_group_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final SqlGroupMemVO sqlGroupMemVO = groupMemVOList.get(position);
            viewHolder.tvTitle.setText(sqlGroupMemVO.getMem_name());
            viewHolder.tvTime.setText("最後更新時間:"+sqlGroupMemVO.getGroup_update_time());
            viewHolder.tvLatlng.setText("經緯度:"+sqlGroupMemVO.getGroup_lat()+","+sqlGroupMemVO.getGroup_lng());
            String url = Common.URL + "member/memberApp.do";
            Integer memno = pref.getInt("pref_memno", 0);
//            //按鈕
//            if(memno!=sqlGroupMemVO.getMem_no()){
//                viewHolder.button.setVisibility(View.GONE);
//            }
//            viewHolder.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        Integer groupdetails_no =sqlGroupMemVO.getGroupdetails_no();
//                        new BikeGroupAbortTask().execute(groupdetails_no).get();
//
//                            Toast.makeText(BikeGroupRoomActivity.this,"刪除成功",Toast.LENGTH_SHORT).show();
//                            groupMemVOList.remove(position);
//                            GroupMemsListAdapter.this.notifyDataSetChanged();
//                            pref.edit().remove("pref_key").apply();
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
            new MemberGetImageTask(viewHolder.ivRecycleView).execute(url, memno, 50);
            //退出連線

            //點選cardview 轉頁
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(BikeTrackListActivity.this, BikeTrackPolylinesActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("routeVO", routeVO);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                });
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            TextView tvTitle,tvLatlng,tvTime;
            ImageView ivRecycleView;
            BootstrapButton button;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                tvLatlng = (TextView) itemView.findViewById(R.id.tvLatlng);
//                button = (BootstrapButton) itemView.findViewById(R.id.btnGroupRemove) ;
                ivRecycleView = (ImageView) itemView.findViewById(R.id.TrackListRecycleView);
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
