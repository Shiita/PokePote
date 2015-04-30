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
 * Created by Martin on 30/04/2015.
 */
public class attaques_adapter extends BaseAdapter {

    // Une liste de personnes
    private List<Attaque> mListP;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    static class ViewHolder {
        public TextView numero;
        public TextView nom;
        public TextView type;
        public TextView level;
    }


    public attaques_adapter(Context context, List<Attaque> aListP) {
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
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "selection_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.attaques_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nom = (TextView) layoutItem.findViewById(R.id.AT_Nom);
            viewHolder.numero = (TextView) layoutItem.findViewById(R.id.AT_Numero);
            viewHolder.type = (TextView) layoutItem.findViewById(R.id.AT_Type);
            viewHolder.level = (TextView) layoutItem.findViewById(R.id.AT_Level);
            layoutItem.setTag(viewHolder);
        } else {
            layoutItem = (LinearLayout) convertView;
            viewHolder = (ViewHolder) layoutItem.getTag();
        }


        /*//(2) : Récupération des TextView de notre layout
        TextView tv_Nom = (TextView)layoutItem.findViewById(R.id.TV_Nom);
        TextView tv_Numero = (TextView)layoutItem.findViewById(R.id.TV_Numero);
        ImageView tv_Image = (ImageView) layoutItem.findViewById(R.id.TV_Image);
        //TextView tv_Type = (TextView)layoutItem.findViewById(R.id.TV_Type);*/

        //(3) : Renseignement des valeurs
        viewHolder.nom.setText(mListP.get(position).nom);
        viewHolder.numero.setText(mListP.get(position).numero);
        viewHolder.level.setText(mListP.get(position).level);
        viewHolder.type.setText(mListP.get(position).type);

        return layoutItem;
    }
}
