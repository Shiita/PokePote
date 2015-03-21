package com.example.martin.pokepote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Martin on 20/03/2015.
 */
public class pokemon_adapter extends BaseAdapter {

    // Une liste de personnes
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
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "selection_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.selection_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.nom = (TextView) layoutItem.findViewById(R.id.TV_Nom);
            viewHolder.numero = (TextView) layoutItem.findViewById(R.id.TV_Numero);
            viewHolder.image = (ImageView) layoutItem.findViewById(R.id.TV_Image);
            layoutItem.setTag(viewHolder);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        ViewHolder holder = (ViewHolder) layoutItem.getTag();
        /*//(2) : Récupération des TextView de notre layout
        TextView tv_Nom = (TextView)layoutItem.findViewById(R.id.TV_Nom);
        TextView tv_Numero = (TextView)layoutItem.findViewById(R.id.TV_Numero);
        ImageView tv_Image = (ImageView) layoutItem.findViewById(R.id.TV_Image);
        //TextView tv_Type = (TextView)layoutItem.findViewById(R.id.TV_Type);*/

        //(3) : Renseignement des valeurs
        holder.nom.setText(mListP.get(position).nom);
        holder.numero.setText("#" + mListP.get(position).numero);
        new DownloadImageTask(holder.image).execute(mListP.get(position).url_image);
        //tv_Type.setText(mListP.get(position).type);

        //On retourne l'item créé.
        return layoutItem;
    }

}
