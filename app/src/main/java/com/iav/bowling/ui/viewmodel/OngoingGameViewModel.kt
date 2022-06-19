package com.iav.bowling.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.iav.bowling.model.ScoreBoard
import org.koin.core.KoinComponent

class OngoingGameViewModel : ViewModel() , KoinComponent {

    val scorecard = MutableLiveData<ScoreBoard>()
    var remainingPinsInFrame = MutableLiveData<Int>()
    private var ballRollsInFrame = 0
    var gameOver = MutableLiveData<Boolean>()

    val frames = switchMap(scorecard) { it.framesMutableLiveData }!!
    val frameInPlay = switchMap(scorecard) { it.inPlayLiveData }!!

    init {
        resetAll()
    }

    fun doKnockDownPins(numberOfPins: Int) {
        scorecard.value!!.knockedList.value!!.add(numberOfPins)

        ballRollsInFrame++
        remainingPinsInFrame.value = remainingPinsInFrame.value!! - numberOfPins

        if (scorecard.value!!.inPlayLiveData.value!! == 10 && remainingPinsInFrame.value == 0 && ballRollsInFrame < 3) {
            remainingPinsInFrame.value = 10
            return
        }
        if (remainingPinsInFrame.value == 0 || ballRollsInFrame == 2) {
            resetFrame()
        }
    }

    fun calculateScore() {
        scorecard.value!!.calculateAllScores()
    }

    private fun resetFrame() {
        val currentFrameInPlay = scorecard.value!!.inPlayLiveData.value!!
        if (currentFrameInPlay + 1 == 11) {
            gameOver.value = true
        }

        scorecard.value!!.inPlayLiveData.value = currentFrameInPlay + 1
        remainingPinsInFrame.value = 10
        ballRollsInFrame = 0
    }

    fun resetAll() {
        scorecard.value = ScoreBoard()
        remainingPinsInFrame.value = 10
        gameOver.value = false
        ballRollsInFrame = 0
    }
}
