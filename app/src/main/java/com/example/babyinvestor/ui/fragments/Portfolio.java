package com.example.babyinvestor.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babyinvestor.GeneratedPortfolioActivity;
import com.example.babyinvestor.MainActivity;
import com.example.babyinvestor.R;
import com.example.babyinvestor.data.model.Portfolio.CompanyInfo;
import com.example.babyinvestor.ui.PurchaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class Portfolio extends Fragment {

    private Button gen_portfolio_btn;
    private EditText investmentAmount;
    HashMap<String, CompanyInfo> portfolio = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.portfolio_fragement,container,false);
        gen_portfolio_btn = (Button) view.findViewById(R.id.mock_portfolio_btn);
        investmentAmount = (EditText) view.findViewById(R.id.amount);
        gen_portfolio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MockPortfolioTask mpt = new MockPortfolioTask();
//                mpt.execute();
                if(investmentAmount.getText().toString() !=null ){
                    try{
                        float amount = Float.parseFloat(investmentAmount.getText().toString());
                        new MockPortfolioTask(getContext(),amount).execute();
                    }
                    catch(Exception e){
                        investmentAmount.setError("Enter a correct Amount");
                    }

                }
                else if(investmentAmount.getText().toString().length() <1){
                    investmentAmount.setError("Enter the Amount too be invested");
                }


            }
        });

        return view;
    }

    private class MockPortfolioTask extends AsyncTask<String, Void, HashMap<String, CompanyInfo>> {





        public MockPortfolioTask() {

        }

        Context context;
        private Float amount;
        private MockPortfolioTask(Context context,Float amount) {
            this.context = context.getApplicationContext();
            this.amount = amount;
        }


        @Override
        protected HashMap<String, CompanyInfo> doInBackground(String... params) {

            String register_url = "http://10.0.2.2:5000/";
            try {
                URL url = new URL(register_url);
                HttpURLConnection htpc = (HttpURLConnection) url.openConnection();
                htpc.setRequestMethod("GET");
                //htpc.setDoOutput(true);
                htpc.setDoInput(true);
               // OutputStream outputStream = htpc.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String postdata = "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
//                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")+ "&"
//                        + URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(firstname, "UTF-8")+ "&"
//                        + URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8")+ "&"
//                        + URLEncoder.encode("sex", "UTF-8") + "=" + URLEncoder.encode(sex, "UTF-8")+ "&"
//                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+ "&"
//                        + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
//                //System.out.println(postdata);
//                Log.d("DEBUG", "Data" + postdata);
//                bufferedWriter.write(postdata);
//                bufferedWriter.close();
                //bufferedWriter.flush();
               // outputStream.close();
                InputStream inputStream = htpc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String result = "";
                String line = "";
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                Log.d("DEBUG", "Result" + result);
                JSONObject js = new JSONObject(result);
                JSONArray assets = (JSONArray) js.get("Assets");
                for (int i = 0; i < assets.length(); i++) {
                    Log.d("DEBUG", "Weight" + Float.parseFloat(assets.getJSONObject(i).get("weight").toString()));
                    CompanyInfo pft = new CompanyInfo(assets.getJSONObject(i).get("name").toString(),Float.parseFloat(assets.getJSONObject(i).get("weight").toString()));
                    portfolio.put(assets.getJSONObject(i).get("ticker").toString(),pft);
                }

                return portfolio;



            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            } catch (IOException | JSONException e) {
                e.printStackTrace();

            }
            return null;
        }


        @Override
        protected void onPostExecute(HashMap<String, CompanyInfo> ptf) {
            //TODO
            //do something....Successfu
            if(!(ptf == null)) {
                Log.d("DEBUG", "Starting activity");
                HashMap<String, CompanyInfo> hashMap = ptf;
                Intent intent = new Intent(context, GeneratedPortfolioActivity.class);
                intent.putExtra("hashMap", hashMap);
                intent.putExtra("InvestmentAmount", amount);
                startActivity(intent);
            }
            else {
                Toast t = Toast.makeText(context, "Error generating portfolio", Toast.LENGTH_SHORT);
                t.show();
            }
        }

    }
}
