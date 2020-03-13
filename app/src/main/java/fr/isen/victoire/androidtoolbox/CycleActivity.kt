package fr.isen.victoire.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cycle.*

class CycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cycle)
        cycleActivity.text = "activity created"
    }

    override fun onStart() {
        super.onStart()
        cycleActivity.text =   cycleActivity.text.toString() + "\nactivity started "
    }

    override fun onResume() {
        super.onResume()
        cycleActivity.text =   cycleActivity.text.toString() + "\nactivity resumed "
    }

    override fun onPause() {
        super.onPause()
        Log.i("pause", "activity paused")
        Toast.makeText(applicationContext, "activity paused", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("destroyed", "activity destroyed");
        Toast.makeText(applicationContext, "activity destroyed", Toast.LENGTH_SHORT).show()
    }
}
