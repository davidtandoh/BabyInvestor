package com.example.babyinvestor;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyinvestor.R;
import com.example.babyinvestor.api.ApiInterface;
import com.example.babyinvestor.api.YahooApiClient;
import com.example.babyinvestor.data.model.historicaldata.HistoricalData;
import com.example.babyinvestor.data.model.historicaldata.HistoricalDataCache;
import com.example.babyinvestor.data.model.historicaldata.StockItem;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockDetail extends AppCompatActivity implements View.OnClickListener{
    private String ticker;
    private LineChart lineGraph;
    private Button btn1d;
    private Button btn1w;
    private Button btn1m;
    private Button btn1y;
    private List<StockItem> stockItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_detail_activity);
        ticker = getIntent().getStringExtra("TICKER");
        lineGraph =(LineChart)findViewById(R.id.line_graph);
        btn1d = (Button) findViewById(R.id.onedaybtn);
        btn1w = (Button) findViewById(R.id.oneweekbtn);
        btn1m = (Button) findViewById(R.id.onemonthbtn);
        btn1y = (Button) findViewById(R.id.oneyrbtn);
        btn1d.setOnClickListener(this::onClick);
        btn1w.setOnClickListener(this::onClick);
        btn1m.setOnClickListener(this::onClick);
        btn1y.setOnClickListener(this::onClick);

        executeQuery(ticker,"day");
        //stockItems.addAll(HistoricalDataCache.getHistoricalDataCache().getHistoricalValues());
        Log.d("STCK IT SIZE", String.valueOf(stockItems.size()));



    }
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ticker = getArguments().getString("TICKER");
//        View view = inflater.inflate(R.layout.stock_detail_fragment,container,false);
//        lineGraph =(LineChart)view.findViewById(R.id.line_graph);
//        executeQuery(ticker);
//
//
//        //stockItems.addAll(HistoricalDataCache.getHistoricalDataCache().getHistoricalValues());
//        Log.d("STCK IT SIZE", String.valueOf(stockItems.size()));
//        //displayChart(lineGraph);
//
//        Log.d("VIEW", "Returning view");
//        return view;
//    }

    private void displayChart(LineChart lineGraph,String symbol) {
        Log.d("CHART", String.valueOf(stockItems.size()));
        Description desc = new Description();
        desc.setText(symbol + " close values");
        lineGraph.setDescription(desc);
        lineGraph.setNoDataText("Data set empty");
        lineGraph.setTouchEnabled(true);
        lineGraph.setDragEnabled(true);
        lineGraph.setScaleEnabled(true);
        lineGraph.setPinchZoom(true);
        lineGraph.getLegend().setEnabled(false); // hide legend at base of x-axis
        lineGraph.setDrawGridBackground(false);
        lineGraph.getXAxis().setDrawGridLines(false);

        lineGraph.setDrawBorders(false);
        lineGraph.setPinchZoom(true);


        lineGraph.getXAxis().setTextColor(getResources().getColor(R.color.colorAccent));
        lineGraph.getAxisLeft().setTextColor(getResources().getColor(R.color.colorAccent));
        lineGraph.getAxisLeft().setEnabled(true);
        lineGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        //lineGraph.setExtraRightOffset(getResources().getDimension(R.dimen.table_margin));
        // set marker view

        // set upper & lower limit lines
        //LimitLine lowerLimit = new LimitLine(getLowerLimit() - 5f, "Lower limit");
        //setLimitLine(lowerLimit, LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        //LimitLine upperLimit = new LimitLine(getUpperLimit() + 5f, "Upper limit");
        //setLimitLine(upperLimit, LimitLine.LimitLabelPosition.RIGHT_TOP);

        // set scale on YAxis (x-axis shown by default)
        YAxis yAxis = lineGraph.getAxisLeft();
        //setYAxis(yAxis, lowerLimit, upperLimit);
       // setYAxis(yAxis);
        lineGraph.getAxisRight().setEnabled(false); // hide axis

        // animate graph
        lineGraph.animateY(1500, Easing.EaseInOutQuart);

        // add data
        addData(lineGraph);
    }



    private void addData(LineChart lineGraph) {
        //reverse array list
       Collections.reverse(stockItems);

        // number of data points to display (one for each date)
        String[] xVals = new String[stockItems.size()];
        for (int i = 0; i < xVals.length; i++) {
            Log.d("Price", String.valueOf(stockItems.get(i).getClose_price()));
            xVals[i] = stockItems.get(i).getDate();
        }

        // dataset of close values
        List<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < stockItems.size(); i++) {
           // yVals.add(new Entry((float) stockItems.get(i).getClose_price(), ));
            yVals.add(new Entry(Float.parseFloat(stockItems.get(i).getDate()), (float) stockItems.get(i).getClose_price()));
        }

        LineDataSet dataSet = new LineDataSet(yVals, "Close prices");
        //dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        dataSet.setLineWidth(3f);
        dataSet.setDrawCircles(false);
        dataSet.setCircleColor(getResources().getColor(R.color.colorAccent));
        dataSet.setColor(getResources().getColor(R.color.colorAccent));
        dataSet.setCircleRadius(6f);
        dataSet.setCircleHoleColor(Color.WHITE);
        dataSet.setCircleHoleRadius(4f);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        dataSet.setDrawVerticalHighlightIndicator(false);





        // create & add the graph
        List<ILineDataSet> lineDateSet = new ArrayList<>();
        lineDateSet.add(dataSet);
        LineData data = new LineData(lineDateSet);

