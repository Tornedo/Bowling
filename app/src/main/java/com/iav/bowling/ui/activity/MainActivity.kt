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

    var currentSelectedTabId: Int? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.current_game -> {
                if (currentSelectedTabId == R.id.current_game) return@OnNavigationItemSelectedListener true
                inflateFragment(OngoingGameFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.current_score -> {
                if (currentSelectedTabId == R.id.current_score) return@OnNavigationItemSelectedListener true
                inflateFragment(ScoreBoardFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun inflateFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        inflateFragment(OngoingGameFragment.newInstance())
        bottom_navigation.selectedItemId = R.id.current_game


    }
}
