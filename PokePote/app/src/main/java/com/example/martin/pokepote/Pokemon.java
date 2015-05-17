package com.example.martin.pokepote;

/**
 * Created by Martin on 20/03/2015.
 */

//-------------------------------------------------------------------//
//                                                                   //
//  Classe d'un pokemon defini par son nom, son numero et son image  //
//                                                                   //
//-------------------------------------------------------------------//

public class Pokemon {

    public String nom;
    public String numero;
    public String url_image;

    public Pokemon(String aNom, String aNumero, String aUrlImage) {
        nom = aNom;
        numero = aNumero;
        url_image = aUrlImage;
    }

    public String getNumero(){
        return this.numero;
    }
}
