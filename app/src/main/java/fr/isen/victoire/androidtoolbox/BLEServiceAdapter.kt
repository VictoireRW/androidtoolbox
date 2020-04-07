package fr.isen.victoire.androidtoolbox

import android.bluetooth.BluetoothGattCharacteristic
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.activity_bledevice_rv.view.*


class BLEServiceAdapter(private val serviceList : MutableList<BLEService>):
        ExpandableRecyclerViewAdapter<BLEServiceAdapter.ServiceViewHolder, BLEServiceAdapter.CharacteristicViewHolder>(serviceList){
    class ServiceViewHolder(itemView: View) : GroupViewHolder(itemView){
        val serviceName : TextView = itemView.serviceName
    }
    class CharacteristicViewHolder (itemView:View) : ChildViewHolder(itemView){
        val characteristicUUID : TextView = itemView.characteristicUUID

    }
    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int):ServiceViewHolder =
        ServiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_bledevice_rv, parent, false))

    override fun onCreateChildViewHolder(parent:ViewGroup, viewType:Int):CharacteristicViewHolder=
        CharacteristicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_bledevice_adapter, parent,false))

    override fun onBindChildViewHolder(holder:CharacteristicViewHolder, flatPosition : Int, group : ExpandableGroup<*>, childIndex:Int){
        val characteristic : BluetoothGattCharacteristic=(group as BLEService).items[childIndex]
        val uuid = characteristic.uuid
        holder.characteristicUUID.text = uuid.toString()
    }
    override fun onBindGroupViewHolder(holder:ServiceViewHolder, flatPosition:Int, group: ExpandableGroup<*>){
        val title = group.title
        holder.serviceName.text= title
    }
}