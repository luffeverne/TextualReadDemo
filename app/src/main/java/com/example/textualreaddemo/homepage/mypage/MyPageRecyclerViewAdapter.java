package com.example.textualreaddemo.homepage.mypage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textualreaddemo.R;

import java.util.ArrayList;
import java.util.List;

public class MyPageRecyclerViewAdapter extends RecyclerView.Adapter<MyPageRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<String> newsItemList = new ArrayList<>();

    public MyPageRecyclerViewAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<String> newsItemList) {
        this.newsItemList = newsItemList;
    }

    @NonNull
    @Override
    public MyPageRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.mypagerecyclerview_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPageRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(newsItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItemList == null ? 0 : newsItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.myPageRecyclerView_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerItemClickListener != null) {
                        onRecyclerItemClickListener.OnRecyclerItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener;
    public void setOnRecyclerItemClickListen(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }
    public interface OnRecyclerItemClickListener {
        void OnRecyclerItemClick(int position);
    }
}
