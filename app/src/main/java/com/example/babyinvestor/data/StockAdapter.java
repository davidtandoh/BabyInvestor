package com.example.babyinvestor.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyinvestor.R;
import com.example.babyinvestor.data.model.historicaldata.StockItem;

import java.util.List;

public class StockAdapter  extends RecyclerView.Adapter<StockAdapter.StockViewHolder>{

    private List<StockItem> stockItems;
    private Context context;
    private NewsAdapter.OnItemClickListener onItemClickListener;


    public StockAdapter(List<StockItem> stockItems, Context context) {
        this.stockItems = stockItems;
        this.context = context;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stocksitem,parent,false);
        return new StockAdapter.StockViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holders, int position) {
        final StockAdapter.StockViewHolder holder = holders;
        StockItem item = stockItems.get(position);

        holder.ticker.setText(item.getSecurity().getTicker());
        holder.name.setText(item.getSecurity().getName());
        holder.price.setText(Double.toString(item.getOpen_price()));

    }

    @Override
    public int getItemCount() {
        return stockItems.size();
    }

    public void setOnItemClickListener(NewsAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }


    public class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ticker,name,currency,price;

        NewsAdapter.OnItemClickListener onItemClickListener;

        public StockViewHolder(@NonNull View itemView, NewsAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            ticker = itemView.findViewById(R.id.ticker);
            name = itemView.findViewById(R.id.company_name);
            price = itemView.findViewById(R.id.open_price);

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
