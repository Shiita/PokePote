package com.example.martin.pokepote;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
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
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import static com.example.martin.pokepote.util.call;

//--------------------------------------//
//                                      //
//  Affichage des details d'un pokemon  //
//                                      //
//--------------------------------------//


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

    public float steel;
    public float ghost;
    public float rock;
    public float ground;
    public float poison;
    public float psychic;
    public float fairy;
    public float grass;
    public float dragon;
    public float bug;
    public float flying;
    public float fighting;
    public float ice;
    public float fire;
    public float water;
    public float electric;
    public float normal;
    public float dark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokemon);

        //-------------------------------------------------------------//
        //                      Initialisation                         //

        Intent intent = getIntent();
        Bundle result = intent.getExtras();
        DecimalFormat df = new DecimalFormat("#######0.0");
        TextView PokeName = (TextView) findViewById(R.id.PokeName);
        ImageView PokeImg = (ImageView) findViewById(R.id.PokeImg);
        Button evolButton = (Button) findViewById(R.id.btnEvols);
        TextView PokeDes = (TextView) findViewById(R.id.PokeDes);

        //-------------------------------------------------------------//


        try {

            //-------------------------------------------------------------------//
            //  Recuperation des informations concernant le pokemon selectionne  //

            res = result.getString("result");
            pokemon = new JSONObject(res);
            types = pokemon.getJSONArray("types");
            oeufs = pokemon.getJSONArray("egg_groups");
            capacites = pokemon.getJSONArray("abilities");
            descriptions = pokemon.getJSONArray("descriptions");
            evols = pokemon.getJSONArray("evolutions");
            poids = pokemon.getInt("weight") * 0.1;
            taille = pokemon.getInt("height") * 0.1;

            //-------------------------------------------------------------------//


            this.setTitle(pokemon.getString("name") + " - Details"); // Definie le titre de l'activity


            //-----------------------------------------------------------------------------------------//
            //          Verification de l'existence d'une ou plusieurs evolutions du pokemon           //
            //  Si ce n'est pas le cas le bouton evolutions est rendu indisponible pour l'utilisateur  //

            if(evols.length() == 0){
                evolButton.setEnabled(false);
                evolButton.setBackgroundColor(Color.parseColor("#4C5F041E"));
                evolButton.setTextColor(Color.parseColor("#ff3b0416"));
            }

            //-----------------------------------------------------------------------------------------//



            //----------------------------------------------------------------------------------------------//
            //                           Affichage des informations du pokemon                              //
            //  Utilisation d'un fragment atkDatas pour recuperer les informations generales sur l'attaque  //

            PokeName.setText("#" + pokemon.getString("national_id") + " " + pokemon.getString("name")); // Affichage du numero + nom du pokemon
            util.showImg(getApplicationContext(), pokemon, PokeImg); // Affichage de l'image du pokemmon


            //------------------------------------------//
            //  Affichage de la description du pokemon  //
            String urlString = getString(R.string.api) + descriptions.getJSONObject(0).getString("resource_uri");
            JSONObject description = new JSONObject(util.call(urlString));
            PokeDes.setText(description.getString("description"));
            //------------------------------------------//


            typesStr = util.arrayToString(types);
            oeufsStr = util.arrayToString(oeufs);
            capcitesStr = util.arrayToString(capacites);

            //---------------------------------------------//
            //  Affichage des infos generales  du pokemon  //

            
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("GENERAL INFOS",""), "INFOSGEN").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Species",pokemon.getString("species")), "espece").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Type(s)", typesStr), "types").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Abilities", capcitesStr), "capacite").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Weight", df.format(poids) + " kg"), "poids").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Height", df.format(taille) + " m"), "taile").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Gender Ratio", pokemon.getString("male_female_ratio")), "ratio").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Catch Rate", pokemon.getString("catch_rate") + "%"), "taux").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Egg Cycle", pokemon.getString("egg_cycles")), "cycle").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Egg Groups", oeufsStr), "oeufs").commit();
            //---------------------------------------------//


            //------------------------------------------//
            //  Affichage des statistiques  du pokemon  //
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("",""), "blanck1").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("BASE STATS",""), "STATISTIQUES").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("HP", pokemon.getString("hp")), "pv").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Exp", pokemon.getString("exp")), "experience").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Speed", pokemon.getString("speed")), "vitesse").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Defense", pokemon.getString("defense")), "defense").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Attack", pokemon.getString("attack")), "attaque").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Sp. Def.", pokemon.getString("sp_def")), "defense_spe").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("Sp. Atk.", pokemon.getString("sp_atk")), "attaque_spe").commit();
            //------------------------------------------//

            //-------------------------------------------------//
            //  Affichage des forces et faiblesses du pokemon  //
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("",""), "blanck2").commit();
            getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("TYPE EFFECTIVENESS",""), "forcesfaiblesses").commit();

            for(int i=0;i<types.length();i++){
                steel = 1;ghost = 1;rock = 1;
                ground = 1;poison = 1;psychic = 1;
                fairy = 1;grass = 1;dragon = 1;
                bug = 1;flying = 1;fighting = 1;
                ice = 1;fire = 1;water = 1;
                electric = 1;normal = 1;dark = 1;
                JSONObject type = types.getJSONObject(i);
                int j = i + 2;
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance(type.getString("name").toUpperCase(), ""), "type" + i).commit();
                String url_string = getString(R.string.api) + type.getString("resource_uri");
                String r = util.call(url_string);
                type = new JSONObject(r);
                JSONArray inefficaceArray = type.getJSONArray("ineffective");
                defineSensibility(inefficaceArray,1.5f);
                //JSONArray = util.arrayToString(type.getJSONArray("no_effect"));
                //JSONArray resistanceArray = type.getJSONArray("resistance");
                //defineSensibility(resistanceArray,1);
                JSONArray superefficaceArray = type.getJSONArray("super_effective");
                defineSensibility(superefficaceArray,0.25f);
                JSONArray faibleArray = type.getJSONArray("weakness");
                defineSensibility(faibleArray,2);
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("bug",String.valueOf(bug)), "bug"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("dark",String.valueOf(dark)), "dark" + i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("dragon",String.valueOf(dragon)), "dragon"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("electric",String.valueOf(electric)), "electric"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("fairy",String.valueOf(fairy)), "fairy" + i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("fighting",String.valueOf(fighting)), "fighting"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("fire",String.valueOf(fire)), "fire"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("flying",String.valueOf(flying)), "flying"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("ghost",String.valueOf(ghost)), "ghost"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("grass",String.valueOf(grass)), "grass"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("ground",String.valueOf(ground)), "ground" + i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("ice",String.valueOf(ice)), "ice"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("normal",String.valueOf(normal)), "normal"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("poison",String.valueOf(poison)), "poison"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("psychic",String.valueOf(psychic)), "psychic"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("rock",String.valueOf(rock)), "rock" + i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("steel",String.valueOf(steel)), "steel" + i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("water",String.valueOf(water)), "water"+ i).commit();
                getFragmentManager().beginTransaction().add(findViewById(R.id.datas).getId(), pokemon_infos.newInstance("",""), "blanck" + j ).commit();
            }
            //-------------------------------------------------//

            //----------------------------------------------------------------------------------------------//



        }catch(Exception e){
            Log.d("Exception", e.toString());
        }
    }


    public void defineSensibility(JSONArray array, float f){
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object =  new JSONObject(array.getString(i));
                switch (object.getString("name")) {
                    case "steel" : steel = steel * f;break;
                    case "ghost" : ghost = ghost * f;break;
                    case "rock" : rock = rock * f;break;
                    case "ground" : ground =  ground * f;break;
                    case "poison" : poison = poison * f;break;
                    case "psychic" : psychic = psychic * f;break;
                    case "fairy" : fairy = fairy * f;break;
                    case "grass" : grass = grass * f;break;
                    case "dragon" : dragon = dragon * f;break;
                    case "bug" : bug = bug * f;break;
                    case "flying" : flying = flying * f;break;
                    case "fighting" : fighting = fighting * f;break;
                    case "ice" : ice = ice * f;break;
                    case "fire" : fire = fire * f;break;
                    case "water" : water = water * f;break;
                    case "electric" : electric = electric * f;break;
                    case "normal" : normal = normal * f;break;
                    case "dark" : dark = dark * f;break;
                }
            }
        }catch(Exception e){
            System.out.println(e.toString());
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


    //------------------------------------------------------//
    //                                                      //
    //  Evenement lorsque l'on appui sur un boutons du bas  //
    //                                                      //
    //------------------------------------------------------//

    public void showAttacks(View view){
        util.goToActivity(res,attaques.class,getApplicationContext()); //appel a l'activity du attaques du pokemon
    }

    public void showEvolutions(View view){
        util.goToActivity(res,evolutions.class,getApplicationContext()); //appel a l'activity des evolutions du pokemon
    }

    public void showDescriptions(View view){
        util.goToActivity(res,descriptions.class,getApplicationContext()); //appel a l'activity des descriptions du pokemon
    }

    public void returnToList(View view){
        String urlString = getString(R.string.api_url) + "pokedex/1"; //url à appeler pour récupérer tous les pokemon
        String result = util.call(urlString);
        util.goToActivity(result,selection_pokemon.class,getApplicationContext());
    }

}

