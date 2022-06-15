package com.iav.bowling

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.iav.bowling.ui.viewmodel.OngoingGameViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class OngoingViewModelTest {
    private lateinit var viewModel: OngoingGameViewModel

    @get:Rule
    var rule: TestRule = androidx.arch.core.executor.testing.InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = OngoingGameViewModel()
    }

    @Test
    fun resetAllTest() {
        viewModel.resetAll()

        assertEquals(1, viewModel.scorecard.value!!.inPlayLiveData.value)
        assertEquals(0, viewModel.scorecard.value!!.framesMutableLiveData.value!!.size)
        assertEquals(10, viewModel.remainingPinsInFrame.value)
        assertEquals(false, viewModel.gameOver.value)
    }

    @Test
    fun pinsKnockDownTest() {
        viewModel.resetAll()

        viewModel.doKnockDownPins(2)

        assertEquals(1, viewModel.scorecard.value!!.knockedList.value!!.size)
        assertEquals(1, viewModel.scorecard.value!!.inPlayLiveData.value)
    }

}