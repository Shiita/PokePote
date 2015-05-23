package com.example.martin.pokepote;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//---------------------------------------------------------------//
//                                                               //
//  Activity presentant la liste des descriptions d'un pokemon.  //

//---------------------------------------------------------------//


public class descriptions extends ListActivity {

    public String res;
    public JSONObject pokemon;
    public JSONArray descriptions;
    public JSONObject description;
    public String version;
    public List<Description> list = new ArrayList<Description>();

    //-------------------------------------------------------//
    //                                                       //
    //  Remplissage de la liste des descriptions du pokemon  //
    //                                                       //
    //-------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptions);

        //-------------------------------------------------------------//
        //                      Initialisation                         //

        Intent intent = getIntent();
        Bundle result = intent.getExtras();
        TextView PokeName = (TextView) findViewById(R.id.PokeName);
        ImageView PokeImg = (ImageView) findViewById(R.id.PokeImg);
        Button evolButton = (Button) findViewById(R.id.btnEvols);

        //-------------------------------------------------------------//

        try {

            //--------------------------------------------------//
            //     Recuperation des descriptions du pokemon     //


            res = result.getString("result");
            pokemon = new JSONObject(res);
            descriptions = pokemon.getJSONArray("descriptions");

            //--------------------------------------------------//

            this.setTitle(pokemon.getString("name") + " - Descriptions"); // Definie le titre de l'activity

            //-----------------------------------------------------------------------------------------//
            //          Verification de l'existence d'une ou plusieurs evolutions du pokemon           //
            //  Si ce n'est pas le cas le bouton evolutions est rendu indisponible pour l'utilisateur  //


            if(pokemon.getJSONArray("evolutions").length() == 0){
                evolButton.setEnabled(false);
                evolButton.setBackgroundColor(Color.parseColor("#4C5F041E"));
                evolButton.setTextColor(Color.parseColor("#ff3b0416"));
            }

            //-----------------------------------------------------------------------------------------//


            //-------------------------------------------------------------------------------------------//
            //                         Affichage des informations du pokemon                             //

            PokeName.setText("#" + pokemon.getString("national_id") + " " +  pokemon.getString("name")); // Affichage du numero + nom du pokemon
            util.showImg(getApplicationContext(),pokemon,PokeImg); // Affichage de l'image du pokemmon

            //------------------------------------------------------------------------------------------//


            //----------------------------------------------------------------------------------------------------------------//
            //                         Ajout des descriptions a la liste de descriptions du pokemon                           //


            for(int i=0;i<descriptions.length();i++){
                String urlString = getString(R.string.api) + descriptions.getJSONObject(i).getString("resource_uri");
                description = new JSONObject(util.call(urlString));
                version = description.getJSONArray("games").getJSONObject(0).getString("name");
                list.add(new Description(description.getString("name").split("_")[2],description.getString("description"),version));
            }

            //----------------------------------------------------------------------------------------------------------------//


            //---------------------------------------------------------------------------------------------//
            //  Ordonnancement des descriptions en fonction de la generation de la description du pokemon  //

            Collections.sort(list, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Integer i1 = Integer.parseInt(((Description) o1).generation);
                    Integer i2 = Integer.parseInt(((Description) o2).generation);
                    return i1.compareTo(i2);
                }
            });

            //---------------------------------------------------------------------------------------------//

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }

        //--------------------------------------------------------------------------//
        //           Assignement d'un description_adapter a la liste                //
        //  Definie les proprietes d'affichage a liste des descriptions du pokemon  //

        description_adapter adapter = new description_adapter(this, list);
        setListAdapter(adapter);

        //--------------------------------------------------------------------------//

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
        util.goToActivity(res,detail_pokemon.class,getApplicationContext()); //appel a l'activity du detail du pokemon
    }

    public void showAttacks(View view){
        util.goToActivity(res,attaques.class,getApplicationContext()); //appel a l'activity des attaques du pokemon
    }

    public void showEvolutions(View view){
        util.goToActivity(res,evolutions.class,getApplicationContext()); //appel a l'activity des evolutions du pokemon
    }

    public void returnToList(View view){
        String urlString = getString(R.string.api_url) + "pokedex/1"; //url a  appeler pour recuperer tous les pokemon
        String result = util.call(urlString);
        util.goToActivity(result,selection_pokemon.class,getApplicationContext());
    }
}
