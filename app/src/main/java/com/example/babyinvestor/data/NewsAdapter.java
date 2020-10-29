package com.example.babyinvestor.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.babyinvestor.R;
import com.example.babyinvestor.Utils;
import com.example.babyinvestor.data.model.news.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsItems;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public NewsAdapter(List<NewsItem> newsItems, Context context) {
        this.newsItems = newsItems;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsitem,parent,false);
        return new NewsViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holders, int position) {
        final NewsViewHolder holder = holders;
        NewsItem item = newsItems.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        holder.title.setText(item.getTitle());
        holder.summary.setText(item.getSummary());
        holder.publish_date.setText(Utils.DateFormat(item.getPublication_date()));
        holder.url = newsItems.get(position).getUrl();

       //holder.company.setText(item.getCompany().getCompany_name());
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,summary,company,publish_date;
        Button btShowmore;

        String url;
        //ProgressBar progressBar;
        ImageView imageView;
        OnItemClickListener onItemClickListener;

        public NewsViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            summary = itemView.findViewById(R.id.summary);
            company = itemView.findViewById(R.id.company);
            publish_date = itemView.findViewById(R.id.publish_date);

            imageView = itemView.findViewById(R.id.img);

            btShowmore=itemView.findViewById(R.id.btShowmore);
            summary = itemView.findViewById(R.id.summary);
            btShowmore.setOnClickListener(v -> {

                if (btShowmore.getText().toString().equalsIgnoreCase("Showmore..."))
                {
                    summary.setMaxLines(Integer.MAX_VALUE);//your TextView
                    btShowmore.setText("Showless");
                }
                else
                {
                    summary.setMaxLines(3);//your TextView
                    btShowmore.setText("Showmore...");
                }
            });
            //progressBar = itemView.findViewById(R.id.progress_load_photo);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }


}
