package com.example.porotosqlite.dataLayer

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.porotosqlite.models.Chore
import com.example.porotosqlite.models.DATABASE_NAME
import com.example.porotosqlite.models.DATABASE_VERSION
import com.example.porotosqlite.models.KEY_USER_PASSWORD
import com.example.porotosqlite.models.KEY_CHORE_ASSIGNED_TIME
import com.example.porotosqlite.models.KEY_CHORE_ASSIGNED_TO
import com.example.porotosqlite.models.KEY_USER_NAME
import com.example.porotosqlite.models.KEY_CHORE_ID
import com.example.porotosqlite.models.CHORE_TABLE_NAME
import java.text.DateFormat
import java.util.Date

class ChoresDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CHORE_TABLE = "CREATE TABLE " + CHORE_TABLE_NAME +
                "(" + KEY_CHORE_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_NAME + " TEXT," +
                KEY_USER_PASSWORD + " TEXT," +
                KEY_CHORE_ASSIGNED_TO + " TEXT," +
                KEY_CHORE_ASSIGNED_TIME + " LONG);"

        db?.execSQL(CREATE_CHORE_TABLE)

        Log.d("TABLE CREATED", "SUCCESS")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $CHORE_TABLE_NAME")
        onCreate(db)
    }

    fun createChore(chore: Chore) {
        val db: SQLiteDatabase = writableDatabase

        val values: ContentValues = ContentValues()
        values.put(KEY_USER_NAME, chore.choreName)
        values.put(KEY_USER_PASSWORD, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(CHORE_TABLE_NAME, null, values)
        db.close()

        Log.d("DATA INSERTED", "SUCCESS")
    }

    @SuppressLint("Range", "Recycle")
    fun getAChore(id: Int): Chore? {
        val db: SQLiteDatabase = writableDatabase
        val cursor = db.query(
            CHORE_TABLE_NAME, arrayOf(
                KEY_CHORE_ID,
                KEY_USER_NAME,
                KEY_CHORE_ASSIGNED_TO,
                KEY_USER_PASSWORD,
                KEY_CHORE_ASSIGNED_TIME
            ), "$KEY_CHORE_ID=?", arrayOf(id.toString()),
            null,
            null,
            null
        )

        if (cursor != null) {
            cursor.moveToFirst()

            val chore = Chore()
            chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME))
            chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_USER_PASSWORD))
            chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
            chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

            val dateFormat: DateFormat = DateFormat.getDateInstance()
            var formattedDate =
                dateFormat.format(Date(cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))).time)

            return chore
        }

        return null
    }

    @SuppressLint("Range", "Recycle")
    fun readChores(): ArrayList<Chore> {
        val db: SQLiteDatabase = readableDatabase
        val list: ArrayList<Chore> = ArrayList()
        val selectAll = "SELECT * FROM $CHORE_TABLE_NAME"
        val cursor: Cursor = db.rawQuery(selectAll, null)
        if (cursor.moveToFirst()) {
            do {
                val chore = Chore()
                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME))
                chore.choreID = cursor.getInt(cursor.getColumnIndex(KEY_CHORE_ID))
                chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
                chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_USER_PASSWORD))
                chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

                list.add(chore)
            } while (cursor.moveToNext())
        }
        return list
    }

    @SuppressLint("Range", "Recycle")
    fun readChoresOf(username: String?): ArrayList<Chore> {
        val db: SQLiteDatabase = readableDatabase
        val list: ArrayList<Chore> = ArrayList()
        val selectAll = "SELECT * FROM $CHORE_TABLE_NAME WHERE $KEY_CHORE_ASSIGNED_TO = '$username'"
        val cursor: Cursor = db.rawQuery(selectAll, null)
        if (cursor.moveToFirst()) {
            do {
                val chore = Chore()
                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME))
                chore.choreID = cursor.getInt(cursor.getColumnIndex(KEY_CHORE_ID))
                chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
                chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_USER_PASSWORD))
                chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

                list.add(chore)
            } while (cursor.moveToNext())
        }
        Log.d("key ass to",list.toString())
        return list
    }

    fun updateChore(chore: Chore): Int {
        val db: SQLiteDatabase = writableDatabase

        val values: ContentValues = ContentValues()
        values.put(KEY_USER_NAME, chore.choreName)
        values.put(KEY_USER_PASSWORD, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        return db.update(CHORE_TABLE_NAME, values, "$KEY_CHORE_ID=?", arrayOf(chore.choreID.toString()))
    }

    fun deleteChore(chore: Chore) {
        val db: SQLiteDatabase = writableDatabase
        db.delete(CHORE_TABLE_NAME, "$KEY_CHORE_ID=?", arrayOf(chore.choreID.toString()))
        db.close()
    }
    fun deleteChore(id: Int) {
        val db: SQLiteDatabase = writableDatabase
        db.delete(CHORE_TABLE_NAME, "$KEY_CHORE_ID=?", arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Recycle")
    fun getChoresCount(): Int {
        val db: SQLiteDatabase = readableDatabase
        val countQuery = "SELECT * FROM $CHORE_TABLE_NAME"
        val cursor: Cursor = db.rawQuery(countQuery, null)
        return cursor.count
    }

}