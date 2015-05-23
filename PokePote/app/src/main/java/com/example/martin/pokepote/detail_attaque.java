package com.example.martin.pokepote;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;

//---------------------------------------//
//                                       //
//  Affichage des details d'une attaque  //
//                                       //
//---------------------------------------//


public class detail_attaque extends ActionBarActivity implements pokemon_infos.OnFragmentInteractionListener {

    public JSONObject attaque;
    public String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_attaque);

        //-------------------------------------------------------------//
        //                      Initialisation                         //

        Intent intent = getIntent();
        Bundle result = intent.getExtras();
        TextView AtkName = (TextView) findViewById(R.id.AtkName);
        TextView AtkDes = (TextView) findViewById(R.id.AtkDes);

        //-------------------------------------------------------------//

        try {

            //-----------------------------------//
            //     Recuperation de l'attaque     //

            res = result.getString("result");
            attaque = new JSONObject(res);

            //-----------------------------------//


            this.setTitle(attaque.getString("name") + " - Details"); // Definie le titre de l'activity

            //----------------------------------------------------------------------------------------------//
            //                         Affichage des informations de l'attaque                              //
            //  Utilisation d'un fragment atkDatas pour recuperer les informations generales sur l'attaque  //

            AtkName.setText("#" + attaque.getString("id") + " " +  attaque.getString("name")); // Affichage du numero + nom de l'attaque
            AtkDes.setText(attaque.getString("description")); // Affichage de la description de l'attaque

            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("",""), "blank").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("GENERAL INFORMATIONS",""), "INFOSGEN").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("Category",attaque.getString("category")), "categorie").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("PP", attaque.getString("pp")), "pp").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("Power", attaque.getString("power")), "puissance").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("Accuracy", attaque.getString("accuracy")), "precision").commit();

            //----------------------------------------------------------------------------------------------//

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void returnToList(View view){
        String urlString = getString(R.string.api_url) + "pokedex/1"; //url à appeler pour récupérer tous les pokemon
        String result = util.call(urlString);
        util.goToActivity(result,selection_pokemon.class,getApplicationContext());
    }
}
