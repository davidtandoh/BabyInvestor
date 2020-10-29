package com.example.babyinvestor.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyinvestor.R;
import com.example.babyinvestor.StockDetail;
import com.example.babyinvestor.StockDetailActivity;
import com.example.babyinvestor.api.ApiInterface;
import com.example.babyinvestor.api.YahooApiClient;
import com.example.babyinvestor.data.TrendingStocksAdapter;
import com.example.babyinvestor.data.model.TrendingTickers.QuoteItems;
import com.example.babyinvestor.data.model.TrendingTickers.Quotes;
import com.example.babyinvestor.data.model.TrendingTickers.TrendingTickers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Stocks_fragment extends Fragment implements TrendingStocksAdapter.OnItemClickListener {

    public static final String API_KEY = "OjczNGI1MjE4MDg4MGFhYTJlODRhM2VmNzFlNTBiZDVi";
    public static final String REGION = "US";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Quotes> quotes = new ArrayList<>();
    private List<QuoteItems> quoteItems = new ArrayList<>();
    private TrendingStocksAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_fragment,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.stockRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson();
        return view;
    }


    public void LoadJson(){
        Log.d("DEBUG!!!!","Load Stoocks Json called");
        ApiInterface apiInterface = YahooApiClient.getApiClient().create(ApiInterface.class);

        Call<TrendingTickers> call;
        call = apiInterface.getTrendingTickers(REGION);

        call.enqueue(new Callback<TrendingTickers>() {
            @Override
            public void onResponse(Call<TrendingTickers> call, Response<TrendingTickers> response) {
                if(response.isSuccessful() && response.body().getTrendingTickerResults().getResults()!=null){

                    if(!quotes.isEmpty()){
                        quotes.clear();
                    }

                    quotes = response.body().getTrendingTickerResults().getResults();
                    Quotes q =quotes.get(0);
                    quoteItems = q.getQuotes();
                   // quoteItems = quotes.
                    Log.d("DEBUG_size", String.valueOf(quoteItems.size()));
                    adapter = new TrendingStocksAdapter(quoteItems,getActivity());
                    adapter.setOnItemClickListener(Stocks_fragment.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                else{
                    Toast.makeText(getActivity(),"No Result",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrendingTickers> call, Throwable t) {
                Log.d("FAILURE","failed");

            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        QuoteItems qt = quoteItems.get(position);
        String ticker = qt.getTicker_symbol();

       // StockDetail stockDetail= new StockDetail();
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.flContent, nextFrag, "findThisFragment")
//                .addToBackStack(null)
//                .commit();

//        FragmentManager fm=getFragmentManager();
//        FragmentTransaction ft=fm.beginTransaction();
//        Bundle args = new Bundle();
//        args.putString("TICKER",ticker);
//        stockDetail.setArguments(args);
//        ft.replace(R.id.flContent, stockDetail);
//        ft.commit();


        Intent intent = new Intent(getActivity(), StockDetailActivity.class);
        intent.putExtra("TICKER", ticker);
        startActivity(intent);

    }
}
