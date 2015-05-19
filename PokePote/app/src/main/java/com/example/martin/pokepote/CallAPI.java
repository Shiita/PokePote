package com.example.martin.pokepote;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

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

//--------------------------------------------------------------------------------------------------------//
//                                                                                                        //
//                     Classe nous permettant d'effectuer tous les appels à l'api                         //
//  Nous devons utiliser AsyncTask car cette fonction est lourde et doit être fait de manière asynchrone  //                                                           //
//                                                                                                        //
//--------------------------------------------------------------------------------------------------------//


public class CallAPI extends AsyncTask<String, String, String> {

    public CallAPI(){

    }

    @Override
    protected String doInBackground(String... params) {

        String urlString = params[0];
        String resultToDisplay = null;
        InputStream in = null;

        // HTTP Get
        try {
            URL url = new URL(urlString);//Convertit la variable String en url que pour qu'on puisse la questionner.
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//Appel sur l'url transmis.
            in = new BufferedInputStream(urlConnection.getInputStream());//Récupération du résultat de la requête
            resultToDisplay = util.convertInputStreamToString(in);//Fonction qui permet de convertir le résultat de la requête en string
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //return resultToDisplay;
        }
        return resultToDisplay;
    }

    protected void onPostExecute(String result) {
        super .onPostExecute(result);
    }
}
