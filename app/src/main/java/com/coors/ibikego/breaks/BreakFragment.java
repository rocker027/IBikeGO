package com.coors.ibikego.breaks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.R;
import com.coors.ibikego.TravelVO;

import java.util.ArrayList;
import java.util.List;

public class BreakFragment extends Fragment {
    private List<TravelVO> breakList;
    public BreakFragment() {
        breakList = new ArrayList<>();
        breakList.add(new TravelVO(111111, 222222, 333333, 444444));
        breakList.add(new TravelVO(111111, 222222, 333333, 444444));
        breakList.add(new TravelVO(111111, 222222, 333333, 444444));
        breakList.add(new TravelVO(111111, 222222, 333333, 444444));
        breakList.add(new TravelVO(111111, 222222, 333333, 444444));
        breakList.add(new TravelVO(111111, 222222, 333333, 444444));
        breakList.add(new TravelVO(111111, 222222, 333333, 444444));
        breakList.add(new TravelVO(111111, 222222, 333333, 444444));
        breakList.add(new TravelVO(111111, 222222, 333333, 444444));
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.break_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(new IntroAdapter(inflater));
        return view;
    }


    private class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.ViewHolder> {
        private LayoutInflater inflater;

        public IntroAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTime, tvTitle,tvMem_no;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                tvMem_no = (TextView) itemView.findViewById(R.id.tvMem_no);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.break_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final TravelVO breakPoint = breakList.get(position);
            viewHolder.tvTitle.setText(String.valueOf(breakPoint.getTra_no()));
            viewHolder.tvTime.setText(String.valueOf(breakPoint.getMem_no()));
            viewHolder.tvMem_no.setText(String.valueOf(breakPoint.getLoc_no()));
//            viewHolder.imageView.setImageResource(attractions.getImageId());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BreakDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("break", breakPoint);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return breakList.size();
        }
    }
}