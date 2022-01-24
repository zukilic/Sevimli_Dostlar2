package com.example.sevimli_dostlar2

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_listeleme.*

class Sahiplenme : AppCompatActivity() {

    lateinit var kimlikNo : EditText
    lateinit var tur : Spinner
    lateinit var renk : EditText
    lateinit var cinsiyet : Spinner
    lateinit var konum : EditText
    lateinit var sahiplen : Button
    var item = ""
    var item2 = ""
    var isit = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sahiplenme)


        kimlikNo = findViewById(R.id.kimlikNo)
        tur = findViewById(R.id.tur)
        renk = findViewById(R.id.renk)
        cinsiyet = findViewById(R.id.cinsiyet)
        konum = findViewById(R.id.konum)
        sahiplen = findViewById(R.id.sahiplen)

        // Create an ArrayAdapter using the string array and a default spinner layout
        tur.adapter = ArrayAdapter.createFromResource(this, R.array.tur, android.R.layout.simple_spinner_item)

        tur.onItemSelectedListener = object : Activity(), AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                item = parent.getItemAtPosition(pos).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        cinsiyet.adapter = ArrayAdapter.createFromResource(this, R.array.cinsiyet, android.R.layout.simple_spinner_item)

        cinsiyet.onItemSelectedListener = object : Activity(), AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                item2 = parent.getItemAtPosition(pos).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        sahiplen.setOnClickListener {
            val db2 = DBHelper(this, null).writableDatabase
            val count = "SELECT count(*) FROM " + DBHelper.TABLE_NAME1
            val mcursor = db2.rawQuery(count, null)
            mcursor!!.moveToFirst()
            val icount = mcursor.getInt(0)
            if (icount > 0) {
                val db = DBHelper(this, null)
                val cursor = db.listeleme()

                cursor!!.moveToFirst()
                if(((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) == (kimlikNo.text.toString())) && ((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SAHIP_COL))) == "yok")) {
                    //db.deleteHayvan(kimlikNo.text.toString())
                    db.updateSahip(kimlikNo.text.toString())
                    Toast.makeText(this, "Sokak Hayvanı Sahiplenildi", Toast.LENGTH_LONG).show()
                    isit = 0
                }else if(((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) == (kimlikNo.text.toString())) && ((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SAHIP_COL))) == "var")){
                    Toast.makeText(this, "Hayvan Sahipli", Toast.LENGTH_LONG).show()
                    isit = 0
                }else if(isit == 1) {
                    while (cursor.moveToNext()) {
                        if (((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) == (kimlikNo.text.toString())) && ((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SAHIP_COL))) == "yok")) {
                           // db.deleteHayvan(kimlikNo.text.toString())
                            db.updateSahip(kimlikNo.text.toString())
                            Toast.makeText(
                                this,
                                "Sokak Hayvanı Sahiplenildi",
                                Toast.LENGTH_LONG
                            ).show()
                            isit = 0
                            break
                        }else if(((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) == (kimlikNo.text.toString())) && ((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SAHIP_COL))) == "var")){
                            Toast.makeText(this, "Hayvan Sahipli", Toast.LENGTH_LONG).show()
                            isit = 0
                            break
                        }
                    }
                }
                if(isit == 1) {
                    Toast.makeText(this, "Hayvan Bulunamadı", Toast.LENGTH_LONG).show()
                }
                cursor.close()
            }else{
                Toast.makeText(this, "Hayvan Bulunamadı", Toast.LENGTH_LONG).show()
            }
            mcursor.close()
            isit = 1
            kimlikNo.text.clear()
            renk.text.clear()
            konum.text.clear()
        }

    }
}