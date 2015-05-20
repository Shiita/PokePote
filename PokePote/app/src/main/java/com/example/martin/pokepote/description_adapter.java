package com.example.martin.pokepote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Martin on 06/05/2015.
 */

//----------------------------------------------------------------------------//
//                                                                            //
//             Adapter de liste des descriptions d'un pokemon                 //
//  Définie les propriétés d'affichage à liste des descriptions d'un pokemon  //
//                                                                            //
//----------------------------------------------------------------------------//

public class description_adapter extends BaseAdapter {

    // Une liste de descriptions
    private List<Description> mListP;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    static class ViewHolder {
        public TextView generation;
        public TextView description;
    }


    public description_adapter(Context context, List<Description> aListP) {
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
            //Initialisation de notre item à partir du  layout XML "selection_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.description_layout, parent, false);
            //Récupération des TextView de notre layout
            viewHolder = new ViewHolder();
            viewHolder.generation = (TextView) layoutItem.findViewById(R.id.DE_Generation);
            viewHolder.description = (TextView) layoutItem.findViewById(R.id.DE_Description);
            layoutItem.setTag(viewHolder);
        } else {
            layoutItem = (LinearLayout) convertView;
            viewHolder = (ViewHolder) layoutItem.getTag();
        }

        //Renseignement des valeurs
        viewHolder.generation.setText("Version " + mListP.get(position).version);
        viewHolder.description.setText(mListP.get(position).description);
        return layoutItem;
    }
}
