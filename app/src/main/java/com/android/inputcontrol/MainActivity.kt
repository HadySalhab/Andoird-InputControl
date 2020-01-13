package com.android.inputcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.app.ShareCompat

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listOfRadioButton = listOf<RadioButton>(
            findViewById(R.id.rb_dog),
            findViewById(R.id.rb_cat),
            findViewById(R.id.rb_horse)
        )


        val listOfCheckBoxes = listOf<CheckBox>(findViewById(R.id.cb_apple),findViewById(R.id.cb_banana),findViewById(R.id.cb_berries),findViewById(R.id.cb_strawberry))
        val btnToast = findViewById<Button>(R.id.btn_toast)
        btnToast.setOnClickListener {
            var message =""
            listOfCheckBoxes.forEach {checkBox->
                if(checkBox.isChecked){
                    message = message + checkBox.text.toString() + " "
                }
            }
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        }


        val spinner = findViewById<Spinner>(R.id.family_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.countries,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }
        editText = findViewById(R.id.editText)


        editText.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    sendMessage()
                    true
                }
                else -> false
            }
        }



        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "${parent?.getItemAtPosition(position)} is selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        listOfRadioButton.forEach { radioButton ->
            radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    toastTheName(buttonView as RadioButton)
                }
            }
        }
    }
    fun sendMessage(){
        val msg = editText.text.toString()
        ShareCompat.IntentBuilder.from(this)
            .setChooserTitle("Share msg")
            .setText(msg)
            .setType("text/plain")
            .startChooser()
    }

    fun toastTheName(radioButton: RadioButton) {
        Toast.makeText(this, "${radioButton.text} is clicked", Toast.LENGTH_SHORT).show()
    }
}



