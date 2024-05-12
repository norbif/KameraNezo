package com.cbpajz.kameranz

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cbpajz.kameranz.databinding.ActivityLoginRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Loginregister : AppCompatActivity() {
    private lateinit var binding: ActivityLoginRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = FirebaseAuth.getInstance()
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if (it.isSuccessful){
                            val Intent = Intent(this,MainActivity::class.java)
                            startActivity(Intent)
                        }else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

            }else{
                Toast.makeText(this,"Üres mezők nem lehetnek!", Toast.LENGTH_SHORT).show()
            }
        }
        val toRegisterActbutton = findViewById<TextView>(R.id.textView)
        toRegisterActbutton.setOnClickListener {
            val Intent = Intent(this,RegisterActivity::class.java)
            startActivity(Intent)
        }
        /*

         */
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser!=null){
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}