package com.haero_kim.aac_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haero_kim.aac_practice.data.Contact
import com.haero_kim.aac_practice.viewmodel.ContactViewModel

class AddActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val editTextName = findViewById<EditText>(R.id.add_edittext_name)
        val editTextNumber = findViewById<EditText>(R.id.add_edittext_number)
        val addButton = findViewById<Button>(R.id.add_button)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        if(intent != null && intent.hasExtra(EXTRA_CONTACT_NAME) && intent.hasExtra(
                EXTRA_CONTACT_NUMBER) && intent.hasExtra(EXTRA_CONTACT_ID)) {
            editTextName.setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
            editTextNumber.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        }

        addButton.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val number = editTextNumber.text.toString()

            if(name.isEmpty() || number.isEmpty()){
                Toast.makeText(this, "Please enter name and number", Toast.LENGTH_LONG).show()
            } else{
                val initial = name[0].toUpperCase()
                val contact = Contact(id, name, number, initial)
                contactViewModel.insert(contact)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
    }
}