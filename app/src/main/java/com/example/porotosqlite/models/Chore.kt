package com.example.porotosqlite.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

class Chore() {
    var choreID: Int? = null
    var choreName: String? = null
    var assignedBy: String? = null
    var assignedTo: String? = null
    var timeAssigned: Long? = null

    @SuppressLint("SimpleDateFormat")
    fun showHumanDate(chore: Chore): String {
        val simple = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val result = Date(chore.timeAssigned!!)
        return simple.format(result)
    }

    constructor(
        choreID: Int,
        choreName: String,
        assignedBy: String,
        assignedTo: String,
        timeAssigned: Long
    ) : this() {
        this.choreID = choreID
        this.choreName = choreName
        this.assignedBy = assignedBy
        this.assignedTo = assignedTo
        this.timeAssigned = timeAssigned
    }
}