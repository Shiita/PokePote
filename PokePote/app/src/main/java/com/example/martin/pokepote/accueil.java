package com.example.martin.pokepote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//------------------------------------------------------------//
//                                                            //
//                  Activity d'accueil.                       //
//  Image Pokepote d'attente pour charger tous les pokemons.  //
//                                                            //
//------------------------------------------------------------//

public class accueil extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WifiManager WM = (WifiManager)getSystemService(Context.WIFI_SERVICE);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        this.setTitle("Accueil");

        //si connexion wifi, affichage de la liste des pokémons ; sinon affichage d'un message
        if(netCheckin()) {
        //if(WM.) {
            getPokeInfos();
        }else{
            //Toast.makeText(getApplicationContext(), "Connexion failed", Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Wifi network is disabled, please enabled it.")
                    .setCancelable(false)
                    .setPositiveButton("Enable Wifi", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));

                            finish();
                        }
                    })
                    .setNeutralButton("Enable Data", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));

                            finish();
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(Intent.ACTION_MAIN);
                            i.addCategory(Intent.CATEGORY_HOME);
                            startActivity(i);
                            finish();
                        }
                    })
                    .show();
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

    //------------------------------------------------------------------------------------------//
    //                                                                                          //
    //  Check data connexion                                                                    //
    //                                                                                          //
    //------------------------------------------------------------------------------------------//

    private boolean netCheckin() {
        try {
            ConnectivityManager nInfo = (ConnectivityManager) getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            nInfo.getActiveNetworkInfo().isConnectedOrConnecting();

            Log.d("accueil.java ", "Net avail:"
                    + nInfo.getActiveNetworkInfo().isConnectedOrConnecting());
            ConnectivityManager cm = (ConnectivityManager) getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                Log.d("accueil.java ", "Network available:true");
                return true;
            } else {
                Log.d("accueil.java ", "Network available:false");
                return false;
            }
        } catch (Exception e) {
            Log.d("accueil.java ", "Run exception : " + e);
            return false;
        }
    }
}
