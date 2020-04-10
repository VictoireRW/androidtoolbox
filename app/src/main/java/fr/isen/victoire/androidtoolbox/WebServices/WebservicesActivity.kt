package fr.isen.victoire.androidtoolbox.WebServices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.victoire.androidtoolbox.R
import fr.isen.victoire.androidtoolbox.models.User
import kotlinx.android.synthetic.main.activity_webservices.*



class WebservicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webservices)
        val queue = Volley.newRequestQueue(this)
        val stringRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://randomuser.me/api/?inc=name%2Cpicture%2Clocation%2Cemail&noinfo=&nat=fr&format=pretty&results=15",
            null,
            Response.Listener { response ->
                val gson = Gson()
                val user = gson.fromJson(response.toString(), User::class.java)
                contactRecycler.layoutManager = LinearLayoutManager(this)
                contactRecycler.adapter =
                    WebRecyclerView(user, this)
                contactRecycler.visibility = View.VISIBLE
            },
            Response.ErrorListener {
                Log.d("TAG", "Error")
            }
        )
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        //return user
    }

}



