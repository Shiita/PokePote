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

//Nous devons utiliser AsyncTask car cette fonction est lourde et doit être fait de manière asynchrone
public class CallAPI extends AsyncTask<String, String, String> {

    public Context context;
    public Class classe;
    public int ret = 0;
    public int id;
    public String string;
    public String attribut;
    public FragmentManager manager;

    //Constructeur de la classe
    public CallAPI(Context aContext, Class aClasse) {
        context = aContext;
        classe = aClasse;
    }

    public CallAPI(Context aContext, FragmentManager aManager, String aString, int aId, String aAttribut){
        manager = aManager;
        context = aContext;
        string = aString;
        attribut = aAttribut;
        ret = 1;
        id = aId;
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
            URL url = new URL(urlString);//Convertit la variable String en url que pour qu'on puisse la questionner.
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//Appel sur l'url transmis.
            in = new BufferedInputStream(urlConnection.getInputStream());//Récupération du résultat de la requête
            resultToDisplay = convertInputStreamToString(in);//Fonction qui permet de convertir le résultat de la requête en string
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            //return resultToDisplay;
        }
        return resultToDisplay;
    }

    protected void onPostExecute(String result) {

        if(ret == 0) {
            Intent intent = new Intent(context, classe);
            intent.putExtra("result", result);//Permet de transmettre le résultat de la requête vers la nouvelle activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Permet d'ouvrir une nouvelle activity sans être dans une activity
            context.startActivity(intent);//Utilise le context de l'activity qui execute cette classe et déclenche l'ouverture de la nouvelle activity défini par la classe donnée dans le controlleur
        }else{
            try{
                JSONObject attr = new JSONObject(result);
                String PokeAttr = attr.getString(attribut);
                manager.beginTransaction().add(id, pokemon_infos.newInstance(string, PokeAttr), string).commit();
            }catch(Exception e){
                Log.d("Error",e.toString());
            }

        }
    }
}

