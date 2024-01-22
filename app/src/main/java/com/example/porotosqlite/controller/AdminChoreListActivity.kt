package com.example.porotosqlite.controller

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.porotosqlite.R
import com.example.porotosqlite.R.id.popAssignedByID
import com.example.porotosqlite.R.id.popAssignedToID
import com.example.porotosqlite.R.id.popEnterChoreID
import com.example.porotosqlite.R.id.popSaveChoreID
import com.example.porotosqlite.adapters.ChoreListAdmin
import com.example.porotosqlite.dataLayer.ChoresDatabaseHandler
import com.example.porotosqlite.models.Chore

class AdminChoreListActivity : AppCompatActivity() {

    private var adapter: ChoreListAdmin? = null
    private var choreList: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var dbHandler: ChoresDatabaseHandler? = null
    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)
        choreList = ArrayList()
        layoutManager = LinearLayoutManager(this)
        dbHandler = ChoresDatabaseHandler(this)
        getAllDataFromDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_menu_button) {
            Toast.makeText(this, "Add Button Clicked", Toast.LENGTH_LONG).show()
            createPopupDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog() {
        var view = layoutInflater.inflate(R.layout.popup, null)
        var choreName = view.findViewById<EditText>(popEnterChoreID)
        var assignedBy = view.findViewById<EditText>(popAssignedByID)
        var assignedTo = view.findViewById<EditText>(popAssignedToID)
        var saveButton = view.findViewById<Button>(popSaveChoreID)

        alertDialogBuilder = AlertDialog.Builder(this).setView(view)
        alertDialog = alertDialogBuilder!!.create()
        alertDialog?.show()

        saveButton.setOnClickListener {
            var choreNameStr = choreName.text.toString().trim()
            var assignedByStr = assignedBy.text.toString().trim()
            var assignedToStr = assignedTo.text.toString().trim()
            if (
                !TextUtils.isEmpty(choreNameStr) &&
                !TextUtils.isEmpty(assignedByStr) &&
                !TextUtils.isEmpty(assignedToStr)
            ) {
                var chore: Chore = Chore()
                chore.choreName = choreNameStr
                chore.assignedTo = assignedToStr
                chore.assignedBy = assignedByStr

                dbHandler?.createChore(chore)
                alertDialog!!.dismiss()

                getAllDataFromDatabase()

            } else {
                Toast.makeText(this, "Please Enter Chore!", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getAllDataFromDatabase() {
        choreList = dbHandler!!.readChores()
        choreList?.reverse()
        adapter = ChoreListAdmin(choreList!!, this)
        val recyclerViewId = findViewById<RecyclerView>(R.id.recyclerViewID)
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }
}