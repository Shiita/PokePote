package com.example.martin.pokepote;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class bdd_DAO {
    private SQLiteDatabase bd; //THE DB

    public bdd_DAO() {
    }

    //ATTENTION : A chaque requete (ou groupe de requete) on doit ouvrir et fermer la BDD

    //Ouverture de la connexion
    public void openConnection(Context ctx) {
        bd = new bdd(ctx, "pkm.bd", null, 1).getWritableDatabase();
    }

    //Fermeture de la connexion
    public void closeConnection() {
        bd.close();
    }

    //ajouter un pokemon dans la BDD
    public long addPkm(String id) {
        ContentValues valeurs = new ContentValues();
        valeurs.put("id", Integer.parseInt(id));
        valeurs.put("favori", 0);
        valeurs.put("equipe", 0);
        return bd.insert("pkm", null, valeurs);
    }

    //Set l'attribut favori du pkm
    public Long setFavori(String id, Boolean fav, Context appliCtxt){

        Long valRet ;

        Integer valeur ;
        if (fav) valeur = 1; else valeur= 0;

        PKM_BDD pkm = getPkm(Integer.parseInt(id), appliCtxt);

        openConnection(appliCtxt);
        ContentValues valeurs = new ContentValues();
        valeurs.put("id", Integer.parseInt(id));
        valeurs.put("favori", valeur);

        if(pkm == null) {
           //Creer un nouvel enregistrement
            valeurs.put("equipe", 0);
            valRet = bd.insert("pkm", null, valeurs);
        }else{
            //modifier le favori du pkm concerne
            valeurs.put("equipe", pkm.equipe);
            valRet = new Long(bd.update("pkm", valeurs, "id = " +id, null ));
        }

        closeConnection();
        return valRet;
    }

    //Set l'attribut equipe du pkm
    public Long setEquipe(String id, Boolean eq, Context appliCtxt){

        Long valRet ;
        Integer valeur ;
        if (eq) valeur = 1; else valeur= 0;


        PKM_BDD pkm = getPkm(Integer.parseInt(id), appliCtxt);

        openConnection(appliCtxt);
        ContentValues valeurs = new ContentValues();
        valeurs.put("id", Integer.parseInt(id));
        valeurs.put("equipe", valeur);

        if(pkm == null) {
            //Creer un nouvel enregistrement
            valeurs.put("favori", 0);
            valRet = bd.insert("pkm", null, valeurs);
        }else{
            //modifier l'equipe du pkm concerne
            valeurs.put("favori", pkm.favori);
            valRet = new Long(bd.update("pkm", valeurs, "id = " +id, null ));
        }
        closeConnection();
        return valRet;
    }

    //get favori et equipe du pkm
    public Integer getFavori(String id, Context appliCtxt){
        PKM_BDD pkm = getPkm(Integer.parseInt(id), appliCtxt);
        if(pkm != null)
            return pkm.favori;
        else return 0;
    }

    public Integer getEquipe(String id, Context appliCtxt){
        PKM_BDD pkm = getPkm(Integer.parseInt(id), appliCtxt);
        if (pkm != null)
            return pkm.equipe;
        else return 0;
    }

    public PKM_BDD getPkm(Integer id, Context appliCtxt) {
        openConnection(appliCtxt);
        String[] colonnes = {"id", "favori", "equipe"};
        Cursor curseur = bd.query("pkm", colonnes, "id = "+id, null,  null, null, "id") ;
        if (curseur.getCount()==0)
            return null;
        curseur.moveToFirst();
        PKM_BDD pkm = new PKM_BDD(curseur.getInt(0), curseur.getInt(1), curseur.getInt(2));
        curseur.close();
        closeConnection();
        return pkm;
    }






}
