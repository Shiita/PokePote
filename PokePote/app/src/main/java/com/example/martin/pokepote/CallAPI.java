package com.example.martin.pokepote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Martin on 21/03/2015.
 */
public class CallAPI extends AsyncTask<String, String, String> {

    public Context context;
    public Class classe;

    public CallAPI(Context aContext, Class aClasse) {
        context = aContext;
        classe = aClasse;
    }

    private static String convertInputStreamToString(InputStream inputStream)throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    @Override
    protected String doInBackground(String... params) {

        String urlString=params[0];
        String resultToDisplay = null;
        InputStream in = null;

        // HTTP Get
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            resultToDisplay = convertInputStreamToString(in);
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            return resultToDisplay;
        }
        return resultToDisplay;
    }

    protected void onPostExecute(String result) {

        Intent intent = new Intent(context,classe);

        intent.putExtra("result",result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}

