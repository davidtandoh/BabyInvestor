package com.example.babyinvestor.ui.fragments;

import android.content.Intent;
import android.net.Uri;
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
import com.example.babyinvestor.api.ApiInterface;
import com.example.babyinvestor.api.IntrinioApiClient;
import com.example.babyinvestor.data.NewsAdapter;
import com.example.babyinvestor.data.model.news.News;
import com.example.babyinvestor.data.model.news.NewsItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News_fragment extends Fragment implements NewsAdapter.OnItemClickListener {
    public static final String API_KEY = "OjczNGI1MjE4MDg4MGFhYTJlODRhM2VmNzFlNTBiZDVi";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<NewsItem> newsitems = new ArrayList<>();
    private NewsAdapter adapter;
    //private  NewsAdapter.OnItemClickListener onItemClickListener;
    private String TAG = News_fragment.class.getSimpleName();



    //private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson();
        return view;
    }

    public void LoadJson(){
        Log.d("DEBUG!!!!","Load Json called");
        ApiInterface apiInterface = IntrinioApiClient.getApiClient().create(ApiInterface.class);

        Call<News> call;
        call = apiInterface.getNews(API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getNewsItems() !=null){

                    if(!newsitems.isEmpty()){
                        newsitems.clear();
                    }
                    newsitems = response.body().getNewsItems();
                    Log.d("DEBUG_size", String.valueOf(response.body().getNewsItems().size()));
                    adapter = new NewsAdapter(newsitems,getActivity());
                    adapter.setOnItemClickListener(News_fragment.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                else{
                    Toast.makeText(getActivity(),"No Result",Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("FAILURE","failed");

            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        NewsItem newsitem = newsitems.get(position);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(newsitem.getUrl()));
        startActivity(intent);
    }
}
