package com.example.oknoted

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oknoted.adapter.NoteAdapter
import com.example.oknoted.database.Note
import com.example.oknoted.databinding.ActivityMainBinding
import com.example.oknoted.view.DetailNoteActivity
import com.example.oknoted.view.ManageNote
import com.example.oknoted.viewmodel.NoteViewModel
import com.example.oknoted.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val manageNote = ManageNote()
    private lateinit var adapter: NoteAdapter
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = "Ok Noted"
        val noteViewModel = obtainViewModel(this@MainActivity)
        initListener()

        noteViewModel.getAllNotes().observe(this){
            if(it != null){
                adapter.setListNotes(it)
            }
        }

        adapter = NoteAdapter((object: NoteAdapter.NoteItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity,DetailNoteActivity::class.java)
                var bundle = Bundle()
                bundle.putParcelable("notedata",note)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onUpdateClick(note: Note) {
                isEdit = true
                manageNote.setNote(note)
                manageNote.show(supportFragmentManager,"ManageNoteFragmentTag")
            }

            override fun onDeleteClick(note: Note) {
                noteViewModel.delete(note)
            }
        }))

        binding.rvNote.layoutManager = LinearLayoutManager(this)
        binding.rvNote.setHasFixedSize(true)
        binding.rvNote.adapter = adapter

        manageNote.setOnItemClickListener(object :ManageNote.OnItemClickListener{
            override fun onItemClick(note: Note) {
                if(isEdit){
                    noteViewModel.update(note)
                } else {
                    noteViewModel.insert(note)
                }
            }
        })
    }

    private fun initListener() {
        binding.fabInsert.setOnClickListener {
            isEdit = false
            manageNote.setNote(null)
            manageNote.show(supportFragmentManager,"ManageNoteFragmentTag")
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(NoteViewModel::class.java)
    }

}