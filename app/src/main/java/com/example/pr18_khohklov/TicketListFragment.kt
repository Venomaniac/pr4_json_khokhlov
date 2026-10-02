package com.example.pr18_khokhlov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TicketListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvHello = view.findViewById<TextView>(R.id.tvHello)
        val tvEmpty = view.findViewById<TextView>(R.id.tvEmpty)
        val rv = view.findViewById<RecyclerView>(R.id.rvTickets)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        val user = Storage.getUser(requireContext())
        tvHello.text = "Пользователь: ${user?.login ?: "-"}"

        val tickets = Storage.getTickets(requireContext())    // JSON -> список
        tvEmpty.visibility = if (tickets.isEmpty()) View.VISIBLE else View.GONE

        lateinit var adapter: TicketAdapter
        adapter = TicketAdapter(tickets) { ticket ->
            Storage.deleteTicket(requireContext(), ticket.id)
            adapter.remove(ticket)
            tvEmpty.visibility = if (tickets.isEmpty()) View.VISIBLE else View.GONE
        }
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        btnAdd.setOnClickListener { (activity as MainActivity).show(AddTicketFragment()) }
        btnLogout.setOnClickListener { (activity as MainActivity).show(LoginFragment(), false) }
    }
}
