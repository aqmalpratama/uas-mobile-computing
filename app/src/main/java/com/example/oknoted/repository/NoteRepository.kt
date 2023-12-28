package com.example.oknoted.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.oknoted.database.Note
import com.example.oknoted.database.NoteDao
import com.example.oknoted.database.NoteRoomDatabase
import java.lang.Appendable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private val mNotesDao : NoteDao
    private val executorService : ExecutorService = Executors.newSingleThreadScheduledExecutor()

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun insert(note: Note){
        executorService.execute { mNotesDao.insert(note) }
    }

    fun update(note: Note){
        executorService.execute { mNotesDao.update(note) }
    }

    fun delete(note: Note){
        executorService.execute { mNotesDao.delete(note) }
    }

    fun getAllNote():LiveData<List<Note>> = mNotesDao.getAllNotes()

}