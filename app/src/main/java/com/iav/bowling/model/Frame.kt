package com.iav.bowling.model

import com.iav.bowling.model.Frame.Type.NORMAL
/*
* this data class is used for  keep state of each frame
 */
data class Frame(
    val knockedList: List<Int> = mutableListOf(),
    var score: Int = 0,
    val type: Type = NORMAL
) {
    enum class Type {
        STRIKE, SPARE, NORMAL
    }
}
