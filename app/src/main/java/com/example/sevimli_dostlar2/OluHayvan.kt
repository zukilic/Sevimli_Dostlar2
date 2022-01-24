package com.example.sevimli_dostlar2

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_listeleme.*

class OluHayvan : AppCompatActivity() {

    lateinit var tur : Spinner
    lateinit var konum : EditText
    lateinit var durum : Spinner
    lateinit var kaydet : Button
    var item = ""
    var item2 = ""

    lateinit var tur2 : TextView
    lateinit var konum2 : TextView
    lateinit var durum2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olu_hayvan)

        tur = findViewById(R.id.tur)
        konum = findViewById(R.id.konum)
        durum = findViewById(R.id.durum)
        kaydet = findViewById(R.id.kaydet)

        tur2 = findViewById(R.id.tur2)
        konum2 = findViewById(R.id.konum2)
        durum2 = findViewById(R.id.durum2)

        tur.adapter = ArrayAdapter.createFromResource(this, R.array.tur2, android.R.layout.simple_spinner_item)

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

        kaydet.setOnClickListener {
            val db = DBHelper(this, null)
            db.oluHayvan(item, konum.text.toString(), item2)
            Toast.makeText(this, "Ölü Hayvan Tanımlandı", Toast.LENGTH_LONG).show()
            konum.text.clear()

            val db2 = DBHelper(this, null).writableDatabase
            val count = "SELECT count(*) FROM " + DBHelper.TABLE_NAME3
            val mcursor = db2.rawQuery(count, null)
            mcursor!!.moveToFirst()
            val icount = mcursor.getInt(0)

            if (icount > 0) {
                val db = DBHelper(this, null)
                val cursor = db.oluHayvanListeleme()

                cursor!!.moveToLast()
                if((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL))) == ("Müdahale bekliyor")) {
                    tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL2)) + "\n")
                    konum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KONUM_COL2)) + "\n")
                    durum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL)) + "\n")
                }
                // at last we close our cursor
                cursor.close()
            }
            mcursor.close()
        }


        val db2 = DBHelper(this, null).writableDatabase
        val count = "SELECT count(*) FROM " + DBHelper.TABLE_NAME3
        val mcursor = db2.rawQuery(count, null)
        mcursor!!.moveToFirst()
        val icount = mcursor.getInt(0)
        if (icount > 0) {
            val db = DBHelper(this, null)
            val cursor = db.oluHayvanListeleme()

            cursor!!.moveToFirst()
            if((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL))) == ("Müdahale bekliyor")) {
                tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL2)) + "\n")
                konum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KONUM_COL2)) + "\n")
                durum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL)) + "\n")
            }
            while(cursor.moveToNext()){
                if((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL))) == ("Müdahale bekliyor")) {
                    tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL2)) + "\n")
                    konum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KONUM_COL2)) + "\n")
                    durum2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DURUM_COL)) + "\n")
                }
            }

            cursor.close()
        }
        mcursor.close()
    }
}