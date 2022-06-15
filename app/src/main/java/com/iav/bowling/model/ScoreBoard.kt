package com.iav.bowling.model

import androidx.lifecycle.MutableLiveData
import com.iav.bowling.model.Frame.Type.SPARE
import com.iav.bowling.model.Frame.Type.STRIKE
import com.iav.bowling.util.FRAMES

/*
 This is the model class which calculate the score
 */
data class ScoreBoard(
    val knockedList: MutableLiveData<MutableList<Int>> = MutableLiveData(),
    val framesMutableLiveData: MutableLiveData<HashMap<Int, Frame>> = MutableLiveData(),
    var inPlayLiveData: MutableLiveData<Int> = MutableLiveData()
) {
    var latestScoredFrameIndex = 0

    init {
        knockedList.value = mutableListOf()
        framesMutableLiveData.value = HashMap()
        inPlayLiveData.value = 1
    }

    /*
       calculate the current score and updates previously scores which were not calculated
     */

    fun calculateAllScores() {
        if (latestScoredFrameIndex == FRAMES - 1) return

        var tempScore = 0
        var ballCount = 0

        val listPinsKnocked = knockedList.value!!

        try {
            listPinsKnocked.forEachIndexed { index, pins ->
                fun incrementFrameAndResetCounters() {
                    latestScoredFrameIndex++
                    tempScore = 0
                    ballCount = 0
                }
                /*
                    return when reached to last frame
                 */
                if (latestScoredFrameIndex > 9) return@forEachIndexed

                when {
                    framesMutableLiveData.value!![index] != null -> {
                        //Score for this frame has already been calculated
                        incrementFrameAndResetCounters()
                    }
                    pins == 10 -> {
                        /*
                            update score when there is a strike
                         */
                        val score =
                            (framesMutableLiveData.value!![latestScoredFrameIndex - 1]?.score
                                ?: 0) + 10 + listPinsKnocked[index + 1] + listPinsKnocked[index + 2]
                        framesMutableLiveData.value!![latestScoredFrameIndex] =
                            Frame(knockedList = listOf(pins), score = score, type = STRIKE)
                        incrementFrameAndResetCounters()
                    }
                    pins + tempScore == 10 -> {
                        /*
                            update score when there is a spare
                         */
                        val score =
                            (framesMutableLiveData.value!![latestScoredFrameIndex - 1]?.score
                                ?: 0) + 10 + listPinsKnocked[index + 1]
                        framesMutableLiveData.value!![latestScoredFrameIndex] = Frame(
                            knockedList = listOf(tempScore, pins),
                            score = score,
                            type = SPARE
                        )
                        incrementFrameAndResetCounters()
                    }
                    ballCount == 0 -> {
                        tempScore += pins
                        ballCount++
                    }
                    else -> {
                        val score =
                            (framesMutableLiveData.value!![latestScoredFrameIndex - 1]?.score
                                ?: 0) + tempScore + pins
                        framesMutableLiveData.value!![latestScoredFrameIndex] =
                            Frame(listOf(tempScore, pins), score)
                        incrementFrameAndResetCounters()
                    }
                }
            }
        } catch (e: Exception) {
            // TODO: Handle the exception
        }
    }
}
