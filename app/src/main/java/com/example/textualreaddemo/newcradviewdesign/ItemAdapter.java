package com.example.textualreaddemo.newcradviewdesign;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.PictureBean;

import java.util.List;

/**
 * RecylerView Adapter<P/>
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<PictureBean> dataList;

    public void setDataList(List<PictureBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<PictureBean> getDataList() {
        return dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 创建列表子视图和ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getId(),null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(null != dataList && null != dataList.get(position)){
            setData(holder , position);
        }
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        setData(holder , position);
    }

    /**
     * 用于实现视图内容的实例化和赋值
     */
    public void setData(ViewHolder holder , int pos){
        if(null != getDataList().get(pos)){
            //实例化图片显示
            View view = holder.itemView;
            ImageView imageView = holder.imageView;
            //为专辑图片设置倒影显示
            new BitmapUtil().reflectionBitmap(getDataList().get(pos).getPictureByte() , imageView , new SimpleTarget<Bitmap>(){
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    imageView.setImageBitmap(resource);
                }
            });
//            imageView.setImageBitmap(getDataList().get(pos).getBitmap());
            TextView textView = holder.textView;
            textView.setText(getDataList().get(pos).getId());
        }
    };

    /**
     * 指定子视图的布局id
     * @return
     */
    public int getId(){
        return R.layout.item_picture;
    };

    /**
     * DIFF可用于分析列表数据的变化，避免全部子视图的刷新，可节省子视图更新时的CPU消耗
     */
    private static DiffUtil.ItemCallback DIFF_CALLBACK = new DiffUtil.ItemCallback<PictureBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull PictureBean oldItem, @NonNull PictureBean newItem) {
            return areItemsTheSame(oldItem,newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull PictureBean oldItem, @NonNull PictureBean newItem) {
            return areContentsTheSame(oldItem,newItem);
        }
    };

    /**
     * 子视图的Holder，由标题和图片构成
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            textView = itemView.findViewById(R.id.textview);
        }
    }

    /**
     * 注册监听的接口
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 定义点击事件的监听
     */
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }
}
