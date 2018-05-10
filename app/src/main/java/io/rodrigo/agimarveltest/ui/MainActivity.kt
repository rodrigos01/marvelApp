package io.rodrigo.agimarveltest.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.rodrigo.agimarveltest.R
import io.rodrigo.agimarveltest.ui.characterslist.CharacterListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = CharacterListFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }
}
