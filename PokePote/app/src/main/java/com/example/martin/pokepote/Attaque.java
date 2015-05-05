package com.example.martin.pokepote;

/**
 * Created by Martin on 30/04/2015.
 */
public class Attaque {
    public String nom;
    public String numero;
    public String type;
    public String level;

    public Attaque(String aNumero, String aNom, String aType, String aLevel ) {
        nom = aNom;
        numero = aNumero;
        type = aType;
        level = aLevel;
    }

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
