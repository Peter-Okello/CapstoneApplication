package com.example.poo1458.studentcompanionapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity
{

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private EditText emailCredentials;
    private EditText passwordCredentials;
    public Button loginButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailCredentials = (EditText) findViewById(R.id.email);
        passwordCredentials = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);

    }


    public void checkLogin(View view)
    {
        //Get text from email and password fields
        final String email = emailCredentials.getText().toString();
        final String password = passwordCredentials.getText().toString();

        //Initialize AsyncLogin() class with email and password
        new AsyncLogin().execute(email,password);
        //attemptLogin();




    }

    /*private void attemptLogin() {

        // Reset errors.
        emailCredentials.setError(null);
        passwordCredentials.setError(null);

        // Store values at the time of the login attempt.
        String email = emailCredentials.getText().toString();
        String password = passwordCredentials.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password))
        {
            passwordCredentials.setError(getString(R.string.error_invalid_password));
            focusView = passwordCredentials;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailCredentials.setError(getString(R.string.error_field_required));
            focusView = emailCredentials;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailCredentials.setError(getString(R.string.error_invalid_email));
            focusView = emailCredentials;
            cancel = true;
        }
        else if(!isDomainCorrect(email))
        {
            emailCredentials.setError(getString(R.string.error_invalid_email_domain));
            focusView = emailCredentials;
            cancel = true;
        }
        else if(emailContainSpaces(email))
        {
            emailCredentials.setError(getString(R.string.error_whitespace_inEmail));
            focusView = emailCredentials;
            cancel = true;
        }
        else if(!isPasswordValid(password))
        {
            passwordCredentials.setError(getString(R.string.error_incorrect_password));
            focusView = passwordCredentials;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            new AsyncLogin().execute(email, password);
        }
    }*/

   /* private boolean isEmailValid(String email)
    {

        //TODO: Replace this with your own logic
        return email.contains("cav7157@penguin.lhup.edu");

    }

    private boolean isDomainCorrect(String email)
    {
        return email.contains("lhup.edu");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic

        return password.contains("password");
    }

    private boolean emailContainSpaces(String email)
    {
        return /*email.contains("\\s") || email.contains("\\n") || email.contains(" ");
    }
    */

    private class AsyncLogin extends AsyncTask<String, String, String>
    {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        HttpURLConnection connection;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //What will be displayed on UI thread
            progressDialog.setMessage("\t Loading.....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                //The URL address where the PHP file resides
                url = new URL("http://ec2-35-167-137-48.us-west-2.compute.amazonaws.com/login.inc.php");

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
                return "exception at URL";
            }

            try
            {
                //Setup HttpURLConnection class to send and receive data from php and mysql

                connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setRequestMethod("POST");


                //handle both send and receive requests
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Append parameters to URL
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                //Open connection for sending data
                connection.connect();
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                bufferedWriter.write(query);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();


            }
            catch (IOException e1)
            {
                e1.printStackTrace();
                return "exception at credential parse";
            }

            try
            {
                int response_code = connection.getResponseCode();

                //Check if connection was successful
                // if(response_code == HttpURLConnection.HTTP_OK)
                {

                    //Read data sent from server
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line = bufferedReader.readLine()) != null)
                    {
                        result.append(line);
                    }

                    //Pass data to onPostExecute method
                    return (result.toString());

                }
//                else
//                {
//                    return("unsuccessful");
//                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return "exception from server communication";
            }
            finally
            {
                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            //this method will be running on UI thread

            progressDialog.dismiss();




            if (s.equalsIgnoreCase("true"))
            {
                //Here we launch another activity if we get a successful login

                Intent intent = new Intent(MainActivity.this,SuccessActivity.class);
                intent.putExtra(SuccessActivity.USERNAME, emailCredentials.getText());
                startActivity(intent);
                MainActivity.this.finish();
            }
            else if(s.equalsIgnoreCase("exception at URL"))
            {
                Toast.makeText(MainActivity.this,"Error Occurred. URL error.",Toast.LENGTH_LONG).show();
            }
            else if(s.equalsIgnoreCase("exception at credential parse"))
            {
                Toast.makeText(MainActivity.this,"Error Occurred. Credential error.",Toast.LENGTH_LONG).show();
            }
            else if(s.equalsIgnoreCase("unsuccessful"))
            {
                Toast.makeText(MainActivity.this,"Error Occurred. Unsuccessful connection",Toast.LENGTH_LONG).show();
            }
           else
            {
                //Display Error message if username and password do not match
                Toast.makeText(MainActivity.this,"Invalid username or password credentials",Toast.LENGTH_LONG).show();
            }

        }
    }

}
