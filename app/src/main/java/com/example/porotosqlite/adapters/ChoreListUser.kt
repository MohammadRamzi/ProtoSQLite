package com.example.porotosqlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.porotosqlite.R
import com.example.porotosqlite.models.Chore


class ChoreListUser(private val list: ArrayList<Chore>, private val context: Context) :
    RecyclerView.Adapter<ChoreListUser.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoreListUser.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_view_user, parent, false)
        return ViewHolder(view, context, list)
    }

    override fun onBindViewHolder(holder: ChoreListUser.ViewHolder, position: Int) {
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

        }
    }
}