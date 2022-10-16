package com.zloysport.data

data class Drill(
    val name: String = "",
    val rangeList: List<Range>,
    val relaxTime: Long,
    val imageId: Int
)

data class Range(
    val count: Int,
    val alreadyDone: Boolean
)