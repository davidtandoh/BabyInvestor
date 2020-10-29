package com.example.babyinvestor.ui.main;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.babyinvestor.R;
import com.example.babyinvestor.Utils;
import com.example.babyinvestor.api.ApiInterface;
import com.example.babyinvestor.api.YahooApiClient;
import com.example.babyinvestor.data.model.Price.PriceResponse;
import com.example.babyinvestor.data.model.historicaldata.HistoricalData;
import com.example.babyinvestor.data.model.historicaldata.StockItem;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class StockLineGraphFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private String ticker;
    private LineChart lineGraph;
    private Button btn1w;
    private Button btn1m;
    private Button btn3m;
    private Button btn1y;
    private List<StockItem> stockItems = new ArrayList<>();
    private List<StockItem> one_week_history = new ArrayList<>();
    private List<StockItem> one_month_history = new ArrayList<>();
    private List<StockItem> three_month_history = new ArrayList<>();
    private List<StockItem> one_year_history = new ArrayList<>();

    public StockLineGraphFragment(String ticker) {
        this.ticker = ticker;
    }

    public static StockLineGraphFragment newInstance(int index,String ticker) {
        StockLineGraphFragment fragment = new StockLineGraphFragment(ticker);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.line_graph_fragment, container, false);
//        final TextView textView = root.findViewById(R.id.section_label);
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        //ticker = getIntent().getStringExtra("TICKER");
        lineGraph =(LineChart)root.findViewById(R.id.line_graph);

        btn1w = (Button) root.findViewById(R.id.oneweekbtn);
        btn1m = (Button) root.findViewById(R.id.onemonthbtn);
        btn3m = (Button) root.findViewById(R.id.threemonthbtn);
        btn1y = (Button) root.findViewById(R.id.oneyrbtn);
        btn1w.setOnClickListener(this::onClick);
        btn1m.setOnClickListener(this::onClick);
        btn3m.setOnClickListener(this::onClick);
        btn1y.setOnClickListener(this::onClick);

        executeQuery(ticker,"day");

        return root;
    }

    private void displayChart(LineChart lineGraph,String symbol,String period) {
        Log.d("CHART", String.valueOf(stockItems.size()));
        Description desc = new Description();
        desc.setText(symbol + " close values");//
        lineGraph.setDescription(desc);
        lineGraph.setNoDataText("Data set empty");
        lineGraph.setTouchEnabled(true);
        lineGraph.setDragEnabled(true);
        lineGraph.setScaleEnabled(true);
        lineGraph.setPinchZoom(true);
        lineGraph.getLegend().setEnabled(false);

        lineGraph.setDrawGridBackground(false);
        lineGraph.getXAxis().setDrawGridLines(false);

        lineGraph.setDrawBorders(false);
        lineGraph.setPinchZoom(true);


        lineGraph.getXAxis().setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        lineGraph.getAxisLeft().setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        lineGraph.getAxisLeft().setEnabled(true);
        lineGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        // set scale on YAxis (x-axis shown by default)
        YAxis yAxis = lineGraph.getAxisLeft();
        //setYAxis(yAxis, lowerLimit, upperLimit);
        // setYAxis(yAxis);
        lineGraph.getAxisRight().setEnabled(false); // hide axis

        // animate graph
        lineGraph.animateY(1500, Easing.EaseInOutQuart);

        // add data
        addData(lineGraph,period);
    }



    private void addData(LineChart lineGraph,String period) {


        switch(period) {
            case "month":
                if (!one_month_history.isEmpty()) {
                    stockItems.clear();
                }
               stockItems.addAll(one_month_history) ;
                break;
            case "3month":
                if (!three_month_history.isEmpty()) {
                    stockItems.clear();
                }
                stockItems.addAll(three_month_history);
                break;
            case "year":
                if (!one_year_history.isEmpty()) {
                    stockItems.clear();
                }
                stockItems.addAll(one_year_history);
                break;
            case "week":
                if (!one_week_history.isEmpty()) {
                    stockItems.clear();
                }
                stockItems.addAll(one_week_history);
            default:

        }


        //reverse array list
        Collections.reverse(stockItems);

        // number of data points to display (one for each date)
        String[] xVals = new String[stockItems.size()];
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < xVals.length; i++) {
            Log.d("Price", String.valueOf(stockItems.get(i).getClose_price()));
            String date =  stockItems.get(i).getDate();
            Long epoch = Long.parseLong(date);
            DateFormat format = new SimpleDateFormat("dd/MM/yy");
            //xVals[i] = new java.text.SimpleDateFormat("MM-dd-yy").format(new java.util.Date (Long.parseLong(date)));
            xVals[i] = format.format(epoch*1000);
            dates.add(xVals[i]);
        }

        // dataset of close values
        List<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < stockItems.size(); i++) {
            // yVals.add(new Entry((float) stockItems.get(i).getClose_price(), ));
            yVals.add(new Entry(i, (float) stockItems.get(i).getClose_price()));
        }





        LineDataSet dataSet = new LineDataSet(yVals, "Close prices");
        // dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(false);
        dataSet.setCircleColor(getResources().getColor(R.color.colorPrimaryDark));
        dataSet.setColor(getResources().getColor(R.color.colorPrimaryDark));
        //dataSet.setCircleRadius(6f);
        dataSet.setCircleHoleColor(Color.WHITE);
        dataSet.setCircleHoleRadius(4f);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        dataSet.setDrawVerticalHighlightIndicator(false);



        // create & add the graph
        List<ILineDataSet> lineDateSet = new ArrayList<>();
        lineDateSet.add(dataSet);
        LineData data = new LineData(lineDateSet);


        XAxis xAxis = lineGraph.getXAxis();
        //xAxis.setAvoidFirstLastClipping(false);


        switch(period) {
            case "month":
                xAxis.setGranularity(4f); // minimum axis-step (interval) is 1
                break;
            case "3month":
                xAxis.setGranularity(7f);
                break;
            case "year":
                xAxis.setGranularity(12f);

                break;
            case "week":
                xAxis.setGranularity(1f);
            default:
                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        }




        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        lineGraph.setData(data);
    }

    private void executeQuery(String symbol,String period) {

        String period1;
        String period2= Utils.getCurrentEpochDate();
        String frequency;

        switch(period){
            case "week":
               // period1=Utils.getYesterdayEpochDate();
                period1=Utils.getOneWeekEpochDate();
                if(!one_week_history.isEmpty()){
                    displayChart(lineGraph,symbol,period);
                    return;
                }
                break;
            case "month":
                period1=Utils.getOneMonthEpochDate();
                if(!one_month_history.isEmpty()){
                    displayChart(lineGraph,symbol,period);
                    return;
                }
                break;
            case "3month":
                period1=Utils.getThreeMonthEpochDate();
                if(!three_month_history.isEmpty()){
                    displayChart(lineGraph,symbol,period);
                    return;
                }
                break;
            case "year":
                Log.d("case","year");
                period1=Utils.getOneYearEpochDate();
                if(!one_year_history.isEmpty()){
                    displayChart(lineGraph,symbol,period);
                    return;
                }
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

                    switch(period) {
                        case "week":
                            one_week_history.addAll(stockItems);
                            break;
                        case "month":
                            one_month_history.addAll(stockItems);
                            break;
                        case "3month":
                            three_month_history.addAll(stockItems);
                            break;
                        case "year":
                            one_year_history.addAll(stockItems);
                            break;
                        default:
                            one_week_history.addAll(stockItems);
                    }

                    displayChart(lineGraph,symbol,period);
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
            case R.id.oneweekbtn:
                executeQuery(ticker,"week");
                break;
            case R.id.onemonthbtn:
                executeQuery(ticker,"month");
                break;
            case R.id.threemonthbtn:
                executeQuery(ticker,"3month");
                break;
            case R.id.oneyrbtn:
                executeQuery(ticker,"year");
                break;
        }

    }

}