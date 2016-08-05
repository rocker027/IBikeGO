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


//     @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        List<BlogVO> blogList = null;
//        String url = Common.URL + "blog/blogApp.do";
//        try {
//            blogList = new BlogGetAllTask().execute(url).get();
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//
//        View view = inflater.inflate(R.layout.blog_fragment, container, false);
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(new IntroAdapter(inflater, blogList));
//        return view;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        blogList = null;

//        blogList = new ArrayList<>();
//        blogList.add(new BlogVO(6,10501, "Blog01", "this is test content!", java.sql.Date.valueOf("2016-07-21"),0,R.drawable.blog01));
//        blogList.add(new BlogVO(7,10502, "Blog02", "this is test content!", java.sql.Date.valueOf("2016-07-22"),0,R.drawable.blog02));
//        blogList.add(new BlogVO(8,10503, "Blog03", "this is test content!", java.sql.Date.valueOf("2016-07-23"),0,R.drawable.blog03));
//        blogList.add(new BlogVO(9,10502, "Blog02", "this is test content!", java.sql.Date.valueOf("2016-07-22"),0,R.drawable.blog04));
//        blogList.add(new BlogVO(10,10503, "Blog03", "this is test content!", java.sql.Date.valueOf("2016-07-23"),0,R.drawable.blog05));

        String url = Common.URL + "blog/blogApp.do";
        try {
            blogList = new BlogGetAllTask().execute(url).get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        View view = inflater.inflate(R.layout.attractions_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(new BlogAdapter(inflater));
        return view;
    }

    private class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
        private LayoutInflater inflater;
//        private List<BlogVO> blogList;
        public BlogAdapter(LayoutInflater inflater) {
//        public BlogAdapter(LayoutInflater inflater, List<BlogVO> blogList) {
            this.inflater = inflater;
//            this.blogList = blogList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTime, tvTitle, tvMem_no;
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
            View itemView = inflater.inflate(R.layout.blog_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final BlogVO blog = blogList.get(position);
            viewHolder.tvTitle.setText(blog.getBlog_title());
            viewHolder.tvTime.setText(blog.getBlog_cre().toString());
            viewHolder.tvMem_no.setText(String.valueOf(blog.getMem_no()));
            viewHolder.imageView.setImageResource(blog.getImageId());
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

        @Override
        public int getItemCount() {
            return blogList.size();
        }
    }
}