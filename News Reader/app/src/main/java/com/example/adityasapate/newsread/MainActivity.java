package com.example.adityasapate.newsread;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Map<Integer, String> articalUrls = new HashMap<Integer, String>();   // mapping id and url together
    Map<Integer, String> articaltitles = new HashMap<Integer, String>();
    ArrayList<Integer> articalids = new ArrayList<Integer>(); // declare array of int for ids
    SQLiteDatabase database;
    ListView list ;
    ArrayList<String> titles = new ArrayList<String>();    //Array for title and Urls
    ArrayList<String> urls = new ArrayList<String>();

    ArrayAdapter<String> adapter;  // for adding Items to listview


    public class Download extends AsyncTask<String, Void, String> {    // ASync thread


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            URL url;
            HttpURLConnection Connection;
            try {
                url = new URL(params[0]);
                Connection = (HttpURLConnection) url.openConnection();
                InputStream in = Connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                JSONArray Jarray = new JSONArray(result);  // result contains all the ids which are converted to array
                database.execSQL("DELETE FROM articles");


                for(int i=0; i<10 ;i++) {    //for loop for first 20 ids
                    String articalid = Jarray.getString(i);  // i'th id converted to string

                    //  Download Atask = new Download();

                    url = new URL("https://hacker-news.firebaseio.com/v0/item/" + articalid + ".json?print=pretty"); // execute the url directly which download a file having title and url objs
                    Connection = (HttpURLConnection) url.openConnection();
                    in = Connection.getInputStream();
                    reader = new InputStreamReader(in);
                    data = reader.read();
                    String content = "";  // contents containing the objs
                    while( data != -1)
                    {
                        char current = (char) data;
                        content += current;
                        data = reader.read();

                    }


                    JSONObject Jobj = new JSONObject(content); // make Jobj to access those objs

                    String Title = Jobj.getString("title"); // get title and url
                    String Url = Jobj.getString("url");

                    articalids.add(Integer.valueOf(articalid));
                    articalUrls.put(Integer.valueOf(articalid), Url);
                    articaltitles.put(Integer.valueOf(articalid), Title);

                    String sql = "INSERT INTO articles (articleid, articleurl, articletitle ) VALUES (?, ?, ?)"; // execute database command
                    SQLiteStatement statement = database.compileStatement(sql); // this converts string into sql statement
                    statement.bindString(1, articalid); // 1st column contain all values of ids
                    statement.bindString(2, Url);
                    statement.bindString(3, Title);
                    statement.execute();






                }




                return result;


            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //  upDateListView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


        list = (ListView) findViewById(R.id.list);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {     // when list item is tapped
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), ArticleActivity.class); // send to ArticleActivity
                i.putExtra("urlContent", urls.get(position)); // this this used in ArticleActivity

                startActivity(i);

                // web.loadUrl(urls.get(position));
                // list.setVisibility(View.INVISIBLE);

                // web.setVisibility(View.VISIBLE);
                //  Log.i("Working", urls.get(position));
            }
        });


        database = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null);  // create database

        // create table
        database.execSQL("CREATE TABLE IF NOT EXISTS articles ( id INTEGER PRIMARY KEY, articleid INTEGER, articleurl VARCHAR, articletitle VARCHAR, content VARCHAR)");

        // upDateListView();

        Download task = new Download();
        task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty"); // starts downloadind that downloads all the ids



        upDateListView(); // this method retrives data from database









    }
    public void upDateListView(){

        Cursor c = database.rawQuery("SELECT * FROM articles ORDER BY articleid DESC", null); // select from table

        int articleidIndex = c.getColumnIndex("articleid"); // index of current id column

        int urlIndex = c.getColumnIndex("articleurl");

        int titleIndex = c.getColumnIndex("articletitle");

        c.moveToFirst();
        titles.clear();
        urls.clear();
        while ( c != null) {

            titles.add(c.getString(titleIndex)); // adds the title name to which cursor is pointing at first it points to 1st then 2nd
            urls.add(c.getString(urlIndex));  // adds url to url array
            Log.i("articalids", Integer.toString(c.getInt(articleidIndex)));
            Log.i("urls", c.getString(urlIndex));
            Log.i("Titles", c.getString(titleIndex));

            c.moveToNext();  // increments cursor
        }
        adapter.notifyDataSetChanged(); // as new item added to title array adapter updates
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

