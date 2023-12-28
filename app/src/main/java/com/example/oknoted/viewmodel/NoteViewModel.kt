package com.example.oknoted.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.oknoted.database.Note
import com.example.oknoted.repository.NoteRepository

class NoteViewModel(application: Application): ViewModel() {
    private val mNoteRepository : NoteRepository = NoteRepository(application)

    fun insert(note: Note){
        mNoteRepository.insert(note)
    }

    fun update(note: Note){
        mNoteRepository.update(note)
    }

    fun delete(note: Note){
        mNoteRepository.delete(note)
    }

    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNote()
}