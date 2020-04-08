package fr.isen.victoire.androidtoolbox.BLEScan

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.victoire.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_blescan_rv.view.*

class BLEScanRV ( private val scanResults: ArrayList<ScanResult>, private val deviceClickListener: (BluetoothDevice) -> Unit) :
    RecyclerView.Adapter<BLEScanRV.BluetoothViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_blescan_rv, parent, false)
        return BluetoothViewHolder(view)

    }
    override fun getItemCount(): Int = scanResults.size

    override fun onBindViewHolder(holder: BluetoothViewHolder, position: Int) {
        holder.deviceAddress.text = scanResults[position].device.address
        holder.deviceRssi.text = scanResults[position].rssi.toString()
        holder.deviceName.text = scanResults[position].device.name ?: "Nom inconnu"
        holder.layout.setOnClickListener{
            deviceClickListener.invoke(scanResults[position].device)
        }
    }

    fun addDeviceToList(result: ScanResult) {
        val index = scanResults.indexOfFirst { it.device.address == result.device.address }
        if (index != -1) {
            scanResults[index] = result
        }else {
            scanResults.add(result)
        }
    }

    fun clearResults() {
        scanResults.clear()
    }

    class BluetoothViewHolder(bluetoothView: View) : RecyclerView.ViewHolder(bluetoothView) {
        val layout = bluetoothView.deviceLayout
        val deviceName: TextView = bluetoothView.dName
        val deviceAddress: TextView =  bluetoothView.dAddress
        val deviceRssi: TextView = bluetoothView.dRSSI
    }


}