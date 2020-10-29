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
import com.example.babyinvestor.data.model.TrendingTickers.QuoteItems;

import java.util.List;

public class TrendingStocksAdapter extends RecyclerView.Adapter<TrendingStocksAdapter.StockViewHolder>{
    private List<QuoteItems> quotes;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public TrendingStocksAdapter(List<QuoteItems> quotes, Context context) {
        this.quotes = quotes;
        this.context = context;
    }

    @NonNull
    @Override
    public TrendingStocksAdapter.StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stocksitem,parent,false);
        return new TrendingStocksAdapter.StockViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingStocksAdapter.StockViewHolder holders, int position) {
        final TrendingStocksAdapter.StockViewHolder holder = holders;
        QuoteItems item = quotes.get(position);

        try {
            holder.ticker.setText(item.getTicker_symbol());
            holder.name.setText(item.getCompany_name());
            Double price = Double.valueOf(item.getLast_price());
            Double percent_change = Double.valueOf(item.getMarket_change());
            Double price_change = Double.valueOf(item.getPercent_change());
            holder.price.setText("$"+String.format("%.2f", price));
            if(price_change!=null) {
                holder.price_change.setText(String.format("%.2f", price_change));
                if (item.getMarket_change().startsWith("-")) {
                    holder.price_change.setTextColor(Color.RED);
                } else {
                    holder.price_change.setTextColor(Color.GREEN);
                }
            }



            if(percent_change!=null) {
                holder.percentage_change.setText(String.format("%.2f", percent_change )+"%");
                if (item.getPercent_change().startsWith("-")) {
                    holder.percentage_change.setTextColor(Color.RED);
                } else {
                    holder.percentage_change.setTextColor(Color.GREEN);
                }
            }
        }
        catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }


    public class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ticker,name,currency,price,price_change,percentage_change;

        OnItemClickListener onItemClickListener;

        public StockViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            ticker = itemView.findViewById(R.id.ticker);
            name = itemView.findViewById(R.id.company_name);
            price = itemView.findViewById(R.id.open_price);
            price_change = itemView.findViewById(R.id.priceChange);
            percentage_change = itemView.findViewById(R.id.percentChange);

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
