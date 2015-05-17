package com.example.martin.pokepote;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//------------------------------------------------------------//
//                                                            //
//                  Activity d'accueil.                       //
//  Image Pokepote d'attente pour charger tous les pokemons.  //
//                                                            //
//------------------------------------------------------------//

public class accueil extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        this.setTitle("Accueil");
        getPokeInfos();
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



    //------------------------------------------------------------------------------------------//
    //                                                                                          //
    //  Recuperation de la liste des pokemons et appel à l'activity de la sélection du pokemon  //
    //                                                                                          //
    //------------------------------------------------------------------------------------------//

    public void getPokeInfos() {
            String urlString = getString(R.string.api_url) + "pokedex/1"; //url à appeler pour récupérer tous les pokemon
            String result = util.call(urlString); //appel à l'api
            util.goToActivity(result,selection_pokemon.class,getApplicationContext()); //appel à l'activity de la sélection du pokemon
   }
}
