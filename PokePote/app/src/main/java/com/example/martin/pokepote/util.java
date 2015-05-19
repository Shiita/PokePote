package com.example.martin.pokepote;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Martin on 05/05/2015.
 */


//--------------------------------------------------------//
//                                                        //
//  Fonctions utilisés plusieurs dans le reste du projet  //
//                                                        //
//--------------------------------------------------------//

public class util {

    //-------------------------------------------------//
    //                                                 //
    //  Changer un tableau en une chaine de caractere  //
    //                                                 //
    //-------------------------------------------------//

    public static String arrayToString(JSONArray array) {

        String string = "";
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if (i == 0) {
                    string = object.getString("name");
                } else {
                    string = object.getString("name") + ", " + string;
                }
            }
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return string;
    }



    //-----------------//
    //                 //
    //  Appel à l'api  //
    //                 //
    //-----------------//

    public static String call(String url) {

        String string = "";
        try {
            CallAPI callAPI = new CallAPI();
            callAPI.execute(url);
            string = callAPI.get();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return string;
    }



    //--------------------------------------------//
    //                                            //
    //  Appel à une activity passée en parametre  //
    //                                            //
    //--------------------------------------------//

    public static void goToActivity(String string, Class classe, Context context) {
        Intent intent = new Intent(context,classe);
        intent.putExtra("result",string);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    //----------------------//
    //                      //
    //  Afficher une image  //
    //                      //
    //----------------------//

    public static void showImg(Context context,JSONObject object,ImageView container) {
        try{
            File path = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "iconDex");
            String url_img = context.getString(R.string.api_media) + object.getString("national_id");
            File image = new File(path.getPath() + "/a" + object.getString("national_id") + ".png");

            if (!image.exists()) {
                new DownloadImageTask(container,url_img,context);
            }
            else{
                System.out.println("prout\n\nlaiyervfgilyearvgliyzervilvyu");
                container.setImageBitmap(BitmapFactory.decodeFile(image.getCanonicalPath()));
            }
        }catch(Exception e){
            Log.d("Exception",e.toString());
        }
    }


    //--------------------------------------------------------//
    //                                                        //
    //  Afficher l'evolution d'un pokemon passé en parametre  //
    //                                                        //
    //--------------------------------------------------------//


    public static JSONObject showEvolutions(Context context, JSONObject object1, LinearLayout linear1, LinearLayout linear2, TextView text1, TextView text2, ImageView image){

        JSONObject object3 = new JSONObject();

        try {
                JSONObject object2 = object1.getJSONArray("evolutions").getJSONObject(0);
                String urlString = context.getString(R.string.api) + object2.getString("resource_uri");
                object3 = new JSONObject(util.call(urlString));
                linear1.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.VISIBLE);
                String string = "";
                if (object2.getString("method").equals("level_up")) {
                    string = "Level : " + object2.getString("level");
                } else {
                    string = "Méthode : " + object2.getString("method");
                }
                text1.setText(string);
                text2.setText("#" + object3.getString("national_id") + " " + object3.getString("name"));
                showImg(context, object3, image);
        }catch (Exception e){
            Log.e("Exception",e.toString());
        }
        return object3;
    }

    //------------------------------------------------//
    //                                                //
    //  Converti le résultat d'une requête en string  //
    //                                                //
    //------------------------------------------------//

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }
}
