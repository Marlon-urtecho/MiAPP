package com.example.miapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {

    // Variables para el nombre de la base de datos y la versión
    public static final String DB_NAME = "Login.db";
    private static final int DB_VERSION = 2;

    // Constructor de la clase
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY, Password TEXT)");
        db.execSQL("CREATE TABLE personal_info (id INTEGER PRIMARY KEY, name TEXT, age INTEGER, address TEXT, phone_number TEXT, marital_status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS personal_info");
        onCreate(db);
    }

    // Método para insertar datos en la tabla 'users'
    public boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("Password", password);

        long result = db.insert("users", null, contentValues);
        db.close(); // Cerrar la base de datos

        return result != -1;
    }

    // Método para insertar datos en la tabla 'personal_info'
    public boolean insertPersonalInfo(String name, int age, String address, String phoneNumber, String maritalStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("address", address);
        contentValues.put("phone_number", phoneNumber);
        contentValues.put("marital_status", maritalStatus);

        long result = db.insert("personal_info", null, contentValues);
        db.close(); // Cerrar la base de datos

        return result != -1;
    }

    // Método para comprobar si el usuario existe en la base de datos
    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("users", null, "username=?", new String[]{username}, null, null, null);

        boolean exists = cursor.getCount() > 0;

        cursor.close(); // Cerrar el cursor
        db.close(); // Cerrar la base de datos

        return exists;
    }

    // Método para comprobar la contraseña en la base de datos
    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("users", null, "username=? AND Password=?", new String[]{username, password}, null, null, null);

        boolean match = cursor.getCount() > 0;

        cursor.close(); // Cerrar el cursor
        db.close(); // Cerrar la base de datos

        return match;
    }
}
