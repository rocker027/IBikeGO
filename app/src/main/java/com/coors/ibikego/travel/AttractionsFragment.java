package com.coors.ibikego.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.TravelVO;

import java.util.List;

public class AttractionsFragment extends Fragment {
    private static final String TAG = "AttractionsFragment";
    private List<TravelVO> list;
    String url = Common.URL + "travel/travelApp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        list = null;
        String action = "searchAttractions";
        try {
            list = new TravelGetAllTask().execute(action).get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        View view = inflater.inflate(R.layout.travel_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(new AttractionsAdapter(inflater));
        return view;
    }


    private class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.ViewHolder> {
        private LayoutInflater inflater;
        public AttractionsAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.travel_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
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
                    Intent intent = new Intent(getActivity(), TravelDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("travelVO", travelVO);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

        }

        class ViewHolder extends RecyclerView.ViewHolder {
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
                ivRecycleView = (ImageView) itemView.findViewById(R.id.imageView);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}