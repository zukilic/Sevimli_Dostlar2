package com.example.sevimli_dostlar2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class AsiGirme : AppCompatActivity() {

    lateinit var kimlikNo : EditText
    lateinit var tur : Spinner
    lateinit var renk : EditText
    lateinit var kaydet : Button
    lateinit var zaman : EditText
    lateinit var durum : EditText
    var item = ""
    lateinit var talep : Button

    lateinit var kimlikNo2 : TextView
    lateinit var tur2 : TextView
    lateinit var renk2: TextView
    lateinit var zaman2 : TextView
    lateinit var durum2 : TextView


    var itis = 1

    var isit = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        val dateFormat = DateFormat.getDateFormat(
            applicationContext
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asi_girme)

        kimlikNo = findViewById(R.id.kimlikNo)
        tur = findViewById(R.id.tur)
        renk = findViewById(R.id.renk)
        zaman = findViewById(R.id.zaman)
        durum = findViewById(R.id.durum)
        kaydet = findViewById(R.id.kaydet)
        talep = findViewById(R.id.talep)

        kimlikNo2 = findViewById(R.id.kimlikNo2)
        tur2 = findViewById(R.id.tur2)
        renk2 = findViewById(R.id.renk2)
        zaman2 = findViewById(R.id.zaman2)
        durum2 = findViewById(R.id.durum2)


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

        kaydet.setOnClickListener {
            val db2 = DBHelper(this, null).writableDatabase
            val count = "SELECT count(*) FROM " + DBHelper.TABLE_NAME4
            val mcursor = db2.rawQuery(count, null)
            mcursor!!.moveToFirst()
            val icount = mcursor.getInt(0)

            if (icount > 0) {
                val db3 = DBHelper(this, null)
                val cursor = db3.listeleme()
                val cursor2 = db3.asiDurumuListeleme()
                cursor!!.moveToFirst()
                cursor2!!.moveToLast()
                if (((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) == (kimlikNo.text.toString()))) {
                    kimlikNo2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL)) + "\n")
                    tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL)) + "\n")
                    renk2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.RENK_COL)) + "\n")
                    zaman2.append(cursor2.getString(cursor2.getColumnIndexOrThrow(DBHelper.ZAM_COL)) + "\n")
                    durum2.append(cursor2.getString(cursor2.getColumnIndexOrThrow(DBHelper.DURUM_COL2)) + "\n")
                    db3.asiGirme(
                        zaman2.text.toString(),
                        durum2.text.toString()
                    )
                    Toast.makeText(this, "Aşı Girişi Yapıldı", Toast.LENGTH_LONG).show()
                    isit = 0
                } else if(isit == 1){
                    while (cursor.moveToNext()) {
                        if (((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) == (kimlikNo.text.toString()))) {
                            kimlikNo2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL)) + "\n")
                            tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL)) + "\n")
                            renk2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.RENK_COL)) + "\n")
                            zaman2.append(cursor2.getString(cursor2.getColumnIndexOrThrow(DBHelper.ZAM_COL)) + "\n")
                            durum2.append(cursor2.getString(cursor2.getColumnIndexOrThrow(DBHelper.DURUM_COL2)) + "\n")
                            db3.asiGirme(
                                zaman2.text.toString(),
                                durum2.text.toString()
                            )
                            Toast.makeText(this, "Aşı Girişi Yapıldı", Toast.LENGTH_LONG).show()
                            isit = 0
                            break
                        }
                    }
                }
                if(isit == 1) {
                    Toast.makeText(this, "Aşı Girişi Yapılamıyor", Toast.LENGTH_LONG).show()
                }
                cursor.close()
                cursor2.close()

            }else{
                Toast.makeText(this, "Aşı Girişi Yapılamıyor", Toast.LENGTH_LONG).show()
            }
            mcursor.close()
            isit = 1

        }




        val db2 = DBHelper(this, null).writableDatabase
        val count = "SELECT count(*) FROM " + DBHelper.TABLE_NAME4
        val mcursor = db2.rawQuery(count, null)
        mcursor!!.moveToFirst()
        val icount = mcursor.getInt(0)
        if (icount > 0) {
            val db3 = DBHelper(this, null)
            val cursor = db3.listeleme()
            val cursor2 = db3.asiDurumuListeleme()
            cursor!!.moveToFirst()
            cursor2!!.moveToFirst()
            if (((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) == (kimlikNo.text.toString()))) {
                kimlikNo2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL)) + "\n")
                tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL)) + "\n")
                renk2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.RENK_COL)) + "\n")
                zaman2.append(cursor2.getString(cursor2.getColumnIndexOrThrow(DBHelper.ZAM_COL)) + "\n")
                durum2.append(cursor2.getString(cursor2.getColumnIndexOrThrow(DBHelper.DURUM_COL2)) + "\n")

                itis = 0
            }
            if(itis == 1) {
                while (cursor.moveToNext() && cursor2.moveToNext()) {
                    if (((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) == (kimlikNo.text.toString()))) {
                        kimlikNo2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL)) + "\n")
                        tur2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TUR_COL)) + "\n")
                        renk2.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.RENK_COL)) + "\n")
                        zaman2.append(cursor2.getString(cursor2.getColumnIndexOrThrow(DBHelper.ZAM_COL)) + "\n")
                        durum2.append(cursor2.getString(cursor2.getColumnIndexOrThrow(DBHelper.DURUM_COL2)) + "\n")

                        itis = 0
                        break
                    }
                }
            }
            itis = 1
            cursor.close()
            cursor2.close()
        }
        mcursor.close()



        talep.setOnClickListener{
            val intent = Intent(this,AsiTalebi::class.java)
            startActivity(intent)
        }
    }
}