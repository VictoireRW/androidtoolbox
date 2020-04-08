package fr.isen.victoire.androidtoolbox.Permissions

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.victoire.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_permissions.*

class PermissionsActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    private val TAG = "PermissionDemo"
    private val RECORD_REQUEST_CODE = 101
    val contact = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)
        //setupPermissions()
        photoContacts.setOnClickListener {

            showPictureDialog()
        }

        loadContacts()

    }
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent,
                    PERMISSION_CODE
                )
            }
        }
    }

    private fun showPictureDialog() {
        val pictureDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf(
            "Select photo from gallery",
            "Capture photo from camera"
        )
        pictureDialog.setItems(pictureDialogItems,
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> pickImageFromGallery()
                    1 -> takePictureIntent()
                }
            })
        pictureDialog.show()
    }
    companion object { //permet de faire des static
        //image pick code
        private const val IMAGE_PICK_CODE = 1000;
        //Permission code
        private const val PERMISSION_CODE = 1001;
    }
    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            photoContacts.setImageURI(data?.data)
        }else if (resultCode == Activity.RESULT_OK && requestCode == PERMISSION_CODE) {
            var bmp = data?.extras?.get("data") as Bitmap
            photoContacts.setImageBitmap(bmp)
        }
    }
    private fun loadContacts() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                1)
            //callback onRequestPermissionsResult
        } else {
            getContacts()
            contactRecycler.adapter =
                ContactAdapter(contact.sorted())  //optimiser au max l'affichage des cell contactRecycler: id recycleView
            contactRecycler.layoutManager = LinearLayoutManager(this)
        }
    }
    private fun getContacts() {
        val resolver: ContentResolver = contentResolver;
        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contact.add("  Nom : $name")
            }
        } else {
            Toast.makeText(applicationContext, "No contacts available!", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
    }

}