//        ValueFormatter formatter = new ValueFormatter() {
//            @Override
//            public String getAxisLabel(float value, AxisBase axis) {
//                return xVals[(int) value];
//            }
//        };
//        XAxis xAxis = lineGraph.getXAxis();
//       // xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//        xAxis.setValueFormatter(formatter);

        lineGraph.setData(data);
    }

    private void executeQuery(String symbol,String period) {

        String period1;
        String period2=Utils.getCurrentEpochDate();
        String frequency;

        switch(period){
            case "day":
                period1=Utils.getYesterdayEpochDate();
                break;
            case "week":
                period1=Utils.getOneWeekEpochDate();
                break;
            case "month":
                period1=Utils.getOneMonthEpochDate();
                break;
            case "year":
                Log.d("case","yeear");
                period1=Utils.getOneYearEpochDate();
                break;
            default:
                period1=Utils.getOneWeekEpochDate();
        }


        Log.d("PERIOD1", period1);
        Log.d("PERIOD2", period2);


        ApiInterface apiInterface = YahooApiClient.getApiClient().create(ApiInterface.class);

        Call<HistoricalData> call;
        call = apiInterface.getHistoricalData(period1, period2, symbol, null, "history");

        call.enqueue(new Callback<HistoricalData>() {

            @Override
            public void onResponse(Call<HistoricalData> call, Response<HistoricalData> response) {
                if (response.isSuccessful() && response.body().getStockItems() != null) {
                    Log.d("RESPONSE", "SUCCESSFUL");
                    if (!stockItems.isEmpty()) {
                        stockItems.clear();
                    }

                    stockItems = response.body().getStockItems();
                    for (StockItem s : stockItems) {
                        s.setSymbol(symbol);
                    }
                    displayChart(lineGraph,symbol);
                    //HistoricalDataCache.getHistoricalDataCache().setHistoricalValues(new ArrayList<>(sItems));
                    Log.d("GRAPH", String.valueOf(stockItems.size()));

                } else {
                    Log.d("NO RESULT", String.valueOf(response.body().getStockItems().size()));
                    //Toast.makeText(getActivity(),"No Result",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HistoricalData> call, Throwable t) {
                Log.d("FAILURE", "failed");

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onedaybtn:
                executeQuery(ticker,"day");
                break;
            case R.id.oneweekbtn:
                executeQuery(ticker,"week");
                break;
            case R.id.onemonthbtn:
                executeQuery(ticker,"month");
                break;
            case R.id.oneyrbtn:
                executeQuery(ticker,"year");
                break;
        }

    }


//    private class GetHistoricalData extends AsyncTask<String,Void,List<StockItem>>{
//
//
//        List<StockItem> sItems = new ArrayList<>();
//
//        private String symbol;
//
//        public GetHistoricalData(String symbol, List<StockItem> sitems) {
//            //mTimeFrame = timeFrame;
//            this.sItems =sItems;
//            this.symbol = symbol;
//        }
//
//        public List<StockItem> getStockItems() {
//            return sItems;
//        }
//
//        public void setStockItems(List<StockItem> stockItems) {
//            this.sItems = stockItems;
//        }
//
//
//        @Override
//        protected List<StockItem> doInBackground(String... params)  {
//            ApiInterface apiInterface = YahooApiClient.getApiClient().create(ApiInterface.class);
//            symbol = params[0];
//
//            Call<HistoricalData> call;
//            call = apiInterface.getHistoricalData("1546448400", "1562086800", symbol, null, "history");
//
//            call.enqueue(new Callback<HistoricalData>() {
//                @Override
//                public void onResponse(Call<HistoricalData> call, Response<HistoricalData> response) {
//                    if (response.isSuccessful() && response.body().getStockItems() != null) {
//
//                        if (!sItems.isEmpty()) {
//                            sItems.clear();
//                        }
//
//                        sItems = response.body().getStockItems();
//                        for (StockItem s : sItems) {
//                            s.setSymbol(symbol);
//                        }
//                        //HistoricalDataCache.getHistoricalDataCache().setHistoricalValues(new ArrayList<>(sItems));
//                        Log.d("GRAPH", String.valueOf(sItems.size()));
//
//                    } else {
//                        Log.d("NO RESULT", String.valueOf(response.body().getStockItems().size()));
//                        //Toast.makeText(getActivity(),"No Result",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<HistoricalData> call, Throwable t) {
//                    Log.d("FAILURE", "failed");
//
//                }
//            });
//            return sItems;
//        }
//
//
//        @Override
//        protected void onPostExecute(List<StockItem> sItems) {
//            super.onPostExecute(sItems);
//            Log.d("OPE", String.valueOf(sItems.size()));
//            StockDetail.this.stockItems = stockItems;
//            displayChart(lineGraph);
//        }
//    }






}
