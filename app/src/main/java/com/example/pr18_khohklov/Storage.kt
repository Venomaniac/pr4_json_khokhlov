package com.example.pr18_khokhlov

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Storage {
    private const val PREFS_FILE = "pdd_prefs"
    private const val KEY_USER = "user"
    private const val KEY_TICKETS = "tickets"
    private val gson = Gson()

    private fun prefs(ctx: Context): SharedPreferences =
        ctx.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    fun saveUser(ctx: Context, user: User) {
        val json: String = gson.toJson(user)               // object -> JSON
        prefs(ctx).edit().putString(KEY_USER, json).apply()
    }

    fun getUser(ctx: Context): User? {
        val json = prefs(ctx).getString(KEY_USER, null) ?: return null
        return gson.fromJson(json, User::class.java)       // JSON -> object
    }

    fun saveTickets(ctx: Context, list: List<Ticket>) {
        val json: String = gson.toJson(list)               // список -> JSON-массив
        prefs(ctx).edit().putString(KEY_TICKETS, json).apply()
    }

    fun getTickets(ctx: Context): MutableList<Ticket> {
        val json = prefs(ctx).getString(KEY_TICKETS, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Ticket>>() {}.type
        return gson.fromJson(json, type)                   // JSON-массив -> список
    }

    fun addTicket(ctx: Context, ticket: Ticket) {
        val list = getTickets(ctx)
        list.add(ticket)
        saveTickets(ctx, list)
    }

    fun deleteTicket(ctx: Context, id: Long) {
        val list = getTickets(ctx)
        list.removeAll { it.id == id }
        saveTickets(ctx, list)
    }
}
