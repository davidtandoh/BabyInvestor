package com.example.babyinvestor.ui.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.babyinvestor.R;
import com.example.babyinvestor.data.LoginDataSource;
import com.example.babyinvestor.data.model.LoggedInUser;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText email,firstName,lastName,passwordEditText,secondPassword,address,phone;
    private String gender;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        passwordEditText = findViewById(R.id.password);
        secondPassword = findViewById(R.id.password2);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);



        final Button registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()) {
                    RegisterTask registerTask = new RegisterTask(email.getText().toString(),
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            gender,
                            passwordEditText.getText().toString(),
                            phone.getText().toString(),
                            address.getText().toString());

                    registerTask.execute();
                }
            }
        });






    }


    private boolean validateForm(){
        if (isEmpty(firstName)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }
        if (isEmpty(lastName)) {
            Toast t = Toast.makeText(this, "You must enter Last name to register!", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        return true;


    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    gender = "M";
                    break;
            case R.id.female:
                if (checked)
                    gender = "F";
                    break;
        }
    }

    private class RegisterTask extends AsyncTask<String, Void, Integer> {
        String email,firstname,lastname,phone,sex,password,address;

        public RegisterTask(String email,String firstname, String lastname, String sex, String password,String phone,String address) {
            this.email = email;
            this.firstname = firstname;
            this.lastname = lastname;
            this.sex = sex;
            this.phone = phone;
            this.password = password;
            this.address = address;

        }

        @Override
        protected Integer doInBackground(String... params) {

            String register_url = "http://10.0.2.2/babyinvestor/controller.php?cmd=1";
                try {
                    URL url = new URL(register_url);
                    HttpURLConnection htpc = (HttpURLConnection) url.openConnection();
                    htpc.setRequestMethod("POST");
                    htpc.setDoOutput(true);
                    htpc.setDoInput(true);
                    OutputStream outputStream = htpc.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String postdata = "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")+ "&"
                            + URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(firstname, "UTF-8")+ "&"
                            + URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8")+ "&"
                            + URLEncoder.encode("sex", "UTF-8") + "=" + URLEncoder.encode(sex, "UTF-8")+ "&"
                            + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+ "&"
                            + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
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
            if (a==1) {
                //loggedInUser = new LoggedInUser(u_id, name);
                Toast t = Toast.makeText(RegisterActivity.this, "You were successfully registered", Toast.LENGTH_SHORT);
                t.show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else{
                Toast t = Toast.makeText(RegisterActivity.this, "Error with registration", Toast.LENGTH_SHORT);
            }
        }

    }
}
