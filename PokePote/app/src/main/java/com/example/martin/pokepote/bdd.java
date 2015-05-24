package com.example.martin.pokepote;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bdd extends SQLiteOpenHelper {
    public bdd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Les deux fonctions (onCreate et onUpgrade) heritent de SQLOpenHelper

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table with favori and equipe = 0 or 1
        db.execSQL("CREATE TABLE pkm (" +
                    "id LONG PRIMARY KEY AUTOINCREMENT" +
                    "favori INTEGER NOT NULL" +
                    "equipe INTEGER NOT NULL" +
                 ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE pkm");
        onCreate(db);
    }




}
