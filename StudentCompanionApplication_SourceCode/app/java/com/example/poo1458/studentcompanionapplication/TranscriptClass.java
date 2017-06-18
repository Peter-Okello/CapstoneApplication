package com.example.poo1458.studentcompanionapplication;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by poo1458 on 5/4/17.
 */
public class TranscriptClass extends AppCompatActivity
{
    public static final String USERNAME = "username";
    String CLASS_DATA_URL = "http://ec2-35-167-137-48.us-west-2.compute.amazonaws.com/json_getTranscript_data.php";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ClassListItem> listItems;
    private String username;


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
                    JSONArray jsonArray = jsonObject.getJSONArray("transcript");

                    for (int i = 0; i < jsonArray.length();i++)
                    {
                        //traverse through the array looking for matches in the column fields and return all values matching 'Course_Code' & 'Course_Name'
                        JSONObject object = jsonArray.getJSONObject(i);
                        ClassListItem item = new ClassListItem(
                                object.getString("Course_Code"),
                                object.getString("Grade"));

                        //add items to the constructor defined in the ClassListItem class
                        listItems.add(item);

                    }

                    //wrap the contents of the ClassListItem class and send it to the adapter class where it will be placed in the appropriate position in the ViewHolder
                    adapter = new TranscriptAdapter(listItems,getApplicationContext());
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
