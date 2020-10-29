package com.example.babyinvestor;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyinvestor.data.PortfolioAdapter;
import com.example.babyinvestor.data.SearchAdapter;
import com.example.babyinvestor.data.model.Portfolio.CompanyInfo;
import com.example.babyinvestor.data.model.Portfolio.PortfolioItem;
import com.example.babyinvestor.data.model.search.ResultItem;
import com.example.babyinvestor.ui.fragments.Search_fragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratedPortfolioActivity extends AppCompatActivity {


    private PieChart pieChart;
    private  HashMap<String, CompanyInfo> hashMap;
    private Float amount;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PortfolioItem> portfolioItems = new ArrayList<>();
    private PortfolioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_portfolio);

        Intent intent = getIntent();
        hashMap = (HashMap<String, CompanyInfo>) intent.getSerializableExtra("hashMap");
        amount = intent.getFloatExtra("InvestmentAmount",400);

        recyclerView = (RecyclerView) findViewById(R.id.portfolioRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        //adapter.setOnItemClickListener(Search_fragment.this);

        for (Map.Entry<String, CompanyInfo> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            CompanyInfo value = entry.getValue();
            String name = value.getName();
            Float weight = value.getWeight();
            portfolioItems.add(new PortfolioItem(key,String.valueOf(weight*100),amount*weight,name));
        }


        adapter = new PortfolioAdapter(portfolioItems,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        pieChart = (PieChart) findViewById(R.id.piechart);

        displayChart(pieChart);

    }


    private void displayChart(PieChart pieChart) {
        Description desc = new Description();
        desc.setText("Portfolio asset weights");//
        pieChart.setDescription(desc);


        pieChart.setUsePercentValues(true);
        List<PieEntry> xvalues = new ArrayList<>();

        for (Map.Entry<String, CompanyInfo> entry : hashMap.entrySet()) {
            String key = entry.getKey();
           // Float value = entry.getValue();
            CompanyInfo value = entry.getValue();
            String name = value.getName();
            Float weight = value.getWeight();
            xvalues.add(new PieEntry(weight*100, key));
        }
        //new pieChartColor(data,dataSet);
        // In Percentage

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());




        PieDataSet ds = new PieDataSet(xvalues,"Weights");

        ds.setColors(colors);

//        IPieDataSet pieDataSet = new ArrayList<>();
//        pieDataSet.add(ds);

        PieData data = new PieData(ds);
        data.setValueFormatter(new PercentFormatter());

        pieChart.setData(data);
//        pieChart.description.text = ""
//        pieChart.isDrawHoleEnabled = false
        data.setValueTextSize(13f);


        //pieChart.setOnChartValueSelectedListener(this);
        chartDetails(pieChart, Typeface.SANS_SERIF);

//        lineGraph.setDescription(desc);
//        lineGraph.setNoDataText("Data set empty");
//        lineGraph.setTouchEnabled(true);
//        lineGraph.setDragEnabled(true);
//        lineGraph.setScaleEnabled(true);
//        lineGraph.setPinchZoom(true);
//        lineGraph.getLegend().setEnabled(false); // hide legend at base of x-axis
//
//        lineGraph.setDrawGridBackground(false);
//        lineGraph.getXAxis().setDrawGridLines(false);
//
//        lineGraph.setDrawBorders(false);
//        lineGraph.setPinchZoom(true);
//
//
//        lineGraph.getXAxis().setTextColor(getResources().getColor(R.color.colorAccent));
//        lineGraph.getAxisLeft().setTextColor(getResources().getColor(R.color.colorAccent));
//        lineGraph.getAxisLeft().setEnabled(true);
//        lineGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        // set marker view
//
//        // set upper & lower limit lines
//        //LimitLine lowerLimit = new LimitLine(getLowerLimit() - 5f, "Lower limit");
//        //setLimitLine(lowerLimit, LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        //LimitLine upperLimit = new LimitLine(getUpperLimit() + 5f, "Upper limit");
//        //setLimitLine(upperLimit, LimitLine.LimitLabelPosition.RIGHT_TOP);
//
//        // set scale on YAxis (x-axis shown by default)
//        YAxis yAxis = lineGraph.getAxisLeft();
//        //setYAxis(yAxis, lowerLimit, upperLimit);
//        // setYAxis(yAxis);
//        lineGraph.getAxisRight().setEnabled(false); // hide axis
//
//        // animate graph
//        lineGraph.animateY(1500, Easing.EaseInOutQuart);

        // add data
        //addData(pieChart);
    }

    private void chartDetails(PieChart mChart,Typeface tf) {
//        mChart.setDe
//        mChart.description.isEnabled = true
       // mChart.centerText = ""
        mChart.setCenterTextSize(10F);
        mChart.setCenterTextTypeface(tf);
      //  val l = mChart.legend
       // mChart.legend.isWordWrapEnabled = true
//        mChart.legend.isEnabled = false
//        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//        l.formSize = 20F
//        l.formToTextSpace = 5f
//        l.form = Legend.LegendForm.SQUARE
//        l.textSize = 12f
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.isWordWrapEnabled = true
//        l.setDrawInside(false)
        mChart.setTouchEnabled(false);
        mChart.setDrawEntryLabels(false);
//        mChart.legend.isWordWrapEnabled = true
        mChart.setExtraOffsets(20f, 0f, 20f, 0f);
        mChart.setUsePercentValues(true);
        // mChart.rotationAngle = 0f
        mChart.setUsePercentValues(true);
        mChart.setDrawCenterText(false);
//        mChart.description.isEnabled = true
//        mChart.isRotationEnabled = false
    }



    private void addData(PieChart pieChart) {


        PieData pd = new PieData();
        // dataset of weights
        List<PieEntry> vals = new ArrayList<>();

        vals.add(new PieEntry((float)0.12,(float)0.12));



        PieDataSet ds = new PieDataSet(vals,"Weights");

//        IPieDataSet pieDataSet = new ArrayList<>();
//        pieDataSet.add(ds);

        PieData pdata = new PieData(ds);

        pieChart.setData(pdata);
    }
}
