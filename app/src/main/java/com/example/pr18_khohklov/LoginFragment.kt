package com.example.pr18_khokhlov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etLogin = view.findViewById<EditText>(R.id.etLogin)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        Storage.getUser(requireContext())?.let { etLogin.setText(it.login) }

        btnLogin.setOnClickListener {
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()

            if (login.isEmpty()) {
                etLogin.error = "Введите логин"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Введите пароль"
                return@setOnClickListener
            }

            Storage.saveUser(requireContext(), User(login, password))
            Toast.makeText(requireContext(), "Добро пожаловать, $login!", Toast.LENGTH_SHORT).show()
            (activity as MainActivity).show(TicketListFragment(), false)
        }
    }
}
