package com.example.sevimli_dostlar2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            val intent = Intent(this,Tanimlama::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener{
            val intent = Intent(this,Listeleme::class.java)
            startActivity(intent)
        }
        button3.setOnClickListener{
            val intent = Intent(this,AsiGirme::class.java)
            startActivity(intent)
        }
        button4.setOnClickListener{
            val intent = Intent(this,AsiTalebi::class.java)
            startActivity(intent)
        }
        button5.setOnClickListener{
            val intent = Intent(this,OluHayvan::class.java)
            startActivity(intent)
        }
        button6.setOnClickListener{
            val intent = Intent(this,Saldirgan::class.java)
            startActivity(intent)
        }
        button7.setOnClickListener{
            val intent = Intent(this,Sahiplenme::class.java)
            startActivity(intent)
        }

    }
}