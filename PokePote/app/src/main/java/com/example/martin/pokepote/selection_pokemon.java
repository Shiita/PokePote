package com.example.martin.pokepote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//--------------------------//
//                          //
//  Selection d'un pokemon  //
//                          //
//--------------------------//


public class selection_pokemon extends ListActivity {

    public JSONObject pokedex;
    public JSONArray pokemons;

    public ArrayList<Pokemon> listP = new ArrayList<Pokemon>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_pokemon);
        //-------------------------------------------------
        Intent intent = getIntent();
        Bundle result = intent.getExtras();
        //------------------------------------------------
        //Recupere tous les pokemons

        this.setTitle("Selection");

        try {
            pokedex = new JSONObject(result.getString("result"));//transforme le resultat de la requête en json
            pokemons = pokedex.getJSONArray("pokemon");//recupere les pokemons dans un tableau d'objets

            //remplissage de la liste de façon ordonné
            for (int i = 0; i < pokemons.length(); i++) {
                JSONObject pokemon = pokemons.getJSONObject(i);
                String numero = pokemon.getString("resource_uri").split("/")[3];//recupere le nummero du pokemon
                if((Integer.parseInt(numero)-1)<720) { //Filtre les mega-evolutions
                    listP.add(new Pokemon(pokemon.getString("name").replaceFirst("[a-z]",String.valueOf(pokemon.getString("name").toCharArray()[0]).toUpperCase()),numero,getString(R.string.api_media) + numero + ".png"));//Ajoute le pokemon à la liste
                }
                Collections.sort(listP, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        Integer i1 = Integer.parseInt(((Pokemon) o1).getNumero());
                        Integer i2 = Integer.parseInt(((Pokemon) o2).getNumero());
                        return i1.compareTo(i2);
                    }

                });
            }

        }catch(Exception e){
            Log.d("Exception",e.toString());
        }

        //Création et initialisation de l'Adapter pour les pokemons
        pokemon_adapter adapter = new pokemon_adapter(this, listP);//Permet de définir comment afficher la liste
        //Initialisation de la liste avec les données
        setListAdapter(adapter);//Utilisationde l'adapter definit précédemment et affiche la liste
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

    //Est utilisé lorsque l'utilisateur clic sur un pokemon
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        position = position + 1;
        String urlString = getString(R.string.api_url) + "pokemon/" + position;//url à appeler pour récupérer les informations du pokémon dont l'id est égal à position
        String result = util.call(urlString);
        util.goToActivity(result,detail_pokemon.class,getApplicationContext());
    }
}
