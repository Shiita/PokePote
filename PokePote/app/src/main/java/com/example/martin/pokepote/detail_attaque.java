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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;


public class detail_attaque extends ActionBarActivity implements pokemon_infos.OnFragmentInteractionListener {




    public JSONObject attaque;
    public String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_attaque);

        //-------------------------------------------------
        Intent intent = getIntent();
        Bundle result = intent.getExtras();
        //-------------------------------------------------
        //permet de recuperer les infos du pokemon.
        TextView AtkName = (TextView) findViewById(R.id.AtkName);
        TextView AtkDes = (TextView) findViewById(R.id.AtkDes);

        try {
            res = result.getString("result");
            attaque = new JSONObject(res);

            this.setTitle(attaque.getString("name") + " - Details");

            AtkName.setText("#" + attaque.getString("id") + " " +  attaque.getString("name"));
            AtkDes.setText(attaque.getString("description"));

            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("",""), "blank").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("INFOS GENERALES",""), "INFOSGEN").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("catégorie",attaque.getString("category")), "categorie").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("pp", attaque.getString("pp")), "pp").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("puissance", attaque.getString("power")), "puissance").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.atkdatas).getId(), pokemon_infos.newInstance("précision", attaque.getString("accuracy")), "precision").commit();

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
}
