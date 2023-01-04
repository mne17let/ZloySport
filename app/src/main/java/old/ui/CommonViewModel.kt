package old.ui

import old.data.Range

class CommonViewModel {
    val haveDrills: Boolean = false

    val rangeList = listOf(
        Range(1, true),
        Range(10, false),
        Range(200, false),
        Range(999, false),
        Range(5, false),
        Range(67, false),
        Range(67, false),
        Range(165, false),
        Range(28, true),
        Range(29, false),
        Range(162, true),
        Range(271, false),
        Range(63, true),
        Range(29, false)
    )
}