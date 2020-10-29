package com.example.babyinvestor.api;


import com.example.babyinvestor.data.model.Price.PriceResponse;
import com.example.babyinvestor.data.model.Summary.DefaultKeyStatistics;
import com.example.babyinvestor.data.model.historicaldata.HistoricalData;
import com.example.babyinvestor.data.model.news.News;
import com.example.babyinvestor.data.model.historicaldata.StockPrices;
import com.example.babyinvestor.data.model.TrendingTickers.TrendingTickers;
import com.example.babyinvestor.data.model.search.QueryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("companies/news")
    Call<News> getNews(
      @Query("api_key") String apikey
    );

    @GET("stock_exchanges/USCOMP/prices")
    Call<StockPrices> getStocks(
            @Query("api_key") String apikey
    );


    @GET("market/get-trending-tickers")
    @Headers({
            "x-rapidapi-host: apidojo-yahoo-finance-v1.p.rapidapi.com",
            "x-rapidapi-key: 4d94b03cc4msh30049f1d89b2c2ep13b13djsn664715bbbc44"
    })
    Call<TrendingTickers> getTrendingTickers(
            @Query("region") String region
    );


    @GET("stock/v2/get-historical-data")
    @Headers({
            "x-rapidapi-host: apidojo-yahoo-finance-v1.p.rapidapi.com",
            "x-rapidapi-key: 4d94b03cc4msh30049f1d89b2c2ep13b13djsn664715bbbc44"
    })
    Call<HistoricalData> getHistoricalData(
            @Query("period1") String period1,
            @Query("period2") String period2,
            @Query("symbol") String symbol,
            @Query("frequency") String frequency,
            @Query("filter") String filter
    );


    @GET("stock/v2/get-summary")
    @Headers({
            "x-rapidapi-host: apidojo-yahoo-finance-v1.p.rapidapi.com",
            "x-rapidapi-key: 4d94b03cc4msh30049f1d89b2c2ep13b13djsn664715bbbc44"
    })
    Call<DefaultKeyStatistics> getCompanySummary(
            @Query("region") String region,
            @Query("symbol") String symbol
    );


    @GET("market/auto-complete")
    @Headers({
            "x-rapidapi-host: apidojo-yahoo-finance-v1.p.rapidapi.com",
            "x-rapidapi-key: 4d94b03cc4msh30049f1d89b2c2ep13b13djsn664715bbbc44"
    })
    Call<QueryResponse> getQueryResults(
            @Query("lang") String language,
            @Query("region") String region,
            @Query("query") String query
    );

    @GET("stock/v2/get-profile")
    @Headers({
            "x-rapidapi-host: apidojo-yahoo-finance-v1.p.rapidapi.com",
            "x-rapidapi-key: 4d94b03cc4msh30049f1d89b2c2ep13b13djsn664715bbbc44"
    })
    Call<PriceResponse> getPrice(
            @Query("symbol") String symbol
    );



}
