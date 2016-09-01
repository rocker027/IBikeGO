package com.coors.ibikego.member;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.bikemode.BikeTrackPolylinesActivity;
import com.coors.ibikego.daovo.MemberVO;
import com.coors.ibikego.daovo.RelationshipVO;
import com.coors.ibikego.daovo.RouteVO;
import com.coors.ibikego.daovo.SqlGroupMemVO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MemberFriendsActivity extends AppCompatActivity {
    private Integer mem_no;
    private List<RelationshipVO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_friends);
        mem_no = (Integer) getIntent().getExtras().getSerializable("mem_no");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.friend_recycleView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.VERTICAL));

        try {
            list =  new MemberGetFriendsTask().execute(mem_no).get();
            recyclerView.setAdapter(new RelationshipAdapter(this, list));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private class RelationshipAdapter extends RecyclerView.Adapter<RelationshipAdapter.ViewHolder> {
        private Context context;
        private List<RelationshipVO> relationshipVOs;
        private LayoutInflater inflater;

        public RelationshipAdapter(Context context, List<RelationshipVO> relationshipVOs) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.relationshipVOs = relationshipVOs;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            TextView tvTime, tvTitle,tvLatlng;
            ImageView ivRecycleView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                tvLatlng = (TextView) itemView.findViewById(R.id.tvLatlng);
                ivRecycleView = (ImageView) itemView.findViewById(R.id.ivRecycleView);
            }
        }

        @Override
        public int getItemCount() {
            return relationshipVOs.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = inflater.inflate(R.layout.friends_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final RelationshipVO relationshipVO = relationshipVOs.get(position);
            String url = Common.URL + "member/memberApp.do";
            MemberVO memberVO = null;
            Integer rel_mem_no = relationshipVO.getRel_mem_no();
            try {
                memberVO = new MemberGetOneTask().execute(url,rel_mem_no).get();
                new MemberGetImageTask(viewHolder.ivRecycleView).execute(url, rel_mem_no, 300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewHolder.tvTitle.setText("會員名稱 : "+memberVO.getMem_name());
            viewHolder.tvTime.setText("會員暱稱 : "+memberVO.getMem_nickname());
            viewHolder.tvLatlng.setText("會員Mail:"+memberVO.getMem_email());
//            String url = Common.URL + "member/memberApp.do";
//            Integer mem_no = sqlGroupMemVO.getMem_no();

            //點選cardview 轉頁
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(BikeTrackListActivity.this, BikeTrackPolylinesActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("routeVO", routeVO);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
        }

    }
}
