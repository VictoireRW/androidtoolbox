package fr.isen.victoire.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import android.content.SharedPreferences




class LoginActivity : AppCompatActivity() {
    val goodId = "admin"
    val googPassword = "123"
    var PRIVATE_MODE = 0
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPref = getSharedPreferences("key", PRIVATE_MODE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        if (sharedPref.getString("UserName","")==goodId && sharedPref.getString("password","")==googPassword) {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
            Toast.makeText(this, "login dejà enregistré", Toast.LENGTH_SHORT).show()

        }
        else {
            loginValidate.setOnClickListener {
                var logId = loginIdentifiant.text.toString()
                var logPw = loginPassword.text.toString()
                if (logId == goodId && logPw == googPassword) {
                    //save datas
                    editor.putString("UserName", logId)
                    editor.putString("password", logPw)
                    editor.apply()
                    Toast.makeText(this, "username/psw saved", Toast.LENGTH_SHORT).show()
                    //changer de page
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText( this, "votre id : ${loginIdentifiant.text.toString()}", Toast.LENGTH_LONG ).show()
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
