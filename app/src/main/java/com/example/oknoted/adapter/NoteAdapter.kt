package com.example.oknoted.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.oknoted.database.Note
import com.example.oknoted.databinding.ItemNoteBinding
import com.example.oknoted.helper.NoteDiffCallback

class NoteAdapter(private val listener: NoteItemClickListener): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val listNotes = ArrayList<Note>()

    interface NoteItemClickListener {
        fun onItemClick(note: Note)
        fun onUpdateClick(note: Note)
        fun onDeleteClick(note: Note)
    }

    class NoteViewHolder(private val binding : ItemNoteBinding,private val listener: NoteItemClickListener): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note){
            with(binding){
                tvItemDate.text = note.date
                tvItemTitle.text = note.title
                tvItemDescription.text = note.description

                binding.root.setOnClickListener {
                    listener.onItemClick(note)
                }

                ivUpdate.setOnClickListener {
                    listener.onUpdateClick(note)
                }

                ivDelete.setOnClickListener {
                    listener.onDeleteClick(note)
                }
            }
        }
    }

    fun setListNotes(listNotes: List<Note>){
        val diffCallback = NoteDiffCallback(this.listNotes,listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding,listener)
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }
}