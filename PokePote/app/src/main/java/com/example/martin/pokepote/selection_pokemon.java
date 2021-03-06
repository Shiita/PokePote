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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

    public EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_pokemon);
        //-------------------------------------------------
        Intent intent = getIntent();
        Bundle result = intent.getExtras();
        search = (EditText) findViewById(R.id.editText);
        //------------------------------------------------
        //Recupere tous les pokemons

        this.setTitle("Selection");

        try {
            pokedex = new JSONObject(result.getString("result"));//transforme le resultat de la requête en json
            pokemons = pokedex.getJSONArray("pokemon");//recupere les pokemons dans un tableau d'objets

            //remplissage de la liste de façon ordonnée
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

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                filtrer();
            }
        });
    }


    public void filtrer() {
        // retourner la chaine saisie par l'utilisateur
        String name = search.getText().toString();
        // créer une nouvelle liste qui va contenir la résultat à afficher
        ArrayList listPNew = new ArrayList();

        for (Pokemon pokemon : listP) {
            // si le nom du food commence par la chaine saisie , ajouter-le !
            if (pokemon.nom.toLowerCase().toString().startsWith(name.toLowerCase()) || pokemon.numero.startsWith(name)) {
                listPNew.add(pokemon);
            }
        }
        //vider la liste
        setListAdapter(null);
        pokemon_adapter adapter = new pokemon_adapter(this, listPNew);//Permet de définir comment afficher la liste
        //Initialisation de la liste avec les données
        setListAdapter(adapter);//Utilisation de l'adapter definit précédemment et affiche la liste
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
        Pokemon pokemon = (Pokemon) l.getAdapter().getItem(position);
        String urlString = getString(R.string.api_url) + "pokemon/" + pokemon.numero;//url à appeler pour récupérer les informations du pokémon dont l'id est égal à position
        String result = util.call(urlString);
        util.goToActivity(result,detail_pokemon.class,getApplicationContext());
    }
}
