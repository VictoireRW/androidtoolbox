package fr.isen.victoire.androidtoolbox

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var PRIVATE_MODE = 0

        val sharedPref: SharedPreferences = getSharedPreferences("key", PRIVATE_MODE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        homeCycle.setOnClickListener {
            val intent = Intent(this, CycleActivity::class.java) //f
            startActivity(intent)
        }
        HomeSave.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java) //f
            startActivity(intent)
        }
        loginButton.setOnClickListener {
            editor.clear()
            editor.commit()
            val intent = Intent(this, LoginActivity::class.java) //f
            startActivity(intent)
        }
        PermissionsButton.setOnClickListener {
            val intent = Intent(this, PermissionsActivity::class.java) //f
            startActivity(intent)
        }
    }
}
