package com.coors.ibikego.blog;

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
import android.widget.Toast;

import com.coors.ibikego.daovo.BlogVO;
import com.coors.ibikego.Common;
import com.coors.ibikego.daovo.MemberVO;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.SqlBlogVO;
import com.coors.ibikego.member.MemberGetOneTask;

import java.util.List;

public class BlogFragment extends Fragment {
    private static final String TAG = "BlogFragment";
    BlogAdapter blogAdapter;
    private List<SqlBlogVO> blogList;

    @Override
    public void onStart() {
        super.onStart();
        if(blogList != null) {
            blogAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        blogList = null;

        String url = Common.URL + "blog/blogApp";
        try {
            blogList = new BlogGetAllTask().execute(url).get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        if(blogList == null || blogList.isEmpty()){
            Toast.makeText(getContext(),"與資料庫連線失敗",Toast.LENGTH_SHORT).show();
        }

        View view = inflater.inflate(R.layout.blog_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        blogAdapter = new BlogAdapter(inflater);
        recyclerView.setAdapter(blogAdapter);
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
            final SqlBlogVO blog = blogList.get(position);
            String url = Common.URL + "blog/blogApp";
            int blog_no = blog.getBlog_no();
//            MemberVO memberVO=null;
            String mem_pk = String.valueOf(blog.getMem_no());
//            try {
//                memberVO = new MemberGetOneTask().execute(url,mem_pk).get();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            int imageSize = 250;
            new BlogGetImageTask(viewHolder.ivRecycleView).execute(url, blog_no, imageSize);
            viewHolder.tvTitle.setText(blog.getBlog_title());
            viewHolder.tvTime.setText(blog.getBlog_cre().toString());
            viewHolder.tvMem_no.setText(blog.getMem_name());
//            viewHolder.imageView.setImageResource(blog.getImageId());
            //點選cardview 轉頁
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
            ImageView ivRecycleView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                tvMem_no = (TextView) itemView.findViewById(R.id.tvMem_no);
                ivRecycleView = (ImageView) itemView.findViewById(R.id.ivRecycleView);
            }
        }

        @Override
        public int getItemCount() {
            return blogList.size();
        }
    }
}