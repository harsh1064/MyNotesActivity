package com.example.mynotesactivity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.inflate
import android.view.inputmethod.InputBinding
import android.widget.*
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat.inflate
import com.example.mynotesactivity.DBHandler.DatabaseHandler
import com.example.mynotesactivity.Model.Notes

class NotesActivity : AppCompatActivity() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var edtTitle: EditText
    lateinit var edtNote: EditText
    lateinit var addnote: Button
    lateinit var p1:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        toolbar = findViewById(R.id.tool)
        edtTitle = findViewById(R.id.edtTitle)
        edtNote = findViewById(R.id.edtNote)
        addnote = findViewById(R.id.addNote)
        p1 = findViewById(R.id.P1)

        setSupportActionBar(toolbar)


        toolbar?.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_back)

        toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        addnote.setOnClickListener {
            addnotes()
            setupProgressBar()
        }
    }

    private fun setupProgressBar() {
        val visibility = if (p1.visibility == View.GONE) View.VISIBLE else View.GONE
        p1.visibility = visibility
    }

    private fun addnotes() {
        var title = edtTitle.text.toString()
        var text = edtNote.text.toString()

        if (title.isEmpty() && text.isEmpty()){
            edtTitle.error = "Title is Required"
            edtNote.error = "Note is Required"
        }else{
            val dbHandler = DatabaseHandler(this)
            val rows = dbHandler.addNotes(Notes(null,title,text))

            if (rows > -1){
                var intent: Intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this,"Notes Added Successfully..", Toast.LENGTH_LONG).show()
                Log.d("NotesActivity","data inserted with rows: $rows")
            }else{
                Toast.makeText(this,"Notes Note Added..", Toast.LENGTH_LONG).show()
            }
        }
    }
}