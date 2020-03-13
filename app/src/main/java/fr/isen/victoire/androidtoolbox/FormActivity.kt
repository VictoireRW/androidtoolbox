package fr.isen.victoire.androidtoolbox

import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_form.*
import org.json.JSONObject
import java.io.File
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class FormActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val cal : Calendar = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener{ datePicker : DatePicker, year : Int, monthOfYear : Int, dayOfMonth : Int ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                /*dateButton.text = sdf.format(cal.time)
                var age = 0

                val today = Calendar.getInstance()
                dateButton.text = sdf.format(cal.time)
                age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR)

                if (today.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR))
                    age--*/

        }

        fun showDatePicker(dateSetListener:DatePickerDialog.OnDateSetListener){
            val cal = Calendar.getInstance()
            DatePickerDialog(this@FormActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        sendButton.setOnClickListener {
            val json = JSONObject()
            json.put("name", formName.text.toString())
            json.put("firstname", formFisrtname.text.toString())
            json.put("date", dateButton.text)
            //json.put("age", age)
            val jsonString = json.toString()
            File(cacheDir.absolutePath + "form.json").writeText(jsonString)
        }

        dateButton.setOnClickListener {
            showDatePicker(dateSetListener)
        }



        ReadFile.setOnClickListener {




            val result = File(cacheDir.absolutePath + "form.json").readText(Charsets.UTF_8)
            val json = JSONObject(result)
            /*val age = ChronoUnit.YEARS.between(formatted, json.get("date").toString())*/


            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setMessage("Nom "+json.get("name").toString()+"\n" + "Prénom "+json.get("firstname").toString() + "\n" +"Date de Naissance "+json.get("date").toString()+"\n")
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })
            val alert = alertDialogBuilder.create()
            alert.setTitle("formulaire")
            alert.show()
        }
    }
}
