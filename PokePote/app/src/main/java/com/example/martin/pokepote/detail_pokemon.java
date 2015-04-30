package com.example.martin.pokepote;

import android.app.AlertDialog;
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


public class detail_pokemon extends ActionBarActivity implements pokemon_infos.OnFragmentInteractionListener{

    public JSONObject pokemon;
    public JSONArray types;
    public JSONArray descriptions;
    public String res;
    public String typesStr;
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

        try {
            res = result.getString("result");
            pokemon = new JSONObject(res);
            types = pokemon.getJSONArray("types");
            descriptions = pokemon.getJSONArray("descriptions");
            poids = pokemon.getInt("weight") * 0.1;
            taille = pokemon.getInt("height") * 0.1;

            PokeName.setText("#" + pokemon.getString("national_id") + " " +  pokemon.getString("name"));
            File path = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "iconDex");
            String url_img = getString(R.string.api_media) + pokemon.getString("national_id");
            File image = new File(path.getPath() + "/a" + url_img.substring(url_img.lastIndexOf("/") + 1));

            if (!image.exists()) {
                new DownloadImageTask(PokeImg,url_img, this);
            }else{
                PokeImg.setImageBitmap(BitmapFactory.decodeFile(image.getCanonicalPath()));
            }
            for(int i=0;i<types.length();i++){
                JSONObject type = types.getJSONObject(i);
                if(i == 0){
                    typesStr = type.getString("name");
                }else {
                    typesStr = type.getString("name") + ", " + typesStr;
                }
            }



            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("INFOS GENERALES",""), "INFOSGEN").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("espèce",pokemon.getString("species")), "espece").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("types", typesStr), "types").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("poids", df.format(poids) + " kg"), "poids").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("taille", df.format(taille) + " m"), "taile").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("ratio male/femelle", pokemon.getString("male_female_ratio")), "ratio").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("taux de capture", pokemon.getString("catch_rate") + "%"), "taux").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("cycle des oeufs", pokemon.getString("egg_cycles")), "cycle").commit();

            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("",""), "blanck1").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("STATISTIQUES",""), "STATISTIQUES").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("pv", pokemon.getString("hp")), "pv").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("expérience", pokemon.getString("exp")), "experience").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("vitesse", pokemon.getString("speed")), "vitesse").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("défense", pokemon.getString("defense")), "defense").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("attaque", pokemon.getString("attack")), "attaque").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("défense spéciale", pokemon.getString("sp_def")), "defense_spe").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("attaque spéciale", pokemon.getString("sp_atk")), "attaque_spe").commit();

            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("",""), "blanck2").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("DESCRIPTIONS",""), "DESCRIPTIONS").commit();
            for(int i=0;i<descriptions.length();i++){
                JSONObject description = descriptions.getJSONObject(i);
                String version = description.getString("name");
                String urlString = getString(R.string.api) + description.getString("resource_uri");
                new CallAPI(this,getFragmentManager(),version, findViewById(R.id.datas).getId(),"description").execute(urlString);
            }

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_pokemon, menu);
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
        Intent intent = new Intent(this,attaques.class);
        intent.putExtra("result", res);
        startActivity(intent);
    }
}

