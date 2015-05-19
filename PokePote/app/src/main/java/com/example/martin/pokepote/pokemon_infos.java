package com.example.martin.pokepote;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pokemon_infos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pokemon_infos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pokemon_infos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String attribut;
    private String valeur;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pokemon_infos.
     */
    // TODO: Rename and change types and number of parameters
    public static pokemon_infos newInstance(String param1, String param2) {
        pokemon_infos fragment = new pokemon_infos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public pokemon_infos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            attribut = getArguments().getString(ARG_PARAM1);
            valeur = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View MyView = inflater.inflate(R.layout.fragment_pokemon_infos, container, false);
        TextView Attr = (TextView) MyView.findViewById(R.id.Attribut);
        TextView Val = (TextView)MyView.findViewById(R.id.Valeur);
        Val.setText(valeur);
        Attr.setText(attribut);
        defineColor(attribut,valeur,Attr, Val);
        return MyView;
    }

    public void defineColor(String attribut,String valeur, TextView attr, TextView value){

        switch (attribut.toUpperCase()) {
            case "STEEL" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#aaaabb"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "GHOST" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#5454a8"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "ROCK" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#baa965"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "GROUND" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#ddbb55"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "POISON" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#773366"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "PSYCHIC" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#ff5599"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "FAIRY" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#ffaaff"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "GRASS" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#77cc55"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "DRAGON" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#453479"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "BUG" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#aabb22"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "FLYING" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#6699ff"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "FIGHTING" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#ba5543"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "ICE" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#78deff"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "FIRE" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#ff4422"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "WATER" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#3399ff"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "ELECTRIC" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#ffcc33"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "NORMAL" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#bbbbaa"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
            case "DARK" : {
                if(!valeur.equals("")) {
                    attr.setBackgroundColor(Color.parseColor("#453434"));
                    attr.setTextColor(Color.parseColor("#ffffff"));
                    attr.setGravity(Gravity.CENTER);
                    value.setGravity(Gravity.CENTER);
                    if(Float.valueOf(valeur) < 1){
                        value.setTextColor(Color.GREEN);
                    }else if(Float.valueOf(valeur)>1){
                        value.setTextColor(Color.RED);
                    }
                }
            }break;
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
