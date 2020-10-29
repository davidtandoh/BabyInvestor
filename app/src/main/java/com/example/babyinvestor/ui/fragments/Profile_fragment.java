package com.example.babyinvestor.ui.fragments;

import android.content.SharedPreferences;
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

import com.example.babyinvestor.MainActivity;
import com.example.babyinvestor.R;
import com.example.babyinvestor.data.model.Profile.User;
import com.example.babyinvestor.ui.PurchaseActivity;

import org.json.JSONArray;
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

import static android.content.Context.MODE_PRIVATE;

public class Profile_fragment extends Fragment {

    private EditText fname;
    private EditText lname;
    private EditText phone;
    private EditText address;
    private Button sbmtbutton;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_profile,container,false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String user_id = preferences.getString("s_id", "default value");
        //Bundle args = getArguments();
        fname= (EditText) view.findViewById(R.id.firstname);
        lname= (EditText) view.findViewById(R.id.lastname);
        phone= (EditText) view.findViewById(R.id.phone);
        address= (EditText) view.findViewById(R.id.address);
        sbmtbutton = (Button) view.findViewById(R.id.update);
        sbmtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitProfileTask spt = new SubmitProfileTask(user_id,
                        fname.getText().toString(),
                        lname.getText().toString(),
                        phone.getText().toString(),
                        address.getText().toString());
                spt.execute();

            }
        });
        UpdateTask upt = new UpdateTask(user_id);
        upt.execute();
        return view;
    }

    private void loadfields() {


    }

    private class UpdateTask extends AsyncTask<String, Void, User> {

        String user_id;

        public UpdateTask(String user_id) {
            this.user_id = user_id;
        }

        @Override
        protected User doInBackground(String... strings) {
            String getprofile_url = "http://10.0.2.2/babyinvestor/controller.php?cmd=4";
            try {
                URL url = new URL(getprofile_url);
                HttpURLConnection htpc = (HttpURLConnection) url.openConnection();
                htpc.setRequestMethod("POST");
                htpc.setDoOutput(true);
                htpc.setDoInput(true);
                OutputStream outputStream = htpc.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postdata = "&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");

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
                if (response==1) {
                    JSONArray users = (JSONArray) js.get("user");
                    JSONObject user = users.getJSONObject(0);
                    Log.d("DEBUG", "User" + user);
                    String first_name = (String) user.get("first_name");
                    String last_name = (String) user.get("last_name");
                    String phone = (String) user.get("phone");
                    String address = (String) user.get("address");
                    User the_user = new User(first_name,last_name,phone,address);
                    return the_user;
                }
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
        protected void onPostExecute(User a) {
            //TODO
            fname.setText(a.getFname());
            lname.setText(a.getLname());
            address.setText(a.getAddress());
            phone.setText(a.getPhone());
            //do something....Successfu
        }

    }

    private class SubmitProfileTask extends AsyncTask<String, Void, Integer> {

        private String user_id;
        private String fname;
        private String lname;
        private String phone;
        private String address;

        public SubmitProfileTask(String user_id, String fname, String lname, String phone, String address) {
            this.user_id = user_id;
            this.fname = fname;
            this.lname = lname;
            this.phone = phone;
            this.address = address;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String updateprofile_url = "http://10.0.2.2/babyinvestor/controller.php?cmd=5";
            try {
                URL url = new URL(updateprofile_url);
                HttpURLConnection htpc = (HttpURLConnection) url.openConnection();
                htpc.setRequestMethod("POST");
                htpc.setDoOutput(true);
                htpc.setDoInput(true);
                OutputStream outputStream = htpc.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postdata = "&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8")+ "&"
                        + URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(lname, "UTF-8")+ "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+ "&"
                        + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
                Log.d("DEBUG", "Postdata" + postdata);
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
            //do something....Successful
            if(a==1){
                Toast t = Toast.makeText(getActivity(), "Order has been placed", Toast.LENGTH_SHORT);
                t.show();
            }
            else{
                Toast t = Toast.makeText(getActivity(), "Error placing order", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }
}
