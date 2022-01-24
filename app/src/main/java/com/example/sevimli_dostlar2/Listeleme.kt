package com.example.sevimli_dostlar2

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_listeleme.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class Listeleme : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listeleme)

        val db2 = DBHelper(this, null).writableDatabase
        val count = "SELECT count(*) FROM " + DBHelper.TABLE_NAME1
        val mcursor = db2.rawQuery(count, null)
        mcursor!!.moveToFirst()
        val icount = mcursor.getInt(0)

        if (icount > 0) {

            val db = DBHelper(this, null)
            // db.clear()
            // below is the variable for cursor
            // we have called method to get
            // all names from our database
            // and add to name text view
            val cursor = db.listeleme()

            cursor!!.moveToFirst()
            kimlikNo.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL)) + "\n")
            tur.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL)) + "\n")
            renk.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.RENK_COL)) + "\n")
            cinsiyet.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CINS_COL)) + "\n")
            konum.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KONUM_COL)) + "\n")

            //foto.setImageBitmap((cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.FOTO_COL))).toBitmap())


            // moving our cursor to next
            // position and appending values
            while (cursor.moveToNext()) {
                kimlikNo.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL)) + "\n")
                tur.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL)) + "\n")
                renk.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.RENK_COL)) + "\n")
                cinsiyet.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CINS_COL)) + "\n")
                konum.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KONUM_COL)) + "\n")
                // foto.setImageBitmap((cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.FOTO_COL))).toBitmap())

            }
            // at last we close our cursor
            cursor.close()
        }else {
            Toast.makeText(this, "Hayvan Bulunamadı", Toast.LENGTH_LONG).show()
        }
        mcursor.close()

        kaydet.setOnClickListener {

        }
    }
}

fun Context.assetsToBitmap(fileName:String):Bitmap?{
    return try {
        val stream = assets.open(fileName)
        BitmapFactory.decodeStream(stream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

// extension function to convert bitmap to byte array
fun Bitmap.toByteArray():ByteArray{
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG,10,this)
        return toByteArray()
    }
}
// extension function to convert byte array to bitmap
fun ByteArray.toBitmap():Bitmap{
    var bmp : Bitmap = BitmapFactory.decodeByteArray(this,0,this.size)
    return bmp
}
