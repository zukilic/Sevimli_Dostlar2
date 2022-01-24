package com.example.sevimli_dostlar2


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import android.net.Uri
import android.provider.MediaStore
import android.widget.*
import androidx.core.view.drawToBitmap
import androidx.core.view.get


class Tanimlama : AppCompatActivity() {

    lateinit var kimlikNo : EditText
    lateinit var tur : Spinner
    lateinit var renk : EditText
    lateinit var cinsiyet : Spinner
    lateinit var konum : EditText
    lateinit var foto : ImageView
    lateinit var kaydet : Button
    lateinit var button: Button
    private val pickImage = 100
    private var imageUri: Uri? = null
    var item = ""
    var item2 = ""
    var itis = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        val dateFormat = DateFormat.getDateFormat(
            applicationContext
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tanimlama)

        kimlikNo = findViewById(R.id.kimlikNo)
        tur = findViewById(R.id.tur)
        renk = findViewById(R.id.renk)
        cinsiyet = findViewById(R.id.cinsiyet)
        konum = findViewById(R.id.konum)
        title = "Sevimli_Dostlar"
        foto = findViewById(R.id.foto)
        button = findViewById(R.id.buttonLoadPicture)
        kaydet = findViewById(R.id.kaydet)

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

        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        kaydet.setOnClickListener {
            val db2 = DBHelper(this, null).writableDatabase
            val count = "SELECT count(*) FROM " + DBHelper.TABLE_NAME1
            val mcursor = db2.rawQuery(count, null)
            mcursor!!.moveToFirst()
            val icount = mcursor.getInt(0)
            if (icount > 0) {
                val db3 = DBHelper(this, null)
                val cursor = db3.listeleme()
                cursor!!.moveToFirst()
                if ((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) != (kimlikNo.text.toString())) {
                    while (cursor.moveToNext()) {
                        if ((cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KIMLIK_COL))) != (kimlikNo.text.toString())) {
                            itis += 1
                        } else{
                            break
                        }
                    }
                }
                if(itis == icount) {
                    val db = DBHelper(this, null)
                    db.tanimlama(
                        kimlikNo.text.toString(),
                        item,
                        renk.text.toString(),
                        item2,
                        konum.text.toString()
                    )//, foto.toString().toByteArray())
                    Toast.makeText(this, "Sokak Hayvanı Tanımlandı", Toast.LENGTH_LONG).show()
                    kimlikNo.text.clear()
                    //tur.visibility = View.GONE
                    renk.text.clear()
                    //cinsiyet.visibility = View.GONE
                    konum.text.clear()
                    // foto.setImageDrawable(null)
                } else{
                    Toast.makeText(this, "Bu Sokak Hayvanı Zaten Bulunuyor -> " + kimlikNo.text.toString(), Toast.LENGTH_LONG).show()
                    itis = 1
                }
            }else{
                val db = DBHelper(this, null)
                db.tanimlama(
                    kimlikNo.text.toString(),
                    item,
                    renk.text.toString(),
                    item2,
                    konum.text.toString()
                )//, foto.toString().toByteArray())
                Toast.makeText(this, "Sokak Hayvanı Tanımlandı", Toast.LENGTH_LONG).show()
                kimlikNo.text.clear()
                //tur.visibility = View.GONE
                renk.text.clear()
                //cinsiyet.visibility = View.GONE
                konum.text.clear()
                // foto.setImageDrawable(null)
            }
            itis = 1
            mcursor.close()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            foto.setImageURI(imageUri)
        }
    }
}
