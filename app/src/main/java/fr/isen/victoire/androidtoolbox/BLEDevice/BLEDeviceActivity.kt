package fr.isen.victoire.androidtoolbox.BLEDevice

import android.bluetooth.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.victoire.androidtoolbox.BLEService.BLEService
import fr.isen.victoire.androidtoolbox.BLEService.BLEServiceAdapter
import fr.isen.victoire.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_bledevice.*


class BLEDeviceActivity : AppCompatActivity() {

    private var bluetoothGatt: BluetoothGatt? = null
    private var TAG:String = "MyActivity"

    companion object{
        private const val STATE_DISCONNECTED = "déconnecté"
        private const val STATE_CONNECTED = "Connecté"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bledevice)
        val device : BluetoothDevice = intent.getParcelableExtra("ble_device")
        detailsDeviceName.text = device.name
        bluetoothGatt = device.connectGatt(this, true, gattCallback)
        Log.i("detailsBLE", "gatt connected")


    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    runOnUiThread {
                        detailsDeviceStatus.text =
                            STATE_CONNECTED
                    }
                    bluetoothGatt?.discoverServices()
                    Log.i(
                        TAG,
                        "Attempting to start service discovery :" + bluetoothGatt?.discoverServices()
                    )
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    runOnUiThread {
                        detailsDeviceStatus.text =
                            STATE_DISCONNECTED
                    }
                    bluetoothGatt?.discoverServices()
                    Log.i(TAG, "deconnecté")
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            runOnUiThread {
                detailsDeviceRV.adapter =
                    BLEServiceAdapter(gatt?.services?.map {
                        BLEService(
                            it.uuid.toString(),
                            it.characteristics
                        )
                    }?.toMutableList() ?: arrayListOf(), gatt, this@BLEDeviceActivity)
                detailsDeviceRV.layoutManager = LinearLayoutManager(this@BLEDeviceActivity)
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            val value = characteristic.getStringValue(0)
            Log.e(
                "TAG",
                "onCharacteristicRead: " + value + " UUID " + characteristic.uuid.toString()
            )
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic
        ) {
            val value = characteristic.value
            Log.e(
                "TAG",
                "onCharacteristicRead: " + value + " UUID " + characteristic.uuid.toString()
            )
        }
    }}





