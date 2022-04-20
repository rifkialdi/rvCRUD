package com.example.rvcrud

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper (context: Context): SQLiteOpenHelper(context, NAMA_DATABASE, null, VERSI_DATABASE) {
    companion object{
        private const val NAMA_DATABASE = "pbk.db"
        private const val NAMA_TABLE = "siswa"
        private const val VERSI_DATABASE = 1
        private const val KOLOM_ID = "id"
        private const val KOLOM_NAMA = "nama"
        private const val KOLOM_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val buatTableSiswa = ("CREATE TABLE $NAMA_TABLE (" +
                "$KOLOM_ID INTEGER PRIMARY KEY," +
                "$KOLOM_NAMA TEXT," +
                "$KOLOM_EMAIL TEXT" +
                ")")
        db?.execSQL(buatTableSiswa)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $NAMA_TABLE")
    }

    fun insSiswa(ms: ModelSiswa): Long{
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(KOLOM_ID, ms.id)
        cv.put(KOLOM_NAMA, ms.name)
        cv.put(KOLOM_EMAIL, ms.email)

        val berhasil = db.insert(NAMA_TABLE, null, cv)
        db.close()
        return berhasil
    }

    @SuppressLint("Range")
    fun ambilSemuaSiswa(): ArrayList<ModelSiswa>{
        val daftarSiswa: ArrayList<ModelSiswa> = ArrayList()
        val queryTampil = "SELECT * FROM $NAMA_TABLE"
        val db = this.readableDatabase
        val kursor: Cursor

        try {
            kursor = db.rawQuery(queryTampil, null)
        }catch (kesalahan: Exception){
            kesalahan.printStackTrace()
            db.execSQL(queryTampil)
            return ArrayList()
        }

        var id: Int
        var nama: String
        var email: String

        if (kursor.moveToFirst()){
            do {
                id = kursor.getInt(kursor.getColumnIndex("id"))
                nama = kursor.getString(kursor.getColumnIndex("nama"))
                email = kursor.getString(kursor.getColumnIndex("email"))

                val ms = ModelSiswa(id = id, name = nama, email = email)
                daftarSiswa.add(ms)
            }while (kursor.moveToNext())
        }

        return daftarSiswa
    }

    fun updSiswa(ms: ModelSiswa): Int{
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(KOLOM_ID, ms.id)
        cv.put(KOLOM_NAMA, ms.name)
        cv.put(KOLOM_EMAIL, ms.email)

        val berhasil = db.update(
            NAMA_TABLE,
            cv,
            "id=" + ms.id,
            null
        )
        db.close()
        return berhasil
    }

    fun hapusSiswaDgId(no_index: Int): Int{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KOLOM_ID, no_index)

        val berhasil = db.delete(NAMA_TABLE, "id=$no_index", null)
        db.close()
        return berhasil
    }

}