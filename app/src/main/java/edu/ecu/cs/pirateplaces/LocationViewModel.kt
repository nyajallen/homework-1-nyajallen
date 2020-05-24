package edu.ecu.cs.pirateplaces

import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {

    var currentIndex = 0
    var isCheckedIn = false

    private val locationBank = listOf(
        R.string.location_studentcenter,
        R.string.location_biltmore,
        R.string.location_hangingrock,
        R.string.location_wright
    )

    private val nameBank = listOf(
        R.string.studentcenter_names,
        R.string.biltmore_names,
        R.string.hangingrock_names,
        R.string.wright_names
    )

    val currentLocationText: Int
        get() = locationBank[currentIndex]

    val currentNameText: Int
        get() = nameBank[currentIndex]

    fun moveToNext() : Int{
        if (currentIndex != locationBank.size - 1) {
            currentIndex++
        }
        return currentIndex
    }

    fun moveToPrev() : Int{
        if (currentIndex != 0) {
            currentIndex--
        }
        return currentIndex
    }

    fun isFirstPlace(): Boolean {
        return currentIndex == 0
    }
    fun isLastPlace() : Boolean{
        return currentIndex == locationBank.size - 1
    }
}