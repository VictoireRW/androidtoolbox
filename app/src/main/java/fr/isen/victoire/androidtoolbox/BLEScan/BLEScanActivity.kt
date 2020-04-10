package fr.isen.victoire.androidtoolbox.BLEScan

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.victoire.androidtoolbox.BLEDevice.BLEDeviceActivity
import kotlinx.android.synthetic.main.activity_blescan.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name
import android.R



class BLEScanActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private var mScanning: Boolean = false
    private lateinit var adapter: BLEScanRV
    private val devices = ArrayList<ScanResult>()

    companion object {
        private val REQUEST_ENABLE_BT = 1
        private val SCAN_PERIOD: Long = 4000

    }

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val isBLEEnable: Boolean
        get() = bluetoothAdapter?.isEnabled == true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fr.isen.victoire.androidtoolbox.R.layout.activity_blescan)
        val progressBar = findViewById<ProgressBar>(fr.isen.victoire.androidtoolbox.R.id.progressBar)
        scanText.text = "Lancer un scan"

        devicesRV.adapter =
            BLEScanRV(devices, ::onDeviceClicked)
        devicesRV.layoutManager = LinearLayoutManager(this)
            playButton.setOnClickListener() {
                playButton.visibility = View.GONE
                pauseButton.visibility = View.VISIBLE

                when {
                    isBLEEnable -> {
                        initBLEScan()
                        initScan()
                    }

                    bluetoothAdapter != null -> {
                        // demande d'activation bluetooth
                        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        startActivityForResult(enableBtIntent,
                            REQUEST_ENABLE_BT
                        )
                    }
                    else -> {
                        // device pas compatible BLE
                        DeviceNCompatible.visibility = View.VISIBLE

                    }
                }
            }
        pauseButton.setOnClickListener() {
            pauseButton.visibility = View.GONE
            playButton.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            divider.visibility = View.VISIBLE
            scanText.text = "Scan arrêté"
            onStop()
        }

    }

    override fun onStop() {
        super.onStop()
        if (isBLEEnable) {
            scanLeDevice(false)
        }
    }

    private fun initScan() {
        progressBar.visibility = View.VISIBLE
        //val progressBar = findViewById(fr.isen.victoire.androidtoolbox.R.id.progressBar) as ProgressBar
        progressBar.secondaryProgress = 100
        progressBar.progress = 10
        progressBar.max = 100
        divider.visibility = View.GONE
        scanText.text = "Scan en cours"
        handler = Handler()
        scanLeDevice(true)
    }

    private fun initBLEScan() {
        adapter =
            BLEScanRV(arrayListOf(), ::onDeviceClicked)
        devicesRV.adapter = adapter
        devicesRV.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        handler = Handler()

        scanLeDevice(true)
        devicesRV.setOnClickListener{
            scanLeDevice(!mScanning)
        }



    }

    private val leScanCallBack = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.w("BLEScanActivity", "${result.device}")
            runOnUiThread {
                adapter.addDeviceToList(result)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun scanLeDevice(enable: Boolean) {
        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if (enable) {
                Log.w("BLEScanActivity", "Scanning for devices")
                handler.postDelayed({
                    mScanning = false
                    stopScan(leScanCallBack)
                    scanText.text = "Scan fini"
                    progressBar.visibility = View.INVISIBLE
                    divider.visibility = View.VISIBLE

                }, SCAN_PERIOD)
                mScanning = true
                startScan(leScanCallBack)
            } else {
                mScanning = false
                stopScan(leScanCallBack)
            }
        }
    }

    private fun onDeviceClicked(device: BluetoothDevice) {
        val intent = Intent(this, BLEDeviceActivity::class.java)
        intent.putExtra("ble_device", device)
        startActivity(intent)
    }


}
