package com.example.martin.pokepote;

/**
 * Created by Martin on 30/04/2015.
 */

//---------------------------------------------------------------------------------//
//                                                                                 //
//  Classe d'une attaque definie par son numero, son numero son type et son level  //
//                                                                                 //
//---------------------------------------------------------------------------------//

public class Attaque {
    public String nom;
    public String numero;
    public String type;
    public String level;

    //-----------------------------//
    //  Constructeur de la classe  //
    //-----------------------------//
    public Attaque(String aNumero, String aNom, String aType, String aLevel ) {
        nom = aNom;
        numero = aNumero;
        type = aType;
        level = aLevel;
    }


    //-----------------------//
    //  Methode d'affichage  //
    //-----------------------//
    @Override
    public String toString() {
        return "Attaque{" +
                "nom='" + nom + '\'' +
                ", numero='" + numero + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
