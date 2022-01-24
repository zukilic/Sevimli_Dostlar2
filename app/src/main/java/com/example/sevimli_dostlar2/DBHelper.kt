package com.example.sevimli_dostlar2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME1 + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                KIMLIK_COL + " TEXT NOT NULL UNIQUE, " + //NOT NULL UNIQUE
                TUR_COL + " TEXT, " +
                RENK_COL + " TEXT, " +
                CINS_COL + " TEXT, " +
                KONUM_COL + " TEXT, " +
                FOTO_COL + " BLOB, " +
                SAHIP_COL + " TEXT" + ")")

        val query2 = ("CREATE TABLE " + TABLE_NAME2 + " ("
                + ID_COL2 + " INTEGER PRIMARY KEY, " +
                ID_COL + " INTEGER, " +
                ACIK_COL + " TEXT, " +
                "FOREIGN KEY (" + ID_COL + ") " +
                "REFERENCES " + TABLE_NAME1 + " ("+ ID_COL + ")" + ")")

        val query3 = ("CREATE TABLE " + TABLE_NAME3 + " ("
                + ID_COL3 + " INTEGER PRIMARY KEY, " +
                TUR_COL2 + " TEXT, " +
                KONUM_COL2 + " TEXT, " +
                DURUM_COL + " TEXT" + ")" )

        val query4 = ("CREATE TABLE " + TABLE_NAME4 + " ("
                + ID_COL4 + " INTEGER PRIMARY KEY, " +
                ZAM_COL + " DATE, " +
                DURUM_COL2 + " TEXT, " +
                ID_COL2 + " INTEGER, " +
                "FOREIGN KEY (" + ID_COL2 + ") " +
                "REFERENCES " + TABLE_NAME2 + " (" + ID_COL2 + ") \n" +
                "       ON UPDATE CASCADE\n" +
                "       ON DELETE CASCADE" + ")")

        val query5 = ("CREATE TABLE " + TABLE_NAME5 + " ("
                + ID_COL5 + " INTEGER PRIMARY KEY, " +
                TUR_COL3 + " TEXT, " +
                KONUM_COL3 + " TEXT, " +
                ACIK_COL2 + " TEXT, " +
                DURUM_COL3 + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
        db.execSQL(query2)
        db.execSQL(query3)
        db.execSQL(query4)
        db.execSQL(query5)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5)

        onCreate(db)
    }

    // This method is for adding data in our database
    fun tanimlama(kimlik : String, tur : String, renk : String, cins : String, konum : String){//, foto : ByteArray){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(KIMLIK_COL, kimlik)
        values.put(TUR_COL, tur)
        values.put(RENK_COL, renk)
        values.put(CINS_COL, cins)
        values.put(KONUM_COL, konum)
        //values.put(FOTO_COL, foto)
        values.put(SAHIP_COL, "yok")

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME1, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // This method is for adding data in our database
    //fun asiGirme(kimlik : String, tur : String, renk : String, zaman : String, durum : String){
    fun asiGirme(zaman : String, durum : String){

      //  val values = ContentValues()
        val values2 = ContentValues()

      //  values.put(KIMLIK_COL, kimlik)
       // values.put(TUR_COL, tur)
       // values.put(RENK_COL, renk)

        values2.put(ZAM_COL, zaman)
        values2.put(DURUM_COL2, durum)

        val db = this.writableDatabase

     //   db.insert(TABLE_NAME1, null, values)
        db.insert(TABLE_NAME4, null, values2)


       /* val query = "UPDATE $TABLE_NAME1\n" +
                "                SET $ZAM_COL = $zaman, $DURUM_COL2 = durum\n" +
                "        WHERE $KIMLIK_COL = $kimlik"*/
       val query = "Select * from Table Info tf\n" +
               "LEFT JOIN Table WorkForce twf ON twf.tf_id = tf.id\n" +
               "LEFT JOIN Table WorkDetails twd ON twd.tw_id = twf.id"

       // db.update(TABLE_NAME1,values2, "$KIMLIK_COL=?", arrayOf(kimlik))
        db.close()
    }

    fun asiTalebi(kimlik : String, tur : String, renk : String, aciklama : String){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        values.put(KIMLIK_COL, kimlik)
        values.put(TUR_COL, tur)
        values.put(RENK_COL, renk)


        val values2 = ContentValues()

        values2.put(ACIK_COL, aciklama)

        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME1, null, values)
        db.insert(TABLE_NAME2, null, values2)

        // at last we are
        // closing our database
        db.close()
    }

    fun oluHayvan(tur : String, konum : String, durum : String){

        val values = ContentValues()

        values.put(TUR_COL2, tur)
        values.put(KONUM_COL2, konum)
        values.put(DURUM_COL, durum)

        val db = this.writableDatabase

        db.insert(TABLE_NAME3, null, values)
        db.close()
    }

    fun saldirganHayvan(tur : String, konum : String, aciklama: String, durum : String){

        val values = ContentValues()

        values.put(TUR_COL3, tur)
        values.put(KONUM_COL3, konum)
        values.put(ACIK_COL2, aciklama)
        values.put(DURUM_COL3, durum)

        val db = this.writableDatabase

        db.insert(TABLE_NAME5, null, values)
        db.close()
    }

    fun oluHayvan(tur : String, konum : String, aciklama: String, durum : String){

        val values = ContentValues()

        values.put(TUR_COL2, tur)
        values.put(KONUM_COL2, konum)
        values.put(DURUM_COL, durum)

        val db = this.writableDatabase

        db.insert(TABLE_NAME3, null, values)
        db.close()
    }

    //clear all tables
    fun clear() {
        // on below line we are creating
        // a variable to write our database.
        val db = this.writableDatabase
        db.execSQL("delete from "+ TABLE_NAME1)
        db.execSQL("delete from "+ TABLE_NAME2)
        db.execSQL("delete from "+ TABLE_NAME3)
        db.execSQL("delete from "+ TABLE_NAME4)
        db.execSQL("delete from "+ TABLE_NAME5)
        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
       // db.delete(TABLE_NAME1, "kimlik=?", arrayOf(kimlik))
        db.close()
    }

    fun deleteHayvan(kimlik: String) {

        // on below line we are creating
        // a variable to write our database.
        val db = this.writableDatabase

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME1, "kimlik_no=?", arrayOf(kimlik))
        //db.execSQL("delete from "+ TABLE_NAME1 + " where " + KIMLIK_COL + " =" + kimlik)
        db.close()
    }

    fun updateSahip(kimlik : String) {
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME1"
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val cv = ContentValues()
                cv.put(SAHIP_COL, "var")
                db.update(TABLE_NAME1,cv, "$KIMLIK_COL=?",
                    arrayOf(kimlik))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }


    // below method is to get
    // all data from our database
    fun listeleme(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME1, null)

    }

    fun oluHayvanListeleme(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME3, null)

    }

    fun saldirganHayvanListeleme(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME5, null)

    }

    fun asiDurumuListeleme(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME4, null)

    }

    companion object{
        private val DATABASE_NAME = "SEVİMLİ DOSTLAR"
        private val DATABASE_VERSION = 1

        val TABLE_NAME1 = "sokak_hayvani"
        val ID_COL = "hayvan_id"
        val KIMLIK_COL = "kimlik_no"
        val TUR_COL = "tür"
        val RENK_COL = "renk"
        val CINS_COL = "cinsiyet"
        val KONUM_COL = "konum"
        val FOTO_COL = "fotoğraf"
        val SAHIP_COL = "hayvan_sahibi"

        val TABLE_NAME2 = "asi_talebi"
        val ID_COL2 = "asi_talebi_id"
        val ACIK_COL = "aciklama"

        val TABLE_NAME3 = "olu_hayvan"
        val ID_COL3 = "olu_hayvan_id"
        val TUR_COL2 = "olu_hayvan_turu"
        val KONUM_COL2 = "olu_hayvan_konumu"
        val DURUM_COL = "olu_hayvan_durumu"

        val TABLE_NAME4 = "asi_islemi"
        val ID_COL4 = "asi_islemi_id"
        val ZAM_COL = "son_asi_zamani"
        val DURUM_COL2 = "asi_durumu"

        val TABLE_NAME5 = "saldirgan_sokak_hayvani"
        val ID_COL5 = "saldirgan_hayvan_id"
        val TUR_COL3 = "saldiranin_turu"
        val KONUM_COL3 = "saldirganin_konumu"
        val ACIK_COL2 = "sikayet_aciklama"
        val DURUM_COL3 = "saldirganin_durumu"
    }
}
