package fr.isen.victoire.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_permissions_cell.view.*

class  ContactAdapter(val contact : List<String>) : RecyclerView.Adapter< ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_permissions_cell, parent, false)
            //permet de cree le Layout et le Viewholder associé
            //Viewholder va gerer la vue.
        )

    override fun getItemCount(): Int {
        return contact.size
        // ou on enlève les crochets et met = notreInt
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        holder.contactName.text = contact[position]
    }
    class ContactViewHolder(contactView: View) : RecyclerView.ViewHolder(contactView){
        val contactName: TextView = contactView.contactName
    }


}