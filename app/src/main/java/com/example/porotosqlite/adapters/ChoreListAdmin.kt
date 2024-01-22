package com.example.porotosqlite.adapters

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.porotosqlite.R
import com.example.porotosqlite.R.id.popAssignedByID
import com.example.porotosqlite.R.id.popAssignedToID
import com.example.porotosqlite.R.id.popEnterChoreID
import com.example.porotosqlite.R.id.popSaveChoreID
import com.example.porotosqlite.dataLayer.ChoresDatabaseHandler
import com.example.porotosqlite.models.Chore


class ChoreListAdmin(private val list: ArrayList<Chore>, private val context: Context) :
    RecyclerView.Adapter<ChoreListAdmin.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoreListAdmin.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_view_admin, parent, false)
        return ViewHolder(view, context, list)
    }

    override fun onBindViewHolder(holder: ChoreListAdmin.ViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View, context: Context, choreList: ArrayList<Chore>) :
        RecyclerView.ViewHolder(itemView) {
        var contexHere = context
        var listHere = choreList

        var choreName = itemView.findViewById<TextView>(R.id.listChoreName)
        var assignedBy = itemView.findViewById<TextView>(R.id.assignedByID)
        var assignedTo = itemView.findViewById<TextView>(R.id.assignedTo)
        var assignedTime = itemView.findViewById<TextView>(R.id.assignedTime)

        var deleteButton = itemView.findViewById<Button>(R.id.listDeleteButton)
        var editButton = itemView.findViewById<Button>(R.id.listEditButton)

        fun bindView(chore: Chore) {
            choreName.text = chore.choreName
            assignedBy.text = chore.assignedBy
            assignedTo.text = chore.assignedTo
            assignedTime.text = chore.showHumanDate(chore)

            var positionHere = adapterPosition
            var chore: Chore = listHere[positionHere]

            deleteButton.setOnClickListener {
                deleteChore(chore.choreID!!.toInt())
                listHere.removeAt(positionHere)
                notifyItemRemoved(positionHere)
            }
            editButton.setOnClickListener {
                updateChore(chore)
            }
        }

        fun deleteChore(id: Int) {
            val db = ChoresDatabaseHandler(contexHere)
            db.deleteChore(id)
        }

        fun updateChore(chore: Chore) {

            var alertDialogBuilder: AlertDialog.Builder?
            var alertDialog: AlertDialog?
            var dbHandler = ChoresDatabaseHandler(contexHere)

            var view = LayoutInflater.from(contexHere).inflate(R.layout.popup, null)
            var choreName = view.findViewById<EditText>(popEnterChoreID)
            var assignedBy = view.findViewById<EditText>(popAssignedByID)
            var assignedTo = view.findViewById<EditText>(popAssignedToID)
            var saveButton = view.findViewById<Button>(popSaveChoreID)

            choreName.setText(chore.choreName)
            assignedBy.setText(chore.assignedBy)
            assignedTo.setText(chore.assignedTo)

            alertDialogBuilder = AlertDialog.Builder(contexHere).setView(view)
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
                    chore.choreName = choreNameStr
                    chore.assignedTo = assignedToStr
                    chore.assignedBy = assignedByStr

                    dbHandler?.updateChore(chore)
                    notifyItemChanged(adapterPosition,chore)
                    alertDialog!!.dismiss()

//                    getAllDataFromDatabase()

                } else {
//                    Toast.makeText(this,"Please Enter Chore!",Toast.LENGTH_LONG).show()
                }

            }

        }

    }
}