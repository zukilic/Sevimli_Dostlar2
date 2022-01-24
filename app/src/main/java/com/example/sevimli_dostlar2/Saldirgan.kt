package com.example.sevimli_dostlar2

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class Saldirgan : AppCompatActivity() {

    lateinit var tur : Spinner
    lateinit var konum : EditText
    lateinit var aciklama : EditText
    lateinit var durum : Spinner
    lateinit var gonder : Button
    var item = ""
    var item2 = ""

    lateinit var tur2 : TextView
    lateinit var konum2 : TextView
    lateinit var aciklama2 : TextView
    lateinit var durum2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saldirgan)

        tur = findViewById(R.id.tur)
        konum = findViewById(R.id.konum)
        aciklama = findViewById(R.id.aciklama)
        durum = findViewById(R.id.durum)
        gonder = findViewById(R.id.gonder)

        tur2 = findViewById(R.id.tur2)
        konum2 = findViewById(R.id.konum2)
        aciklama2 = findViewById(R.id.aciklama2)
        durum2 = findViewById(R.id.durum2)

        tur.onItemSelectedListener = object : Activity(), AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                item = parent.getItemAtPosition(pos).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        durum.adapter = ArrayAdapter.createFromResource(this, R.array.durum, android.R.layout.simple_spinner_item)

        durum.onItemSelectedListener = object : Activity(), AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                item2 = parent.getItemAtPosition(pos).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        gonder.setOnClickListener {
            val db = DBHelper(this, null)
            db.saldirganHayvan(item, konum.text.toString(), aciklama.text.toString(), item2)
            Toast.makeText(this, "Saldırgan Sokak Hayvanı Tanımlandı", Toast.LENGTH_LONG).show()
            konum.text.clear()
            aciklama.text.clear()


            val db2 = DBHelper(this, null).writableDatabase
            val count = "SELECT count(*) FROM " + DBHelper.TABLE_NAME5
            val mcursor = db2.rawQuery(count, null)
            mcursor!!.moveToFirst()
            val icount = mcursor.getInt(0)

            if (icount > 0) {
                val db = DBHelper(this, null)
                // db.clear()

                val cursor = db.saldirganHayvanListeleme()

                cursor!!.moveToLast()
                if((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL3))) == ("Müdahale bekliyor")) {
                    tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL3)) + "\n")
                    konum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KONUM_COL3)) + "\n")
                    aciklama2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACIK_COL2)) + "\n")
                    durum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL3)) + "\n")
                }
                // at last we close our cursor
                cursor.close()
            }
            mcursor.close()
        }


        val db2 = DBHelper(this, null).writableDatabase
        val count = "SELECT count(*) FROM " + DBHelper.TABLE_NAME5
        val mcursor = db2.rawQuery(count, null)
        mcursor!!.moveToFirst()
        val icount = mcursor.getInt(0)

        if (icount > 0) {
            val db = DBHelper(this, null)

            val cursor = db.saldirganHayvanListeleme()

            cursor!!.moveToFirst()
            if((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL3))) == ("Müdahale bekliyor")) {
                tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL3)) + "\n")
                konum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KONUM_COL3)) + "\n")
                aciklama2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACIK_COL2)) + "\n")
                durum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL3)) + "\n")
            }

            // moving our cursor to next
            // position and appending values
            while(cursor.moveToNext()){
                if((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL3))) == ("Müdahale bekliyor")) {
                    tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL3)) + "\n")
                    konum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KONUM_COL3)) + "\n")
                    aciklama2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACIK_COL2)) + "\n")
                    durum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL3)) + "\n")
                }
            }
            // at last we close our cursor
            cursor.close()
        } else {

        }
        mcursor.close()

    }
}