package com.NickCM.nickcm_criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.NickCM.nickcm_criminalintent.database.CrimeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "CrimeListViewModel"

//generating a dummy list of crimes
class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()

    //mutable state flow does it allows us to cache our crimes and only update when the FLOW changes
    private val _crimes : MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())

    val crimes: StateFlow<List<Crime>>
        get() = _crimes.asStateFlow()



    init {
        Log.d(TAG, "init starting")
        viewModelScope.launch {
            crimeRepository.getCrimes().collect {
                _crimes.value= it
            }
            Log.d(TAG, "coroutine launched")

        }


    }


    suspend fun addCrime(crime: Crime){
        crimeRepository.addCrime(crime)
    }



}