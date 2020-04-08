package fr.isen.victoire.androidtoolbox.WebServices

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.victoire.androidtoolbox.R
import fr.isen.victoire.androidtoolbox.models.RoundedTransformation
import fr.isen.victoire.androidtoolbox.models.User
import kotlinx.android.synthetic.main.activity_web_recycler_view.view.*


class WebRecyclerView(private val users: User, val context: Context) :
    RecyclerView.Adapter<WebRecyclerView.WebHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.activity_web_recycler_view, parent, false)
        /*view.pictWeb.setOnClickListener {
            val intent = Intent(this, DetailsWS::class.java) //f
            startActivity(intent)
        }*/
        return WebHolder(
            users,
            view,
            context
        )
    }

    override fun getItemCount(): Int {
        return users.results.size
    }

    override fun onBindViewHolder(holder: WebHolder, position: Int) {
        holder.loadInfo(position)
    }

    class WebHolder(private val webUsers: User, view: View, val context: Context) :
        RecyclerView.ViewHolder(view) {
        private val name: TextView = view.nameWeb
        private val image: ImageView = view.pictWeb
        private val address: TextView = view.addressWeb
        private val email: TextView = view.mailWeb

            fun loadInfo(index: Int) {
                val nameWS =
                    webUsers.results[index].name.first + " " + webUsers.results[index].name.last
                val addressWS =
                    webUsers.results[index].location.city + "," + webUsers.results[index].location.state
                Picasso.get()
                    .load(webUsers.results[index].picture.large)
                    .fit().centerInside()
                    .transform(RoundedTransformation(400, 0))
                    .into(image)

                email.text = webUsers.results[index].email
                name.text = nameWS
                address.text = addressWS
            }
    }
}
