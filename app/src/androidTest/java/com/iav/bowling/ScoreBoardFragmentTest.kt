package com.iav.bowling

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.iav.bowling.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test


class ScoreBoardFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val targetContext: Context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun check_ScoreBoard_Fragment_When_Application_Start() {
       onView(ViewMatchers.withId(R.id.current_score))
            .perform(ViewActions.click())

       onView(ViewMatchers.withId(R.id.fragmentScorecardTextView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun check_PlayAgain_Clicked() {
        onView(ViewMatchers.withId(R.id.current_score))
            .perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.fragmentScorecardResetButton))
            .perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.current_game))
            .perform(ViewActions.click())

        val currentFrame: String =  targetContext.resources.getString(R.string.frame_current)
        onView(ViewMatchers.withText("$currentFrame : 1")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}