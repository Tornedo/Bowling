package com.iav.bowling

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.iav.bowling.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test


class OngoingGameFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val targetContext: Context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun check_Ongoing_Fragment_When_Application_Start() {
         onView(withId(R.id.fragmentCurrentPinsTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun check_Next_Frame_After_Two_Input_On_EditText() {
        val currentFrame: String =  targetContext.resources.getString(R.string.frame_current)

        onView(withId(R.id.fragmentCurrentFramePinsToKnockDownEditText)).perform(ViewActions.typeText("5"))
        onView(withId(R.id.fragmentCurrentPinsNextButton)).perform(click())

        onView(withId(R.id.fragmentCurrentFramePinsToKnockDownEditText)).perform(ViewActions.typeText("5"))
        onView(withId(R.id.fragmentCurrentPinsNextButton)).perform(click())
        Espresso.closeSoftKeyboard()
        onView(withText("$currentFrame : 2")).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun check_Errors_After_Invalid_Input() {
        val errorString: String =  targetContext.resources.getString(R.string.invalid_pin_input)
        onView(withId(R.id.fragmentCurrentFramePinsToKnockDownEditText)).perform(ViewActions.typeText("11"))
        onView(withId(R.id.fragmentCurrentPinsNextButton)).perform(click())
        Espresso.closeSoftKeyboard()
        onView(withText(errorString)).check(ViewAssertions.matches(isDisplayed()))
    }

}