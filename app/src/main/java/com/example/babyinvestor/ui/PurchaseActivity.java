package com.example.babyinvestor.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babyinvestor.R;
import com.example.babyinvestor.ui.login.LoginActivity;
import com.example.babyinvestor.ui.login.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class PurchaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText order;
    private TextView price;
    private Button buybtn;
    private String ticker;
    private TextView tickertxt;
    private String pr;
    private String orderType;
    private String user_id;
    //private float num_shares;
    AlertDialog.Builder builder;

    private int spinner_pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiity_order);
        ticker = getIntent().getStringExtra("TICKER");
        pr = getIntent().getStringExtra("PRICE");
        SharedPreferences preferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        user_id = preferences.getString("s_id", "default value");

        buybtn = (Button) findViewById(R.id.buy_button);
        price = (TextView) findViewById(R.id.price);
        tickertxt = (TextView) findViewById(R.id.ticker_txt);
        order = (EditText) findViewById(R.id.ordervalue);
        tickertxt.setText(ticker);
        price.setText(pr);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        builder = new AlertDialog.Builder(this);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> ordertype = new ArrayList<String>();
        ordertype.add("No of Shares");
        ordertype.add("Value");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ordertype);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //builder.setMessage("Are you sure you want to goo ahead with burchase") .setTitle("Review Order");



                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure you want to go ahead with Placing an order for "+getNumShares()+" number of shares?")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
//                                Toast.makeText(getApplicationContext(),"you choose confirm for alertbox",
//                                        Toast.LENGTH_SHORT).show();

                                float num_shares = getNumShares();


                                OrderTask order = new OrderTask(user_id,ticker,pr,num_shares);
                                order.execute();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
//                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("REVIEW ORDER");
                alert.show();



            }
        });

    }

    public float getNumShares(){
        if(spinner_pos == 0){
            String order_v = order.getText().toString();
            return Float.parseFloat(order_v);
        }
        else{
            return Float.parseFloat(order.getText().toString())/Float.parseFloat(pr);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();



        switch(position){
            case 0:
                spinner_pos = 0;
            case 1:
                spinner_pos = 1;

        }

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class OrderTask extends AsyncTask<String, Void, Integer> {
        String ticker,price, value,user_id;
        float no_of_shares;

        public OrderTask(String user_id, String ticker, String price, Float no_of_shares) {
            this.user_id = user_id;
            this.ticker = ticker;
            this.price = price;
            this.no_of_shares = no_of_shares;
            //this.value = value;
        }
//user_id
        //ticker
        //no_of_shares
        //price
        //buy


        @Override
        protected Integer doInBackground(String... strings) {
            String register_url = "http://10.0.2.2/babyinvestor/controller.php?cmd=3";
            try {
                URL url = new URL(register_url);
                HttpURLConnection htpc = (HttpURLConnection) url.openConnection();
                htpc.setRequestMethod("POST");
                htpc.setDoOutput(true);
                htpc.setDoInput(true);
                OutputStream outputStream = htpc.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postdata = "&"+ URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("ticker", "UTF-8") + "=" + URLEncoder.encode(ticker, "UTF-8")+ "&"
                        + URLEncoder.encode("num_shares", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(no_of_shares), "UTF-8")+ "&"
                        + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8");
                //System.out.println(postdata);
                Log.d("DEBUG", "Data" + postdata);
                bufferedWriter.write(postdata);
                bufferedWriter.close();
                //bufferedWriter.flush();
                outputStream.close();
                InputStream inputStream = htpc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String result = "";
                String line = "";
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                Log.d("DEBUG", "Result" + result);
                JSONObject js = new JSONObject(result);
                int response = (int) js.get("result");
                Log.d("DEBUG", "Response" + response);
                return response;
//                if (response==1) {
//                    //loggedInUser = new LoggedInUser(u_id, name);
//                    Toast t = Toast.makeText(PurchaseActivity.this, "You were successfully registered", Toast.LENGTH_SHORT);
//                    t.show();
//                }
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
        protected void onPostExecute(Integer a) {
            //TODO
            //do something....Successfu
            if(a==1){
                Toast t = Toast.makeText(PurchaseActivity.this, "Order has been placed", Toast.LENGTH_SHORT);
                t.show();
            }
            else{
                Toast t = Toast.makeText(PurchaseActivity.this, "Error placing order", Toast.LENGTH_SHORT);
                t.show();
            }
        }




    }

}
