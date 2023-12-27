package com.pieterbommele.dunkbuzz.ui.matchesScreen

import android.util.Log
import androidx.lifecycle.ViewModel

class MatchesViewModel : ViewModel() {
    init {
        Log.i("vm inspection", "AboutViewModel init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("vm inspection", "AboutViewModel cleared")
    }
}