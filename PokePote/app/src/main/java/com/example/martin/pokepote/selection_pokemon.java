package com.example.martin.pokepote;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class selection_pokemon extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_pokemon);

        Log.d("selection_pokemon","ok");
        Intent intent = getIntent();

        Bundle result = intent.getExtras();

        JSONObject pokedex = new JSONObject();
        JSONArray pokemons = new JSONArray();

        ArrayList<Pokemon> listP = new ArrayList<Pokemon>();

        try {
            pokedex = new JSONObject(result.getString("pokedex"));
            pokemons = pokedex.getJSONArray("pokemon");
            for (int i = 0; i < pokemons.length(); i++) {
                JSONObject pokemon = pokemons.getJSONObject(i);
                listP.add(new Pokemon("", ""));
            }
            for (int i = 0; i < pokemons.length(); i++) {
                JSONObject pokemon = pokemons.getJSONObject(i);
                if((Integer.parseInt(pokemon.getString("resource_uri").split("/")[3])-1)<720) {
                    listP.set(Integer.parseInt(pokemon.getString("resource_uri").split("/")[3]) - 1, new Pokemon(pokemon.getString("name"), "#" + pokemon.getString("resource_uri").split("/")[3]));
                }
            }
        }catch(Exception e){
            Log.d("Exception",e.toString());
        }

        //Création et initialisation de l'Adapter pour les personnes
        pokemon_adapter adapter = new pokemon_adapter(this, listP);

        //Récupération du composant ListView
        ListView list = (ListView)findViewById(R.id.ListPokemon);

        //Initialisation de la liste avec les données
        list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selection_pokemon, menu);
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
}
