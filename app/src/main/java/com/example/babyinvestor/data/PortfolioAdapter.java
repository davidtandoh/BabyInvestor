package com.example.babyinvestor.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyinvestor.R;
import com.example.babyinvestor.data.model.Portfolio.PortfolioItem;

import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>{
    private List<PortfolioItem> results;
    private Context context;


    public PortfolioAdapter(List<PortfolioItem> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public PortfolioAdapter.PortfolioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.portfolio_item,parent,false);
        return new PortfolioAdapter.PortfolioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioAdapter.PortfolioViewHolder holders, int position) {
        final PortfolioAdapter.PortfolioViewHolder holder = holders;
        PortfolioItem item = results.get(position);

        holder.ticker.setText(item.getTicker());
        Double weight = Double.valueOf(item.getPercentage());
        holder.weight.setText(String.format("%.2f", weight)+"%");
        Double amount =  Double.valueOf(item.getAmount());
        holder.amount.setText("$"+String.format("%.2f", amount));
        holder.company_name.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class PortfolioViewHolder extends RecyclerView.ViewHolder{
        TextView ticker,weight,amount,company_name;

        public PortfolioViewHolder(@NonNull View itemView) {
            super(itemView);

            ticker = itemView.findViewById(R.id.ticker);
            weight = itemView.findViewById(R.id.weight);
            amount = itemView.findViewById(R.id.amount);
            company_name = itemView.findViewById(R.id.company_name);


        }

        //public PortfolioViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }

//        @Override
//        public void onClick(View view) {
//            onItemClickListener.onItemClick(view,getAdapterPosition());
//        }
    }

}
