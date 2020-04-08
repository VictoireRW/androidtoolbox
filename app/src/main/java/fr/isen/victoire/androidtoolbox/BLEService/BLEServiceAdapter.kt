package fr.isen.victoire.androidtoolbox.BLEService

import android.app.AlertDialog
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.activity_bledevice_adapter.view.*
import kotlinx.android.synthetic.main.activity_bledevice_rv.view.*
import fr.isen.victoire.androidtoolbox.R

import android.graphics.Matrix
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.ble_alertdialog.view.*


class BLEServiceAdapter(private val serviceList : MutableList<BLEService>,
                        gatt: BluetoothGatt?,
                        var context: Context ):
        ExpandableRecyclerViewAdapter<BLEServiceAdapter.ServiceViewHolder, BLEServiceAdapter.CharacteristicViewHolder>(serviceList){

    val ble: BluetoothGatt? = gatt

    class ServiceViewHolder(itemView: View) : GroupViewHolder(itemView){
        val serviceName : TextView = itemView.serviceName
        val charUUID : TextView = itemView.characteristicUUID
        val arrowDown: ImageView = itemView.flecheBas
        val arrowUp: ImageView = itemView.flecheHaut

        override fun expand() {
            arrowDown.visibility = View.GONE
            arrowUp.visibility = View.VISIBLE
            Log.i("BLEService","expand")
        }

        override fun collapse() {
            arrowDown.visibility = View.VISIBLE
            arrowUp.visibility = View.GONE
            Log.i("BLEService","collapse")
        }
    }

    class CharacteristicViewHolder (itemView:View) : ChildViewHolder(itemView){
        val characteristicUUID : TextView = itemView.UUID
        val lire: TextView = itemView.bRead
        val ecrire: TextView = itemView.bWrite
        val notifier: TextView = itemView.bNotify
        val proprietes: TextView = itemView.device
        val valeurBle: TextView = itemView.valeur
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder =
        ServiceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_bledevice_rv,
                parent,
                false
            )
        )


    override fun onCreateChildViewHolder(parent:ViewGroup, viewType:Int): CharacteristicViewHolder =
        CharacteristicViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_bledevice_adapter,
                parent,
                false
            )
        )

    override fun onBindChildViewHolder(holder: CharacteristicViewHolder, flatPosition : Int, group : ExpandableGroup<*>, childIndex:Int){
       val characteristic : BluetoothGattCharacteristic=(group as BLEService).items[childIndex]
        val uuid = characteristic.uuid
        holder.characteristicUUID.text = uuid.toString()
        holder.lire.visibility = View.GONE
        holder.ecrire.visibility = View.GONE
        holder.notifier.visibility = View.GONE

        if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) {
            holder.lire.visibility = View.VISIBLE
        }
        if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) {
            holder.ecrire.visibility = View.VISIBLE
        }
        if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
            holder.notifier.visibility = View.VISIBLE
        }
        holder.characteristicUUID.text = uuid.toString()
        holder.proprietes.text = "Proprietés : ${characteristic.properties}"
        ble?.readCharacteristic(characteristic)
        if(characteristic.value != null){
            holder.valeurBle.text =  "Valeur : ${String (characteristic.value)}"
        } else {
            holder.valeurBle.text =  "Valeur : null"
        }
        holder.lire.setOnClickListener {
            ble?.readCharacteristic(characteristic)
            if(characteristic.value != null){
                holder.valeurBle.text =  "Valeur : ${String (characteristic.value)}"
            } else {
                holder.valeurBle.text =  "Valeur : null"
            }
        }
        holder.ecrire.setOnClickListener {
            val popup = AlertDialog.Builder(context)
            val dialogLayout = View.inflate(context, R.layout.ble_alertdialog, null)
            popup.setView(dialogLayout)
            popup.setPositiveButton("Valider", DialogInterface.OnClickListener {
                    _, _ -> ble?.writeCharacteristic(characteristic)
            })
            popup.show()
        }
    }

    override fun onBindGroupViewHolder(holder: ServiceViewHolder, flatPosition:Int, group: ExpandableGroup<*>){
        val title = group.title
        holder.charUUID.text= title
        holder.arrowDown.visibility = View.VISIBLE
        holder.arrowUp.visibility = View.GONE
        var uuidName: String = when (group.title) {
            "00001801-0000-1000-8000-00805f9b34fb" -> "Attirbut générique"
            "00001800-0000-1000-8000-00805f9b34fb" -> "Accès générique"
            else -> "Service spécifique"
        }
        holder.serviceName.text=uuidName
    }
}

