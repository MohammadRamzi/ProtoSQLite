package com.example.porotosqlite.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.porotosqlite.R
import com.example.porotosqlite.adapters.ChoreListUser
import com.example.porotosqlite.dataLayer.ChoresDatabaseHandler
import com.example.porotosqlite.models.Chore

class UserChoreListActivity : AppCompatActivity() {

    private var adapter: ChoreListUser? = null
    private var choreList: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var dbHandler: ChoresDatabaseHandler? = null

    companion object { var username: String = "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)
        choreList = ArrayList()
        layoutManager = LinearLayoutManager(this)
        dbHandler = ChoresDatabaseHandler(this)
        getAllDataFromDatabase()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.top_menu, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.add_menu_button) {
//            Toast.makeText(this, "Add Button Clicked", Toast.LENGTH_LONG).show()
//            createPopupDialog()
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    fun createPopupDialog() {
//        var view = layoutInflater.inflate(R.layout.popup, null)
//        var choreName = view.findViewById<EditText>(R.id.popEnterChoreID)
//        var assignedBy = view.findViewById<EditText>(R.id.popAssignedByID)
//        var assignedTo = view.findViewById<EditText>(R.id.popAssignedToID)
//        var saveButton = view.findViewById<Button>(R.id.popSaveChoreID)
//
//        alertDialogBuilder = AlertDialog.Builder(this).setView(view)
//        alertDialog = alertDialogBuilder!!.create()
//        alertDialog?.show()
//
//        saveButton.setOnClickListener {
//            var choreNameStr = choreName.text.toString().trim()
//            var assignedByStr = assignedBy.text.toString().trim()
//            var assignedToStr = assignedTo.text.toString().trim()
//            if (
//                !TextUtils.isEmpty(choreNameStr) &&
//                !TextUtils.isEmpty(assignedByStr) &&
//                !TextUtils.isEmpty(assignedToStr)
//            ) {
//                var chore: Chore = Chore()
//                chore.choreName = choreNameStr
//                chore.assignedTo = assignedToStr
//                chore.assignedBy = assignedByStr
//
//                dbHandler?.createChore(chore)
//                alertDialog!!.dismiss()
//
//                getAllDataFromDatabase()
//
//            } else {
//                Toast.makeText(this, "Please Enter Chore!", Toast.LENGTH_LONG).show()
//            }
//        }
//    }

    fun getAllDataFromDatabase() {
        choreList = dbHandler!!.readChoresOf(username)
        choreList?.reverse()
        adapter = ChoreListUser(choreList!!, this)
        val recyclerViewId = findViewById<RecyclerView>(R.id.recyclerViewID)
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

}
