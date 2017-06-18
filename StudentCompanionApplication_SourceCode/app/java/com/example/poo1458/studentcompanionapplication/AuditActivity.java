package com.example.poo1458.studentcompanionapplication;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by poo1458 on 4/15/17.
 */

public class AuditActivity extends AppCompatActivity
{
    public static final String USERNAME = "username";
    private static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    SwipeRefreshLayout mSwipeRefreshLayout;

    String CLASS_DATA_URL = "http://ec2-35-167-137-48.us-west-2.compute.amazonaws.com/json_get_data.php";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ClassListItem> listItems;
    private String username;
    private ClassListItem classListItem;
    private ClassAdapter classAdapter;




    SearchView  searchView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.audit_layout);

        final Intent intent = getIntent();
        username = intent.getStringExtra(USERNAME);

        recyclerView = (RecyclerView) findViewById((R.id.recyclerView));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





        listItems = new ArrayList<>();

        /*for(int i = 0; i < 10; i++)
        {
            ClassListItem listItem = new ClassListItem(
                    "heading" +(i+1), "This is some sample text"
            );

            listItems.add(listItem);
        }

        adapter = new ClassAdapter(listItems, this);

        recyclerView.setAdapter(adapter);*/

        loadRecyclerView();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {

                return;
            }
        });
        //Make call to AsyncTask
    return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //adds item to the action bar
        getMenuInflater().inflate(R.menu.search_main, menu);

        //Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) AuditActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if(searchItem != null)
        {
            searchView = (SearchView) searchItem.getActionView();
        }
        if(searchView != null)
        {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(AuditActivity.this.getComponentName()));
            searchView.setIconified(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    //Every time the user presses the search button on keypad an Activity is recreated which in turn calls this function


    @Override
    protected void onNewIntent(Intent intent)
    {
        //Get search query and create object of class AsyncFetch
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if(searchView != null)
            {
                searchView.clearFocus();
            }
            new AsyncFetch(query).execute();
        }
    }

    //Create the AsyncFetch Class
    private  class AsyncFetch extends AsyncTask<String,String,String>
    {
        ProgressDialog progressDialog =  new ProgressDialog(AuditActivity.this);

        HttpURLConnection connection;
        URL url = null;
        String searchQuery;

        public AsyncFetch(String searchQuery)
        {
            this.searchQuery = searchQuery;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            //method will be running on the user interface thread
            progressDialog.setMessage("\tSearching...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            try
            {
                //url where the search php file resides
                url = new URL("http://ec2-35-167-137-48.us-west-2.compute.amazonaws.com/class_search.php");

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
                return e.toString();
            }

            try
            {
                //Setup HttpURLConnection class to send and receive data from php and mysql

                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(CONNECTION_TIMEOUT);
                connection.setRequestMethod("POST");

                //setDoInput and setDoOutput to true as we send and receive data
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("searchQuery", searchQuery);

                String query = builder.build().getEncodedQuery();

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                bufferedWriter.write(query);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                connection.connect();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
                return e1.toString();
            }

            try
            {
                int response_code = connection.getResponseCode();

                //Check to see if a successful connection was made
                if(response_code == HttpURLConnection.HTTP_OK)
                {
                    //Read all the data sent from the server
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line = bufferedReader.readLine()) != null)
                    {
                        result.append(line);
                    }

                    //Pass the data to the onPostExecuteMethod
                    return (result.toString());
                }
                else
                {
                    return ("Connection error");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return e.toString();
            }
            finally
            {
                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            mSwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(s);

        }

        /* @Override
        protected void onPostExecute(String s)
        {

            //running on user interface thread
            progressDialog.dismiss();
            List<ClassListItem> classListItems = new ArrayList<>();

            progressDialog.dismiss();
            if(s.equals("no rows"))
            {
                Toast.makeText(AuditActivity.this,"Class could not be found",Toast.LENGTH_LONG).show();
            }
            else
            {
                try
                {
                    JSONArray jsonArray = new JSONArray(s);

                    //Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        ClassListItem listItem = new ClassListItem(s,s);

                        listItem.head = json_data.getString("Course_Code");
                        listItem.body = json_data.getString("Course_Name");

                    }
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    adapter = new ClassAdapter(listItems,getApplicationContext());
                    //classAdapter = new ClassAdapter(AuditActivity.this, classListItems);

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AuditActivity.this));

                }
                catch (JSONException e)
                {
                    Toast.makeText(AuditActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(AuditActivity.this,s.toString(),Toast.LENGTH_LONG).show();
                }

            }
            super.onPostExecute(s);
        }*/
    }





    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        TextView classNameTextView = (TextView) findViewById(R.id.classListName);
        String className = classNameTextView.getText().toString();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkedTextView1:
                if (checked)
                {
                    Toast.makeText(AuditActivity.this,className+" has been added to your fall schedule",Toast.LENGTH_LONG).show();
                }

                else
                {
                    Toast.makeText(AuditActivity.this,"Nothing",Toast.LENGTH_LONG).show();
                }

        }
    }

    private void loadRecyclerView()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        //process that will be running on UI thread
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();

        //Send 'GET' method to server and wait for a response
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, CLASS_DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();

                try {
                    //create JSON object
                    JSONObject jsonObject = new JSONObject(response);
                    //search server for JSON array called 'server response'
                    JSONArray jsonArray = jsonObject.getJSONArray("class_list");

                    for (int i = 0; i < jsonArray.length();i++)
                    {
                        //traverse through the array looking for matches in the column fields and return all values matching 'Course_Code' & 'Course_Name'
                        JSONObject object = jsonArray.getJSONObject(i);
                        ClassListItem item = new ClassListItem(
                                object.getString("Course_Code"),
                                object.getString("Course_Name"));

                        //add items to the constructor defined in the ClassListItem class
                        listItems.add(item);

                    }

                    //wrap the contents of the ClassListItem class and send it to the adapter class where it will be placed in the appropriate position in the ViewHolder
                    adapter = new ClassAdapter(listItems,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }



            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


}
