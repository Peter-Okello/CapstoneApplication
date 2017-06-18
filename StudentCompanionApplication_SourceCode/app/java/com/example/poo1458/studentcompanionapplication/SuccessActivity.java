package com.example.poo1458.studentcompanionapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by poo1458 on 3/26/17.
 */
public class SuccessActivity extends AppCompatActivity
{
    public static final String USERNAME = "username";

    public String username;

    public static DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        final Intent intent = getIntent();
        username = intent.getStringExtra(USERNAME);



       /* // Setup Actionbar / Toolbar
        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Setup Navigation Drawer Layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });*/






      /*  Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

            }
        });*/




        Button auditButton = (Button) findViewById(R.id.jsonButton);

        auditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(SuccessActivity.this,"Fetching Class List",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SuccessActivity.this, AuditActivity.class);
                intent.putExtra(AuditActivity.USERNAME, username);
                startActivity(intent);
            }
        });

        Button transcriptButton = (Button) findViewById(R.id.transcript_button);
         transcriptButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view)
             {
                 Toast.makeText(SuccessActivity.this,"Preparing Transcript",Toast.LENGTH_LONG).show();
                 Intent transcriptIntent = new Intent(SuccessActivity.this,TranscriptClass.class);
                 transcriptIntent.putExtra(TranscriptClass.USERNAME,username);
                 startActivity(transcriptIntent);

             }
         });





    }


}
