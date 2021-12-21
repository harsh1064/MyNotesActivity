package com.example.mynotesactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesactivity.DBHandler.DatabaseHandler
import com.example.mynotesactivity.Model.Notes
import com.example.mynotesactivity.RecyclerAdapter.NotesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var Fab: FloatingActionButton
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fab = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.recyclerView)

        Fab.setOnClickListener {
            var intent: Intent = Intent(this,NotesActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        ViewNotes()
    }

    private fun ViewNotes() {
        val dbHandler = DatabaseHandler(this)
        val noteList:ArrayList<Notes> = dbHandler.readNotes()

        recyclerView.adapter = NotesAdapter(this,noteList)
    }

//    override fun onResume() {
//        super.onResume()
//        ViewNotes()
//    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}