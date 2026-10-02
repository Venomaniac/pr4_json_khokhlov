package com.example.pr18_khokhlov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            show(LoginFragment(), false)      // стартовый экран - авторизация
        }
    }

    fun show(fragment: Fragment, addToBackStack: Boolean = true) {
        val tr = supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
        if (addToBackStack) tr.addToBackStack(null)
        tr.commit()
    }
}
