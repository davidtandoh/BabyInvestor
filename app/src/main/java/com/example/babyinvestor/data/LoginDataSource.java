package com.example.babyinvestor.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.babyinvestor.data.model.LoggedInUser;
import com.example.babyinvestor.ui.login.LoginActivity;

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

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource implements OnTaskCompleted {

    LoggedInUser loggedInUser = null;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication


                String type = "login";
//            BackgroundWorker bg = new BackgroundWorker(this);
//             bg.execute(type, username, password);
           // String result = bg.onPostExecute();
                LoginTask loginTask = new LoginTask(type, username, password, new OnTaskCompleted() {
                    @Override
                    public Result<LoggedInUser> onTaskCompleted(LoggedInUser user) {
                        return new Result.Success<>(loggedInUser);
                    }
                });
                synchronized (loginTask) {
                    loginTask.execute().notify();
                }
                if (loggedInUser != null) {
                    Log.d("DEBUG", "Logged in User" + loggedInUser.getDisplayName());
                    //SharedPreferences prefs = LoginActivity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

//set value to pref
                   // prefs.edit().putString("s_id", username.toString()).apply();

                    return new Result.Success<>(loggedInUser);
                } else {
                    return new Result.Error(new IOException("User credentials incorrect"));
                }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    @Override
    public Result<LoggedInUser> onTaskCompleted(LoggedInUser user) {
        return null;
    }


    private class LoginTask extends AsyncTask<String, Void, LoggedInUser> {
        String type;
        String username;
        String password;
        private OnTaskCompleted listener;

        public LoginTask(String type, String username, String password,OnTaskCompleted listener) {

            this.type = type;
            this.username = username;
            this.password = password;
            this.listener = listener;

        }

        public LoginTask(String type, String username, String password) {
            this.type = type;
            this.username = username;
            this.password = password;
        }


        @Override
        protected LoggedInUser doInBackground(String... params) {

            String login_url = "http://10.0.2.2/babyinvestor/controller.php?cmd=2";
            if(type.equals("login")) {
                // Log.d("DEBUG","Data"+login_url);
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection htpc = (HttpURLConnection) url.openConnection();
                    htpc.setRequestMethod("POST");
                    htpc.setDoOutput(true);
                    htpc.setDoInput(true);
                    OutputStream outputStream = htpc.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String postdata = "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
                        String u_id = (String) user.get("user_id");
                        String name = (String) user.get("first_name");
                        loggedInUser = new LoggedInUser(u_id, name);
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

            }
            return loggedInUser;
        }

        @Override
        protected void onPostExecute(LoggedInUser user) {
            super.onPostExecute(user);
            listener.onTaskCompleted(user);
        }
    }



}

interface OnTaskCompleted{
    Result<LoggedInUser> onTaskCompleted(LoggedInUser user);
}