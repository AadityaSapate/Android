package com.example.adityasapate.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 *
 */
class MainActivity extends AppCompatActivity {

    EditText edittext ;
    TextView textview2;

    public void find(View view)
    {
       Log.i("input", edittext.getText().toString());

        DownloadTask task = new DownloadTask();
        String result = "";
        try {
            result = task.execute("http://samples.openweathermap.org/data/2.5/weather?q="+edittext.getText().toString()+"&appid=b1b15e88fa797225412429c1c50c122a1" ).get();
        } catch (Exception e) {
            System.out.print(e);
        }

    }


    private Bundle savedInstanceState;

    class DownloadTask extends AsyncTask<String, Void, String > {


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            URL url;
            HttpURLConnection connection;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result = result + current;
                    data = reader.read();
                }

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                return "failed";
            }

            return null;
        }

        @Override
        public void onPostExecute(String result) {
             String msg = "";
            super.onPostExecute(result);
           try {
               JSONObject Jobject = new JSONObject(result);
               String info = Jobject.getString("weather");
               Log.i("weather", info);
               JSONArray array = new JSONArray(info);
               for (int i = 0; i < array.length(); i++) {

                   JSONObject JPart = array.getJSONObject(i);

                   String main = JPart.getString("main");
                   String des = JPart.getString("description");
                  if(main != "" && des != "")
                     {
                        msg += main + des;
                     }
                     if(msg != ""){
                         textview2.setText(msg);
                     }
               }


        } catch (JSONException e) {
               e.printStackTrace();
           }
        }}


        @Override
        public void onCreate(Bundle savedInstanceState) {
            this.savedInstanceState = savedInstanceState;
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            edittext = (EditText)findViewById(R.id.editText);

            textview2 = (TextView) findViewById(R.id.textView2);

        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }