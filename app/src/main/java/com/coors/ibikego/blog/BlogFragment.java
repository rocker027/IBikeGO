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

import com.coors.ibikego.daovo.BlogVO;
import com.coors.ibikego.Common;
import com.coors.ibikego.daovo.MemberVO;
import com.coors.ibikego.R;
import com.coors.ibikego.member.MemberGetOneTask;

import java.util.List;

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
            MemberVO memberVO=null;
            String mem_pk = String.valueOf(blog.getMem_no());
            try {
                memberVO = new MemberGetOneTask().execute(url,mem_pk).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            int imageSize = 250;
            new BlogGetImageTask(viewHolder.ivRecycleView).execute(url, blog_no, imageSize);
            viewHolder.tvTitle.setText(blog.getBlog_title());
            viewHolder.tvTime.setText(blog.getBlog_cre().toString());
            viewHolder.tvMem_no.setText(memberVO.getMem_name());
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

            //長按cardview跳出選單，檢舉、收藏
//            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    PopupMenu popupMenu = new PopupMenu(, view, Gravity.END);
//                    popupMenu.inflate(R.menu.popup_menu);
//                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId()) {
//                                case R.id.insert:
//                                    Intent insertIntent = new Intent(SpotListActivity.this, SpotInsertActivity.class);
//                                    startActivity(insertIntent);
//                                    break;
//                                case R.id.update:
//                                    Intent updateIntent = new Intent(SpotListActivity.this,
//                                            SpotUpdateActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("spot", spot);
//                                    updateIntent.putExtras(bundle);
//                                    startActivity(updateIntent);
//                                    break;
//                                case R.id.delete:
//                                    if (Common.networkConnected(SpotListActivity.this)) {
//                                        String url = Common.URL + "SpotServlet";
//                                        String action = "spotDelete";
//                                        int count = 0;
//                                        try {
//                                            count = new SpotUpdateTask().execute(url, action, spot, null).get();
//                                        } catch (Exception e) {
//                                            Log.e(TAG, e.toString());
//                                        }
//                                        if (count == 0) {
//                                            Common.showToast(SpotListActivity.this, R.string.msg_DeleteFail);
//                                        } else {
//                                            spots.remove(position);
//                                            SpotsRecyclerViewAdapter.this.notifyDataSetChanged();
//                                            Common.showToast(SpotListActivity.this, R.string.msg_DeleteSuccess);
//                                        }
//                                    } else {
//                                        Common.showToast(SpotListActivity.this, R.string.msg_NoNetwork);
//                                    }
//                            }
//                            return true;
//                        }
//                    });
//                    popupMenu.show();
//                    return true;
//                }
//            });
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