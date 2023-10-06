package com.example.listadealumnos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperAlumno (context: Context): SQLiteOpenHelper(context, DB_name, null, DB_version) {
    val sqlCreate = "CREATE TABLE $nombreTabla($keyId INTEGER PRIMARY KEY, $nom TEXT, $cuenta TEXT, $correo TEXT, $img TEXT)"
    companion object {
        private val DB_version = 1
        private val DB_name = "bdalumnos.bd"
        private val nombreTabla = "alumnos"
        private val keyId = "id"
        private val nom = "nombre"
        private val cuenta = "numCuenta"
        private val correo = "email"
        private val img = "imagen"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $nombreTabla")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}