package com.cbpajz.kameranz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cbpajz.kameranz.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference

class ProfileActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityProfileBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //
        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        databaseReference=FirebaseDatabase.getInstance().getReference("Users")
        binding.saveBtn.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val bio = binding.etBio.text.toString()
            val user = User(firstName,lastName,bio)

            if (uid != null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this@ProfileActivity,"Sikerült a profilt frissiteni!",Toast.LENGTH_SHORT).show()


                    }else{
                        Toast.makeText(this@ProfileActivity,"Nem sikerült a profilt frissiteni!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        //
        val backActbutton = findViewById<Button>(R.id.profile_back)
        backActbutton.setOnClickListener {
            val Intent = Intent(this,MainProfileActivity::class.java)
            startActivity(Intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
    }

/*
    private fun uploadProfilePic() {
        imageUri= Uri.parse("android.resource:://$packageName/${R.drawable.baseline_person_24}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+firebaseAuth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(this@ProfileActivity,"Profil kép frissitése sikeres!",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this@ProfileActivity,"Profil kép frissitése nem sikerült!",Toast.LENGTH_SHORT).show()
        }
    }
 */

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser==null){
            val intent = Intent(this,Loginregister::class.java)
            startActivity(intent)
        }
    }
}