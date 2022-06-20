package com.iav.bowling.model

import androidx.lifecycle.MutableLiveData
import com.iav.bowling.util.SIZE_OF_FRAMES

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
        if (latestScoredFrameIndex == SIZE_OF_FRAMES - 1) return

        var tempScore = 0
        var ballCount = 0
        var score = 0
        val listPinsKnocked = knockedList.value

        try {
            listPinsKnocked?.forEachIndexed { index, pins ->
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
                    framesMutableLiveData.value?.get(index) != null -> {
                        //Score for this frame has already been calculated
                        incrementFrameAndResetCounters()
                    }
                    pins == 10 -> {
                        /*
                                            update score when there is a strike
                                         */
                        score = listPinsKnocked?.let {
                            calculateStrikeScore(latestScoredFrameIndex,
                                it, index)
                        }!!
                        framesMutableLiveData.value?.set(latestScoredFrameIndex,
                            createFrameWithScore(tempScore, pins, score, Frame.Type.STRIKE)
                        )

                        incrementFrameAndResetCounters()
                    }
                    pins + tempScore == 10 -> {
                        /*
                                            update score when there is a spare
                                         */
                        score = listPinsKnocked?.let {
                            calculateSpareScore(latestScoredFrameIndex,
                                it, index)
                        }!!
                        framesMutableLiveData.value?.set(latestScoredFrameIndex,
                            createFrameWithScore(tempScore, pins, score, Frame.Type.SPARE)
                        )
                        incrementFrameAndResetCounters()
                    }
                    ballCount == 0 -> {
                        tempScore += pins
                        ballCount++
                    }
                    else -> {
                        score = calculateNormalScore(latestScoredFrameIndex, tempScore, pins)
                        framesMutableLiveData.value?.set(latestScoredFrameIndex,
                            createFrameWithScore(tempScore, pins, score, Frame.Type.NORMAL)
                        )
                        incrementFrameAndResetCounters()
                    }
                }
            }
        } catch (e: Exception) {
            // TODO: Handle the exception
        }
    }

    private fun createFrameWithScore(
        tempScore: Int,
        pins: Int,
        score: Int,
        type: Frame.Type
    ): Frame {
        return Frame(knockedList = listOf(tempScore, pins), score = score, type = type)
    }


    private fun calculateStrikeScore(
        latestScoredFrameIndex: Int,
        listPinsKnocked: MutableList<Int>,
        index: Int
    ): Int {
        return (framesMutableLiveData.value?.get(latestScoredFrameIndex - 1)?.score
            ?: 0) + SIZE_OF_FRAMES + listPinsKnocked[index + 1] + listPinsKnocked[index + 2]
    }

    private fun calculateSpareScore(
        latestScoredFrameIndex: Int,
        listPinsKnocked: MutableList<Int>,
        index: Int
    ): Int {
        return (framesMutableLiveData.value?.get(latestScoredFrameIndex - 1)?.score
            ?: 0) + SIZE_OF_FRAMES + listPinsKnocked[index + 1]
    }

    private fun calculateNormalScore(
        latestScoredFrameIndex: Int,
        tempScore: Int,
        pins: Int
    ): Int {
        return (framesMutableLiveData.value?.get(latestScoredFrameIndex - 1)?.score
            ?: 0) + tempScore + pins
    }
}
