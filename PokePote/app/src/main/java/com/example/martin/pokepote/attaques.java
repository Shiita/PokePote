package com.example.martin.pokepote;

import android.app.ListActivity;
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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class attaques extends ListActivity {

    public String res;
    public JSONObject pokemon;
    public JSONArray attaques;
    public List<Attaque> list = new ArrayList<Attaque>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attaques);
        Intent intent = getIntent();
        Bundle result = intent.getExtras();
        TextView PokeName = (TextView) findViewById(R.id.PokeName);
        ImageView PokeImg = (ImageView) findViewById(R.id.PokeImg);


        try {
            res = result.getString("result");
            pokemon = new JSONObject(res);
            attaques = pokemon.getJSONArray("moves");


            PokeName.setText("#" + pokemon.getString("national_id") + " " +  pokemon.getString("name"));
            File path = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "iconDex");
            String url_img = getString(R.string.api_media) + pokemon.getString("national_id");
            File image = new File(path.getPath() + "/a" + url_img.substring(url_img.lastIndexOf("/") + 1));

            if (!image.exists()) {
                new DownloadImageTask(PokeImg,url_img, this);
            }else{
                PokeImg.setImageBitmap(BitmapFactory.decodeFile(image.getCanonicalPath()));
            }

            for(int i=0;i<attaques.length();i++){
                JSONObject attaque = attaques.getJSONObject(i);
                Log.d("attaque " + i,attaque.toString());
                String nom = attaque.getString("name");
                String learn_type = attaque.getString("learn_type");
                String level = "999";
                if(attaque.has("level")) {
                   level = attaque.getString("level");
                }
                //String numero = "0";
                String numero = attaque.getString("resource_uri").split("/")[4];
                list.add(new Attaque(numero,nom,learn_type,level));
            }

            Collections.sort(list, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Integer i1 = Integer.parseInt(((Attaque) o1).level);
                    Integer i2 = Integer.parseInt(((Attaque) o2).level);
                    return i1.compareTo(i2);
                }
            });

           for(Attaque atk : list){
               if(atk.level.equals("999")){
                   atk.level = "";
               }
           }

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }

        attaques_adapter adapter = new attaques_adapter(this, list);
        setListAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attaques, menu);
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String num_atk = list.get(position).numero;
        String urlString = getString(R.string.api_url) + "move/" + num_atk;//url à appeler pour récupérer les informations du pokémon dont l'id est égal à position
        new CallAPI(getApplicationContext(),detail_attaque.class).execute(urlString);//Execute l'url
    }

    public void showGenInfo(View view){
        Intent intent = new Intent(this,detail_pokemon.class);
        intent.putExtra("result", res);
        startActivity(intent);
    }

}
