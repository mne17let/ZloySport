package com.zloysport.data

import com.zloysport.ui.composables.common.CircleSliderRange

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
}