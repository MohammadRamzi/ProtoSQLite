package com.example.porotosqlite.controller

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.porotosqlite.R
import com.example.porotosqlite.dataLayer.ChoresDatabaseHandler
import com.example.porotosqlite.models.Chore


class MainActivity : AppCompatActivity() {

    private var dbHandler: ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterChore = findViewById<EditText>(R.id.enterChoreID)
        val assignedBy = findViewById<EditText>(R.id.assignedByID)
        val assignedTo = findViewById<EditText>(R.id.assignedToID)
        val saveButton = findViewById<Button>(R.id.saveChoreID)

        dbHandler = ChoresDatabaseHandler(this)

        checkDB()

        saveButton.setOnClickListener {
            if (
                !TextUtils.isEmpty(enterChore.text.toString()) &&
                !TextUtils.isEmpty(assignedBy.text.toString()) &&
                !TextUtils.isEmpty(assignedTo.text.toString())
            ) {
                val chore = Chore()
                chore.choreName = enterChore.text.toString()
                chore.assignedBy = assignedBy.text.toString()
                chore.assignedTo = assignedTo.text.toString()

                saveChoreToDatabase(chore)
                Toast.makeText(this, "your chore has been saved successfully", Toast.LENGTH_LONG)
                    .show()

                startActivity(Intent(this, AdminChoreListActivity::class.java))
            } else {
                Toast.makeText(this, "please enter a chore", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun checkDB() {
        if (dbHandler!!.getChoresCount() > 0) {
            startActivity(Intent(this, AdminChoreListActivity::class.java))
        }
    }

    fun saveChoreToDatabase(chore: Chore) {
        dbHandler?.createChore(chore)
    }

}