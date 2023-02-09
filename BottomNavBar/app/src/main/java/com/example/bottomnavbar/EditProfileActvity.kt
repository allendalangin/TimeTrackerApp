package com.example.bottomnavbar

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomnavbar.databinding.PersonalizationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.core.View
import java.io.ByteArrayOutputStream
import java.util.Base64

class EditProfileActvity: AppCompatActivity() {

    var sImage: String? = ""
    var sBackgroundImage: String? = ""
    private lateinit var binding: PersonalizationBinding
    private lateinit var database: DatabaseReference
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = PersonalizationBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        val database = FirebaseDatabase.getInstance()
        val fAuth = FirebaseAuth.getInstance().currentUser
        val userId = fAuth?.uid
        val ref = database.getReference("User")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = dataSnapshot.child(userId!!).child("name").getValue(String::class.java)
                binding.nameInput.setText(name)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        binding.closeTask.setOnClickListener() {
            val name = sBackgroundImage.toString()
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
        }

        binding.saveProfile.setOnClickListener(){
            val name = binding.nameInput.text.toString()
            when {
                TextUtils.isEmpty(name) -> Toast.makeText(this, "Please enter a name.", Toast.LENGTH_SHORT).show()
                else -> {
                    ref.child(userId!!).child("name").setValue(name)
                    ref.child(userId!!).child("profileImg").setValue(sImage)
                    ref.child(userId!!).child("backgroundImg").setValue(sBackgroundImage)

                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

                    this.startActivity(Intent(this, NavDrawerActivity::class.java))
        }
            }
        }
    }

    private val ActivityResultLauncher = registerForActivityResult<Intent,ActivityResult> (
        ActivityResultContracts.StartActivityForResult()
            ){result : ActivityResult ->
        if(result.resultCode== RESULT_OK) {
            val uri = result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitMap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitMap.compress(Bitmap.CompressFormat.PNG,100, stream)
                val bytes = stream.toByteArray()
                sImage = android.util.Base64.encodeToString(bytes,android.util.Base64.DEFAULT)
                binding.imageView.setImageBitmap(myBitMap)
                inputStream!!.close()
                Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show()
            } catch (ex: Exception) {
                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private val BackgroundActivityResultLauncher = registerForActivityResult<Intent,ActivityResult> (
        ActivityResultContracts.StartActivityForResult()
    ){result : ActivityResult ->
        if(result.resultCode== RESULT_OK) {
            val uri = result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitMap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitMap.compress(Bitmap.CompressFormat.PNG,100, stream)
                val bytes = stream.toByteArray()
                sBackgroundImage = android.util.Base64.encodeToString(bytes,android.util.Base64.DEFAULT)
                binding.backgroundView.setImageBitmap(myBitMap)
                inputStream!!.close()
                Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show()
            } catch (ex: Exception) {
                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun insert_Img(view: android.view.View) {
        var myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.setType("image/*")
        ActivityResultLauncher.launch(myfileintent)
    }

    fun insert_BackgroundImg(view: android.view.View) {
        var myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.setType("image/*")
        BackgroundActivityResultLauncher.launch(myfileintent)
    }
}