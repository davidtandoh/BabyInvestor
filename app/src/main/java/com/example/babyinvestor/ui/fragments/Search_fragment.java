package com.example.babyinvestor.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyinvestor.MainActivity;
import com.example.babyinvestor.R;
import com.example.babyinvestor.StockDetailActivity;
import com.example.babyinvestor.api.ApiInterface;
import com.example.babyinvestor.api.YahooApiClient;
import com.example.babyinvestor.data.SearchAdapter;
import com.example.babyinvestor.data.TrendingStocksAdapter;
import com.example.babyinvestor.data.model.TrendingTickers.QuoteItems;
import com.example.babyinvestor.data.model.TrendingTickers.Quotes;
import com.example.babyinvestor.data.model.TrendingTickers.TrendingTickers;
import com.example.babyinvestor.data.model.search.QueryResponse;
import com.example.babyinvestor.data.model.search.ResultItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_fragment extends Fragment implements SearchAdapter.OnItemClickListener {

    public static final String REGION = "US";
    public static final String LANG = "en";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ResultItem> results = new ArrayList<>();
    private SearchAdapter adapter;
    private TextView title;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_fragment,container,false);
        Bundle args = getArguments();
        String keyword = this.getArguments().getString("query");
        recyclerView = (RecyclerView)view.findViewById(R.id.stockRecyclerView);
        title = (TextView) view.findViewById(R.id.title);
        title.setText("Query Result");
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson(keyword);
        return view;
    }


        private void LoadJson(String keyword){
        Log.d("DEBUG!!!!","Load Stoocks Json called");
        ApiInterface apiInterface = YahooApiClient.getApiClient().create(ApiInterface.class);

        Call<QueryResponse> call;
        call = apiInterface.getQueryResults(LANG,REGION,keyword);

        call.enqueue(new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
                if(response.isSuccessful() && response.body().getResultSet().getResults()!=null){

                    if(!results.isEmpty()){
                        results.clear();
                    }
                   results = response.body().getResultSet().getResults();
                    // quoteItems = quotes.
                    Log.d("DEBUG_size", String.valueOf(results.size()));
                    adapter = new SearchAdapter(results,getActivity());
                    adapter.setOnItemClickListener(Search_fragment.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                else{
                    if(getActivity()!= null) {
                        Toast.makeText(getActivity(), "No Result", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {
                Log.d("FAILURE","failed");

            }
        });

    }


    @Override
    public void onItemClick(View view, int position) {
        ResultItem rt = results.get(position);
        String ticker = rt.getSymbol();

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
