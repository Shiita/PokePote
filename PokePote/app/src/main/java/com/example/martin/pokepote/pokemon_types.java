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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pokemon_types.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pokemon_types#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pokemon_types extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String type;

    private OnFragmentInteractionListener mListener1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment pokemon_types.
     */
    // TODO: Rename and change types and number of parameters
    public static pokemon_types newInstance(String param1) {
        pokemon_types fragment = new pokemon_types();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM3, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public pokemon_types() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View MyView = inflater.inflate(R.layout.fragment_pokemon_types, container, false);
        TextView Type = (TextView) MyView.findViewById(R.id.Type);
        Type.setText(type);
        Type.setTextColor(Color.WHITE);
        defineColor(type, Type);
        return MyView;
    }

    public void defineColor(String attribut, TextView attr){

        switch (attribut.toUpperCase()) {
            case "STEEL" : attr.setBackgroundColor(Color.parseColor("#aaaabb"));break;
            case "GHOST" : attr.setBackgroundColor(Color.parseColor("#5454a8"));break;
            case "ROCK" : attr.setBackgroundColor(Color.parseColor("#baa965"));break;
            case "GROUND" : attr.setBackgroundColor(Color.parseColor("#ddbb55"));break;
            case "POISON" : attr.setBackgroundColor(Color.parseColor("#773366"));break;
            case "PSYCHIC" : attr.setBackgroundColor(Color.parseColor("#ff5599"));break;
            case "FAIRY" : attr.setBackgroundColor(Color.parseColor("#ffaaff"));break;
            case "GRASS" : attr.setBackgroundColor(Color.parseColor("#77cc55"));break;
            case "DRAGON" : attr.setBackgroundColor(Color.parseColor("#453479"));break;
            case "BUG" : attr.setBackgroundColor(Color.parseColor("#aabb22"));break;
            case "FLYING" : attr.setBackgroundColor(Color.parseColor("#6699ff"));break;
            case "FIGHTING" : attr.setBackgroundColor(Color.parseColor("#ba5543"));break;
            case "ICE" : attr.setBackgroundColor(Color.parseColor("#78deff"));break;
            case "FIRE" : attr.setBackgroundColor(Color.parseColor("#ff4422"));break;
            case "WATER" : attr.setBackgroundColor(Color.parseColor("#3399ff"));break;
            case "ELECTRIC" : attr.setBackgroundColor(Color.parseColor("#ffcc33"));break;
            case "NORMAL" : attr.setBackgroundColor(Color.parseColor("#bbbbaa"));break;
            case "DARK"  : attr.setBackgroundColor(Color.parseColor("#453434"));break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener1 != null) {
            mListener1.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener1 = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener1 = null;
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
