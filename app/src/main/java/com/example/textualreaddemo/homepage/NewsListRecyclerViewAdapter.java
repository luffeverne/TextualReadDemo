package com.example.textualreaddemo.homepage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.NewsListData;

import java.util.List;

/**
 * 新闻简讯列表recycleview适配器
 * 更新时间：2022-6-8 21：30
 * @author houdeng
 */
public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<NewsListData> mItems;
    private MyOnItemClickListener mOnItemClickListener;

    private final int VIEW_TYPE_PICTURE_TITLE = 1;
    private final int VIEW_TYPE_NO_PICTURE_TITLE = 2;

    public NewsListRecyclerViewAdapter(Activity context) {
        this.context = context;
    }

    public void setData(List<NewsListData> items){
        mItems = items;
        notifyDataSetChanged();
    }

    /**
     * 获取新闻样式
     * @return 标志
     */
    @Override
    public int getItemViewType(int position) {
        if (mItems != null && mItems.get(position).getImgList() != null
                && mItems.get(position).getImgList().size() > 0){
            return VIEW_TYPE_PICTURE_TITLE;
        }
        return VIEW_TYPE_NO_PICTURE_TITLE;
    }

    /**
     * 根据样式设置布局
     * @param viewType 上一个方法返回的标志
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_NO_PICTURE_TITLE){
            view = LayoutInflater.from(context).inflate(R.layout.layout_news_list_no_picture_title_item,parent,false);
            return new TitleViewHolder(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.layout_news_list_picture_title_item,parent,false);
            return new PictureTitleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PictureTitleViewHolder){
            ((PictureTitleViewHolder) holder).itemTitle.setText(mItems.get(position).getTitle());
            Glide.with(holder.itemView.getContext())
                    .load(mItems.get(position).getImgList().get(0))
                    .into(((PictureTitleViewHolder) holder).imageView);
            ((PictureTitleViewHolder) holder).itemText.setText(mItems.get(position).getDigest());
            ((PictureTitleViewHolder) holder).lastTitle.setText(mItems.get(position).getTitle());
        }else {
            ((TitleViewHolder)holder).itemText.setText(mItems.get(position).getDigest());
            ((TitleViewHolder)holder).itemTitle.setText(mItems.get(position).getTitle());
            ((TitleViewHolder) holder).lastTitle.setText(mItems.get(position).getTitle());
        }
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(holder.itemView,position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    /**
     * 有图片时ViewHolder
     */
    private class PictureTitleViewHolder extends RecyclerView.ViewHolder{

        public TextView itemTitle;
        public TextView itemText;
        public ImageView imageView;
        public TextView lastTitle;

        public PictureTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.news_list_title);
            imageView = itemView.findViewById(R.id.news_list_image);
            itemText = itemView.findViewById(R.id.news_list_text);
        }
    }

    /**
     * 无图片时ViewHolder
     */
    private class TitleViewHolder extends RecyclerView.ViewHolder{

        public TextView itemTitle;
        public TextView itemText;
        public TextView lastTitle;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.news_list_title);
            itemText = itemView.findViewById(R.id.news_list_text);
        }
    }

    /**
     * 设置点击事件监听接口
     */
    public interface MyOnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(MyOnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
