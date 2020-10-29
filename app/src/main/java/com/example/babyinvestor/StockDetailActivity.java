package com.example.babyinvestor;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.example.babyinvestor.api.ApiInterface;
import com.example.babyinvestor.api.YahooApiClient;
import com.example.babyinvestor.data.model.Price.PriceResponse;
import com.example.babyinvestor.ui.PurchaseActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.babyinvestor.ui.main.SectionsPagerAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockDetailActivity extends AppCompatActivity {

    private String ticker;
    private TextView price;
    private String pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        ticker = getIntent().getStringExtra("TICKER");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),ticker);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        //FloatingActionButton fab = findViewById(R.id.fab);
        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        price = findViewById(R.id.price);
        loadPrice();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(StockDetailActivity.this, PurchaseActivity.class);
                intent.putExtra("TICKER", ticker);
                intent.putExtra("PRICE",pr);
                startActivity(intent);
            }
        });
    }

    private void loadPrice(){
        Log.d("LOAD","starteed");

        ApiInterface apiInterface = YahooApiClient.getApiClient().create(ApiInterface.class);

        Call<PriceResponse> call;
        call = apiInterface.getPrice(ticker);

        call.enqueue(new Callback<PriceResponse>() {

            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                if (response.isSuccessful() && response.body().getPrice() != null) {
                    Log.d("PRICERESPONSE", response.body().getPrice().toString());
                    pr=response.body().getPrice().getRegMarketPrice().getRaw();
                    Double price = Double.valueOf(pr);
                    setPrice(price);
                    Log.d("PRICE", response.body().getPrice().getRegMarketPrice().getRaw());
                }
            }

            @Override
            public void onFailure(Call<PriceResponse> call, Throwable t) {
                Log.d("PRICEFAIL", "FAILED");
            }
        });
        // Log.d("RETURN PRICE", pr);
        //return pr;
    }

    public void setPrice(Double pr){
        price.setText("$"+String.format("%.2f", pr));
    }
}