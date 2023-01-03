package com.zloysport.old.data

import com.zloysport.old.composables.common.CircleSliderRange
import com.zloysport.old.composables.common.LineSliderRange

data class Drill(
    val name: String = "",
    val rangeList: List<Range>,
    val relaxTime: Long,
    val imageId: Int
)

data class Range(
    val count: Int,
    val alreadyDone: Boolean
) {
    fun toCircleSliderRange(): CircleSliderRange = CircleSliderRange(count)
    fun toLineSliderRange(): LineSliderRange = LineSliderRange(count)
}