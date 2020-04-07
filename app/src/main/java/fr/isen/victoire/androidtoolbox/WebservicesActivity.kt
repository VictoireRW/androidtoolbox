package fr.isen.victoire.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_webservices.*



class WebservicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webservices)
        val queue = Volley.newRequestQueue(this)
        //var user = User()
        val stringRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://randomuser.me/api/?inc=name%2Cpicture%2Clocation%2Cemail&noinfo=&nat=fr&format=pretty&results=15",
            null,
            Response.Listener { response ->
                val gson = Gson()
                val user = gson.fromJson(response.toString(), User::class.java)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = WebRecyclerView(user,this)
                recyclerView.visibility = View.VISIBLE
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



