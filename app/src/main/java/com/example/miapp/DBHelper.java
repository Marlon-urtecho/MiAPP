package com.example.miapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    // Varieble para el nombre de la base de datos
    public static final String DBNAME = "Login.db";

    //Constructor de la clase
    public DBHelper(Context context) {
        super(context,"Login.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key,Password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {

        MyDB.execSQL("drop Table if exists users");//si la tabla de usuarios existe




    }

    //creamos un metodo para insertar
    public boolean insertData(String username ,String password){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("Password", password);


        long result = MyDB.insert("users",null,contentValues);

        if (result == -1)return false;
        else
            return true;
    }

    //funcion para comprobar si en la base de datos existe el usuario
    public boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor= MyDB.rawQuery("select * from users where username = ?",new String[]{username});

        if (cursor.getCount() > 0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean checkusernamepassword(String username , String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("select * from users where username = ? and password = ?",new String[]{username,password});
        if (cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

}
