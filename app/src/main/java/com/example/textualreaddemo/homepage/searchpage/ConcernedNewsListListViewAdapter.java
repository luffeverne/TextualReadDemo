package com.example.textualreaddemo.homepage.searchpage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.NewsListData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConcernedNewsListListViewAdapter extends RecyclerView.Adapter<ConcernedNewsListListViewAdapter.LinearViewHolder> {

    private Context context;
    private List<NewsListData> mItems;

    public ConcernedNewsListListViewAdapter(Context context) {
        this.context = context;
    }

    public void setConcernedPeopleNews(List<NewsListData> mItems){
        this.mItems = mItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConcernedNewsListListViewAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_concerned_news_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView.setText(mItems.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "模拟新闻"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LinearViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.concerned_news_list_item_text);
        }
    }
}
