package com.example.babyinvestor.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.babyinvestor.R;
import com.example.babyinvestor.api.ApiInterface;
import com.example.babyinvestor.api.YahooApiClient;
import com.example.babyinvestor.data.TrendingStocksAdapter;
import com.example.babyinvestor.data.model.Summary.DefaultKeyStatistics;
import com.example.babyinvestor.data.model.Summary.SummaryProfile;
import com.example.babyinvestor.data.model.TrendingTickers.Quotes;
import com.example.babyinvestor.data.model.TrendingTickers.TrendingTickers;
import com.example.babyinvestor.ui.fragments.Stocks_fragment;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String API_KEY = "OjczNGI1MjE4MDg4MGFhYTJlODRhM2VmNzFlNTBiZDVi";

    private String ticker;
    TextView  comProfile,summary, employees, website, sector, industry;
    ImageButton showMore;
    boolean showMoreclicked = true;



    public SummaryFragment(String ticker) {
        this.ticker = ticker;
    }

    public static SummaryFragment newInstance(int index,String ticker) {
        SummaryFragment fragment = new SummaryFragment(ticker);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.summary_fragment, container, false);
        //comProfile = (TextView)root.findViewById(R.id.comProfile);
        summary = (TextView)root.findViewById(R.id.comSummary);
        employees = (TextView) root.findViewById(R.id.employees);
        sector= (TextView) root.findViewById(R.id.sector);
        website = (TextView) root.findViewById(R.id.website);
        showMore = (ImageButton) root.findViewById(R.id.btnShowmore);

        showMore.setOnClickListener(v -> {

            int icon;

            if (showMoreclicked)
            {
                showMoreclicked = false;
                icon = R.drawable.baseline_keyboard_arrow_up_black_18;
                summary.setMaxLines(Integer.MAX_VALUE);
            }
            else
            {
                showMoreclicked = true;
                icon = R.drawable.baseline_keyboard_arrow_down_black_18;
                summary.setMaxLines(3);
            }

            showMore.setImageDrawable(
                    ContextCompat.getDrawable(getActivity().getApplicationContext(), icon));
        });




        loadSummary();
        return root;
    }


    public void loadSummary(){
        ApiInterface apiInterface = YahooApiClient.getApiClient().create(ApiInterface.class);

        Call<DefaultKeyStatistics> call;
        call = apiInterface.getCompanySummary("US",ticker);

        call.enqueue(new Callback<DefaultKeyStatistics>() {
            @Override
            public void onResponse(Call<DefaultKeyStatistics> call, Response<DefaultKeyStatistics> response) {
                if(response.isSuccessful() && response.body().getSummaryProfile() !=null){

                    SummaryProfile profile = response.body().getSummaryProfile();
                    //comProfile.setText(profile.getLongBusinessSummary());
                    summary.setText(profile.getLongBusinessSummary());
                    employees.setText(profile.getFullTimeEmployees());
                    sector.setText(profile.getSector());
                    website.setText(profile.getWebsite());
                }
                else{
                    if(getActivity()!=null) {
                        Toast.makeText(getActivity(), "No Result", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultKeyStatistics> call, Throwable t) {
                Log.d("FAILURE","failed");

            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}
