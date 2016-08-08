package com.coors.ibikego.blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.BlogVO;
import com.coors.ibikego.Common;
import com.coors.ibikego.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.*;

public class BlogFragment extends Fragment {
    private static final String TAG = "BlogFragment";
    private List<BlogVO> blogList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        blogList = null;

        String url = Common.URL + "blog/blogApp.do";
        try {
            blogList = new BlogGetAllTask().execute(url).get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        View view = inflater.inflate(R.layout.blog_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(new BlogAdapter(inflater));
        return view;
    }

    private class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
        private LayoutInflater inflater;
        public BlogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.blog_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final BlogVO blog = blogList.get(position);
            String url = Common.URL + "blog/blogApp.do";
            int blog_no = blog.getBlog_no();
            int imageSize = 250;
            new BlogGetImageTask(viewHolder.imageView).execute(url, blog_no, imageSize);
            viewHolder.tvTitle.setText(blog.getBlog_title());
            viewHolder.tvTime.setText(blog.getBlog_cre().toString());
            viewHolder.tvMem_no.setText(String.valueOf(blog.getMem_no()));
//            viewHolder.imageView.setImageResource(blog.getImageId());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("blog", blog);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            TextView tvTime, tvTitle, tvMem_no;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                tvMem_no = (TextView) itemView.findViewById(R.id.tvMem_no);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
            }
        }

        @Override
        public int getItemCount() {
            return blogList.size();
        }
    }
}