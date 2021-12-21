package com.example.mynotesactivity.DBHandler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcel
import android.os.Parcelable
import com.example.mynotesactivity.Model.Notes

class DatabaseHandler(context:Context) :SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "note_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NOTE = "note_tbl"

        //columns for notes_tbl

        private const val NOTE_ID = "_id"
        private const val NOTE_TITLE = "note_title"
        private const val NOTE_TEXT = "note_text"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_NOTE:String = ("CREATE TABLE $TABLE_NOTE" +
                "($NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "$NOTE_TITLE TEXT," +
                "$NOTE_TEXT TEXT)")

        db!!.execSQL(CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NOTE")
        onCreate(db)
    }

    fun addNotes(note: Notes):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(NOTE_TITLE,note.title)
        contentValues.put(NOTE_TEXT,note.note)

        val response = db.insert(TABLE_NOTE,null,contentValues)

        db.close()

        return response
    }

    fun readNotes():ArrayList<Notes>{

        val db = this.readableDatabase

        val notesCursor = db.rawQuery("SELECT *FROM $TABLE_NOTE",null)

        val  notesList:ArrayList<Notes> = ArrayList()

        if (notesCursor.moveToFirst()){
            do {
                notesList.add(Notes(notesCursor.getInt(0),
                                    notesCursor.getString(1),
                                    notesCursor.getString(2)))
            }while (notesCursor.moveToNext())
        }
        return notesList
    }

    fun deleteNotes(id:Int):Int{

        var db = this.writableDatabase

        var rows = db.delete(TABLE_NOTE,"$NOTE_ID=?", arrayOf<String>(id.toString()))

        return rows

    }
}