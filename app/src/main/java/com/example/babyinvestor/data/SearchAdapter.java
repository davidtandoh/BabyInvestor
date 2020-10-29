package com.example.babyinvestor.data;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyinvestor.R;
import com.example.babyinvestor.data.model.search.ResultItem;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.StockViewHolder>{
    private List<ResultItem> results;
    private Context context;
    private SearchAdapter.OnItemClickListener onItemClickListener;


    public SearchAdapter(List<ResultItem> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new SearchAdapter.StockViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.StockViewHolder holders, int position) {
        final SearchAdapter.StockViewHolder holder = holders;
        ResultItem item = results.get(position);

        holder.ticker.setText(item.getSymbol());
        holder.name.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setOnItemClickListener(SearchAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }


    public class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ticker,name;

        SearchAdapter.OnItemClickListener onItemClickListener;

        public StockViewHolder(@NonNull View itemView, SearchAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            ticker = itemView.findViewById(R.id.ticker);
            name = itemView.findViewById(R.id.company_name);

            this.onItemClickListener = onItemClickListener;

        }

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
