package com.example.martin.pokepote;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Martin on 20/03/2015.
 */

//-----------------------------------------------------------//
//                                                           //
//             Adapter de liste des pokemons                 //
//  Définie les propriétés d'affichage à liste des pokemons  //
//                                                           //
//-----------------------------------------------------------//

public class pokemon_adapter extends BaseAdapter {

    // Une liste de pokemons
    private List<Pokemon> mListP;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    static class ViewHolder {
        public TextView nom;
        public TextView numero;
        public ImageView image;
    }


    public pokemon_adapter(Context context, List<Pokemon> aListP) {
        mContext = context;
        mListP = aListP;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return mListP.size();
    }

    public Object getItem(int position) {
        return mListP.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        ViewHolder viewHolder;
        //Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du layout XML "selection_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.selection_layout, parent, false);
            viewHolder = new ViewHolder();
            //Récupération des TextView de notre layout
            viewHolder.nom = (TextView) layoutItem.findViewById(R.id.TV_Nom);
            viewHolder.numero = (TextView) layoutItem.findViewById(R.id.TV_Numero);
            viewHolder.image = (ImageView) layoutItem.findViewById(R.id.TV_Image);
            layoutItem.setTag(viewHolder);
        } else {
            layoutItem = (LinearLayout) convertView;
            viewHolder = (ViewHolder) layoutItem.getTag();
        }

        //Renseignement des valeurs
        viewHolder.nom.setText(mListP.get(position).nom);
        viewHolder.numero.setText("#" + mListP.get(position).numero);

        File path = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "iconDex");
        File image = new File(path.getPath() + "/a" + mListP.get(position).url_image.substring(mListP.get(position).url_image.lastIndexOf("/") + 1));

        if (!image.exists()){
            System.out.println("no");
            System.out.println(mListP.get(position).url_image);
            new DownloadImageTask(viewHolder.image, mListP.get(position).url_image, mContext);
        }
        else
        {
            System.out.println("yes");
            try {
                viewHolder.image.setImageBitmap(BitmapFactory.decodeFile(image.getCanonicalPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //On retourne l'item créé.
        return layoutItem;
    }

}
