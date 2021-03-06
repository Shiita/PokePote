package com.example.martin.pokepote;

/**
 * Created by Martin on 06/05/2015.
 */

//------------------------------------------------------------------------//
//                                                                        //
//  Classe d'une description definie par sa generation et sa description  //
//                                                                        //
//------------------------------------------------------------------------//

public class Description {
    public String generation;
    public String description;
    public String version;


    //-----------------------------//
    //  Constructeur de la classe  //
    //-----------------------------//
    public Description(String generation, String description, String version) {
        this.generation = generation;
        this.description = description;
        this.version = version;
    }

    //-----------------------//
    //  Methode d'affichage  //
    //-----------------------//
    @Override
    public String toString() {
        return "Description{" +
                "generation='" + generation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
