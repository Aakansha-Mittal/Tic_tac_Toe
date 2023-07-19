package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

var singlePlayer = false
class Player : AppCompatActivity() {

    lateinit var btnMulti : Button
    lateinit var btnSingle : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        btnMulti = findViewById(R.id.btnMulti)
        btnSingle = findViewById(R.id.btnSingle)

        btnSingle.setOnClickListener {
            singlePlayer = true
            val intent = Intent(this, GamePlayer::class.java)
            intent.putExtra("singlePlayer", singlePlayer)
            startActivity(intent)
        }

        btnMulti.setOnClickListener {
            val intent = Intent(this, GamePlayer::class.java)
            intent.putExtra("singlePlayer", singlePlayer)
            startActivity(intent)
        }

    }
}