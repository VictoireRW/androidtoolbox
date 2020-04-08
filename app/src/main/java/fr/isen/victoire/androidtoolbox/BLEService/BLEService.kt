package fr.isen.victoire.androidtoolbox.BLEService

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class BLEService(title: String, items : List<BluetoothGattCharacteristic>):
        ExpandableGroup<BluetoothGattCharacteristic>(title, items)
