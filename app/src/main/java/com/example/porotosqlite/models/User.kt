package com.example.porotosqlite.models

class User() {
    var userID: Int? = null
    var username: String? = null
    var password: String? = null

    constructor(
        userID: Int,
        username: String,
        password: String,
    ) : this() {
        this.userID = userID
        this.username = username
        this.password = password
    }

}