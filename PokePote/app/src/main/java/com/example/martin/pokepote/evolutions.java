package com.example.martin.pokepote;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;


//-----------------------------------------//
//                                         //
//  Affichage des evolutions d'un pokemon  //
//                                         //
//-----------------------------------------//

public class evolutions extends ActionBarActivity {

    public JSONObject pokemon;
    public String res;
    public JSONObject evol1;
    public JSONObject poke_evol1;
    public JSONObject poke_evol2;
    public JSONObject evol2;
    public String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evolutions);

        //-------------------------------------------------------------//
        //                      Initialisation                         //
        Intent intent = getIntent();
        Bundle result = intent.getExtras();
        TextView PokeName = (TextView) findViewById(R.id.PokeName);
        ImageView PokeImg = (ImageView) findViewById(R.id.PokeImg);
        TextView Evol1Name = (TextView) findViewById(R.id.Evol1Name);
        ImageView Evol1Img = (ImageView) findViewById(R.id.Evol1Img);
        TextView Evol2Name = (TextView) findViewById(R.id.Evol2Name);
        ImageView Evol2Img = (ImageView) findViewById(R.id.Evol2Img);
        TextView Transition1Text = (TextView) findViewById(R.id.Trans1Text);
        TextView Transition2Text = (TextView) findViewById(R.id.Trans2Text);
        LinearLayout Transition2 = (LinearLayout) findViewById(R.id.Trans2);
        LinearLayout Evol2 = (LinearLayout) findViewById(R.id.Evol2);
        TextView Transition3Text = (TextView) findViewById(R.id.Trans3Text);
        LinearLayout Transition3 = (LinearLayout) findViewById(R.id.Trans3);
        LinearLayout Evol3 = (LinearLayout) findViewById(R.id.Evol3);
        TextView Evol3Name = (TextView) findViewById(R.id.Evol3Name);
        ImageView Evol3Img = (ImageView) findViewById(R.id.Evol3Img);

        //-------------------------------------------------------------//


        try {


            res = result.getString("result");
            pokemon = new JSONObject(res);
            evol1 = pokemon.getJSONArray("evolutions").getJSONObject(0);
            String urlString = getString(R.string.api) + evol1.getString("resource_uri");
            poke_evol1 = new JSONObject(util.call(urlString));

            this.setTitle(pokemon.getString("name") + " - Evolutions"); // Definie le titre de l'activity

            if(evol1.getString("method").equals("level_up")){
                string = "Level : " + evol1.getString("level");
            }else{
                string = "Méthode : " + evol1.getString("method");

            }

            //------------------------------------------------------------------------------------------------//
            //                                         Afficher le pokemon                                    //
            PokeName.setText("#" + pokemon.getString("national_id") + " " +  pokemon.getString("name"));
            util.showImg(getApplicationContext(),pokemon,PokeImg);
            //------------------------------------------------------------------------------------------------//



            //------------------------------------------------------------------------------------------------//
            //                                  Afficher la première évolution                                //
            Transition1Text.setText(string);
            Evol1Name.setText("#" + poke_evol1.getString("national_id") + " " + poke_evol1.getString("name"));
            util.showImg(getApplicationContext(),poke_evol1,Evol1Img);
            //------------------------------------------------------------------------------------------------//



            if(poke_evol1.getJSONArray("evolutions").length() != 0) { // Verifier s'il a une deuxième evolution
                poke_evol2 = util.showEvolutions(getApplicationContext(),poke_evol1,Transition2,Evol2,Transition2Text,Evol2Name,Evol2Img); //Afficher la deuxieme evolution
                if(poke_evol2.getJSONArray("evolutions").length() != 0){ // Verifier s'il a une troisieme evolution
                    util.showEvolutions(getApplicationContext(),poke_evol2,Transition3,Evol3,Transition3Text,Evol3Name,Evol3Img); // Afficher la troisieme evolution
                }
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

    //------------------------------------------------------//
    //                                                      //
    //  Evenement lorsque l'on appui sur un boutons du bas  //
    //                                                      //
    //------------------------------------------------------//

    public void showPresentation(View view){
        util.goToActivity(res,detail_pokemon.class,getApplicationContext()); //appel à l'activity du detail du pokemon
    }

    public void showAttacks(View view){
        util.goToActivity(res,attaques.class,getApplicationContext()); //appel à l'activity des attaques du pokemon
    }

    public void showDescriptions(View view){
        util.goToActivity(res,descriptions.class,getApplicationContext()); //appel à l'activity des descriptions du pokemon
    }
}
