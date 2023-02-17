package com.example.bottomnavbar

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.bottomnavbar.Fragments.Profile
import com.example.bottomnavbar.Model.UserModel
import com.example.bottomnavbar.databinding.PersonalizationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class EditProfileActvity: AppCompatActivity() {

    private var sImage: String = ""
    private var sBackgroundImage: String = ""
    private lateinit var binding: PersonalizationBinding
    private val root = FirebaseDatabase.getInstance().getReference("User")
    private val reference = FirebaseStorage.getInstance().reference
    private val fAuth = FirebaseAuth.getInstance().currentUser
    private var imageUri: Uri? = null
    private var imageBgUri: Uri? = null
    private val userId = fAuth?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = PersonalizationBinding.inflate(layoutInflater)
        setContentView(this.binding.root)


        root.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = dataSnapshot.child(userId!!).child("name").getValue(String::class.java)
                binding.nameInput.setText(name)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        binding.uploadBtn.setOnClickListener() {

            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, 2)

        }

        binding.uploadBackgroundBtn.setOnClickListener() {

            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, 3)

        }


        binding.closeTask.setOnClickListener() {

            val intent = Intent(this, NavDrawerActivity::class.java)
            intent.putExtra("data",1)
            startActivity(intent)

        }


        binding.saveProfile.setOnClickListener(){
            val name = binding.nameInput.text.toString()
            when {
                TextUtils.isEmpty(name) -> Toast.makeText(this, "Please enter a name.", Toast.LENGTH_SHORT).show()
                imageUri == null ->  Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show()
                else -> {
                    root.child(userId!!).child("name").setValue(name)
                    uploadToFirebase(imageUri!!)
                    uploadBgToFirebase(imageBgUri!!)

                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, NavDrawerActivity::class.java)
                    intent.putExtra("data",1)
                    startActivity(intent)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            binding.imageView.setImageURI(imageUri)
        } else   if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            imageBgUri = data.data
            binding.backgroundView.setImageURI(imageBgUri)
        }
    }

    private fun uploadToFirebase(uri: Uri) {
        val fileRef =
            reference.child( System.currentTimeMillis().toString() + "." + getFileExtension(uri))
        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                val img = uri.toString()
                root.child(userId!!).child("profileImg").setValue(img)
            }
        } .addOnFailureListener {
                Toast.makeText(this, "Uploading Failed !!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadBgToFirebase(uri: Uri) {
        val fileRef =
            reference.child("BG " + System.currentTimeMillis().toString() + "." + getFileExtension(uri))
        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                val img = uri.toString()
                root.child(userId!!).child("backgroundImg").setValue(img)
            }
        } .addOnFailureListener {
            Toast.makeText(this, "Uploading Failed !!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileExtension(mUri: Uri): String? {
        val cr = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(mUri))
    }
}