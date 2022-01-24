package com.example.sevimli_dostlar2

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class AsiTalebi : AppCompatActivity() {

    lateinit var kimlikNo : EditText
    lateinit var tur : Spinner
    lateinit var renk : EditText
    lateinit var aciklama : EditText
    lateinit var gonder : Button
    var item = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asi_talebi)

        kimlikNo = findViewById(R.id.kimlikNo)
        tur = findViewById(R.id.tur)
        renk = findViewById(R.id.renk)
        aciklama = findViewById(R.id.aciklama)
        gonder = findViewById(R.id.gonder)

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


        gonder.setOnClickListener {
            val db = DBHelper(this, null)
            db.asiTalebi(kimlikNo.text.toString(), item, renk.text.toString(), aciklama.text.toString())
            Toast.makeText(this, "Aşı Talebi Alındı", Toast.LENGTH_LONG).show()
            kimlikNo.text.clear()
            //tur.visibility = View.GONE
            renk.text.clear()
            aciklama.text.clear()

        }


    }
}