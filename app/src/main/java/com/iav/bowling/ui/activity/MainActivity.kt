package com.iav.bowling.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.iav.bowling.ui.fragment.OngoingGameFragment
import com.iav.bowling.ui.fragment.ScoreBoardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iav.bowling.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private fun inflateFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.current_game -> {
                    inflateFragment(OngoingGameFragment.newInstance())
                }
                R.id.current_score-> {
                    inflateFragment(ScoreBoardFragment.newInstance())
                }
            }
            return@setOnItemSelectedListener true
        }
        inflateFragment(OngoingGameFragment.newInstance())
        bottom_navigation.selectedItemId = R.id.current_game


    }
}
