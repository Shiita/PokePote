package com.example.martin.pokepote;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;

import static com.example.martin.pokepote.util.call;


public class detail_pokemon extends ActionBarActivity implements pokemon_infos.OnFragmentInteractionListener{

    public JSONObject pokemon;
    public JSONArray types;
    public JSONArray oeufs;
    public JSONArray capacites;
    public JSONArray descriptions;
    public JSONArray evols;
    public String res;
    public String typesStr;
    public String oeufsStr;
    public String capcitesStr;
    public double poids;
    public double taille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokemon);
        //-------------------------------------------------
        Intent intent = getIntent();
        Bundle result = intent.getExtras();
        DecimalFormat df = new DecimalFormat("#######0.0");
        //-------------------------------------------------
        //permet de recuperer les infos du pokemon.
        TextView PokeName = (TextView) findViewById(R.id.PokeName);
        ImageView PokeImg = (ImageView) findViewById(R.id.PokeImg);
        Button evolButton = (Button) findViewById(R.id.btnEvols);
        TextView PokeDes = (TextView) findViewById(R.id.PokeDes);

        try {
            res = result.getString("result");
            pokemon = new JSONObject(res);
            types = pokemon.getJSONArray("types");
            oeufs = pokemon.getJSONArray("egg_groups");
            capacites = pokemon.getJSONArray("abilities");
            descriptions = pokemon.getJSONArray("descriptions");
            evols = pokemon.getJSONArray("evolutions");
            poids = pokemon.getInt("weight") * 0.1;
            taille = pokemon.getInt("height") * 0.1;

            this.setTitle(pokemon.getString("name") + " - Details");

            if(evols.length() == 0){
                evolButton.setEnabled(false);
                evolButton.setBackgroundColor(Color.parseColor("#4C5F041E"));
                evolButton.setTextColor(Color.parseColor("#ff3b0416"));
            }

            PokeName.setText("#" + pokemon.getString("national_id") + " " +  pokemon.getString("name"));
            util.showImg(getApplicationContext(),pokemon,PokeImg);

            String urlString = getString(R.string.api) + descriptions.getJSONObject(0).getString("resource_uri");
            JSONObject description = new JSONObject(util.call(urlString));
            PokeDes.setText(description.getString("description"));

            typesStr = util.arrayToString(types);
            oeufsStr = util.arrayToString(oeufs);
            capcitesStr = util.arrayToString(capacites);

            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("INFOS GENERALES",""), "INFOSGEN").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Species",pokemon.getString("species")), "espece").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Type(s)", typesStr), "types").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Abilities", capcitesStr), "capacite").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Weight", df.format(poids) + " kg"), "poids").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Height", df.format(taille) + " m"), "taile").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Gender Ratio", pokemon.getString("male_female_ratio")), "ratio").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Catch Rate", pokemon.getString("catch_rate") + "%"), "taux").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Egg Cycle", pokemon.getString("egg_cycles")), "cycle").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Egg Groups", oeufsStr), "oeufs").commit();

            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("",""), "blanck1").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("BASE STATS",""), "STATISTIQUES").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("HP", pokemon.getString("hp")), "pv").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Exp", pokemon.getString("exp")), "experience").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Speed", pokemon.getString("speed")), "vitesse").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Defense", pokemon.getString("defense")), "defense").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Attack", pokemon.getString("attack")), "attaque").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Sp. Def.", pokemon.getString("sp_def")), "defense_spe").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Sp. Atk.", pokemon.getString("sp_atk")), "attaque_spe").commit();

            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("",""), "blanck2").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("TYPE EFFECTIVENESS",""), "forcesfaiblesses").commit();
            for(int i=0;i<types.length();i++){
                JSONObject type = types.getJSONObject(i);
                int j = i + 2;
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance(type.getString("name").toUpperCase(), ""), "type" + i).commit();
                String url_string = getString(R.string.api) + type.getString("resource_uri");
                String r = util.call(url_string);
                type = new JSONObject(r);
                String inefficace = util.arrayToString(type.getJSONArray("ineffective"));
                String sanseffet = util.arrayToString(type.getJSONArray("no_effect"));
                String resistant = util.arrayToString(type.getJSONArray("resistance"));
                String superefficace = util.arrayToString(type.getJSONArray("super_effective"));
                String faible = util.arrayToString(type.getJSONArray("weakness"));
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("No effect",sanseffet), "sanseffet"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Ineffective",inefficace), "inefficace"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Weakness",faible), "faible"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Resistance",resistant), "resistant"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Super effective",superefficace), "supperefficace"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("",""), "blanck" + j ).commit();
            /**Elise**/
            }



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

    public void showAttacks(View view){
        util.goToActivity(res,attaques.class,getApplicationContext());
    }

    public void showEvolutions(View view){
        util.goToActivity(res,evolutions.class,getApplicationContext());
    }

    public void showDescriptions(View view){
        util.goToActivity(res,descriptions.class,getApplicationContext());
    }

}

