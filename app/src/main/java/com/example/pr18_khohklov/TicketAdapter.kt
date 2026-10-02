package com.example.pr18_khokhlov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TicketAdapter(
    private val items: MutableList<Ticket>,
    private val onDelete: (Ticket) -> Unit
) : RecyclerView.Adapter<TicketAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val tvQuestion: TextView = v.findViewById(R.id.tvQuestion)
        val tvAnswers: TextView = v.findViewById(R.id.tvAnswers)
        val tvStatus: TextView = v.findViewById(R.id.tvStatus)
        val btnDelete: ImageButton = v.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return VH(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val t = items[position]
        h.tvTitle.text = "Билет № ${t.number}"
        h.tvQuestion.text = t.question
        h.tvAnswers.text = t.answers.mapIndexed { i, a -> "${i + 1}. $a" }.joinToString("\n")
        h.tvStatus.text = (if (t.isPassed) "Решён" else "Не решён") +
                "  |  Правильный ответ: ${t.correctAnswer}"
        h.btnDelete.setOnClickListener { onDelete(t) }
    }

    fun remove(t: Ticket) {
        val index = items.indexOf(t)
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
