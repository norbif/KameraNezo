package com.cbpajz.kameranz


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cbpajz.kameranz.databinding.ActivityMainProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainProfileBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog:Dialog
    private lateinit var user:User
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainProfileBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth=FirebaseAuth.getInstance()
        uid=auth.currentUser?.uid.toString()

        databaseReference=FirebaseDatabase.getInstance().getReference("Users")
        if (uid.isNotEmpty()){
            getUserData()
        }

        binding.backtoMain.setOnClickListener {
            val Intent= Intent(this,MainActivity::class.java)
            startActivity(Intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
        binding.changer.setOnClickListener {
            val Intent=Intent(this,ProfileActivity::class.java)
            startActivity(Intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
        binding.logout.setOnClickListener {
            auth.signOut()
            val Intent= Intent(this,MainActivity::class.java)
            startActivity(Intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
        binding.delete.setOnClickListener {

            val user = auth.currentUser
            user?.delete()
                ?.addOnSuccessListener {

                    val intent = Intent(this, Loginregister::class.java)
                    auth.signOut()
                    startActivity(intent)
                    finish() //
                }
                ?.addOnFailureListener { e ->

                    Toast.makeText(this, "Fiók törlése sikertelen: ${e.message}", Toast.LENGTH_SHORT).show()
                }


            databaseReference.child(uid).removeValue()
        }
    }

    private fun getUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(User::class.java) ?: return // Ha nincs adat, ne csinálj semmit
                    binding.tvfirstName.setText(user.firstName + " " + user.lastName)
                    binding.tvBio.setText(user.bio)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Kezeles a hiba eseten
                    // Például: Toast üzenet megjelenítése
                    Toast.makeText(this@MainProfileActivity, "Adatbázishoz való hozzáférés sikertelen", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            // Ha nincs bejelentkezett felhasználó, ne csinálj semmit
        }
    }

}