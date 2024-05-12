package com.cbpajz.kameranz

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val urls = listOf(
        "https://cam.idokep.hu:443/live/dmjvcam3/index.m3u8",
        "https://mms.iptv.unideb.hu:443/ipcam/ipcam.sport.stream/playlist.m3u8",
        "https://cam.idokep.hu:443/live/demjeni_termalto/index.m3u8",
        "https://cam.idokep.hu:443/live/hight/index.m3u8",
        "https://cam.idokep.hu:443/live/vitorlaskikoto1/index.m3u8"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.listView)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, urls)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedUrl = urls[position]
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("url", selectedUrl)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
        }

        val profileActButton = findViewById<Button>(R.id.to_profile)
        profileActButton.setOnClickListener {
            val Intent=Intent(this, MainProfileActivity::class.java)
            startActivity(Intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
        }
        val searchActbutton = findViewById<Button>(R.id.to_search)
        searchActbutton.setOnClickListener {
            val Intent=Intent(this, SearchActivity::class.java)
            startActivity(Intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
        }
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            val intent = Intent(this, Loginregister::class.java)
            startActivity(intent)
        }
    }
}
