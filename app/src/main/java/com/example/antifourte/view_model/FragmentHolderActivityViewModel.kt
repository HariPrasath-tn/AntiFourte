package com.example.antifourte.view_model

import android.util.MutableDouble
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.antifourte.model.BruteForce

/*
 * view model class for FragmentHolder Activity
 * extends viewModel class
 */
class FragmentHolderActivityViewModel :ViewModel(){

    // user input mutable live data variable
    val targetNumber = MutableLiveData<String>()
    val optionNumber1 = MutableLiveData<String>()
    val optionNumber2 = MutableLiveData<String>()
    val optionNumber3 = MutableLiveData<String>()
    val optionNumber4 = MutableLiveData<String>()

    // final result mutable live data variable
    val resultString = MutableLiveData<String>()


    // assigning initial value for entry boxes
    init{
        targetNumber.value = ""
        optionNumber1.value = ""
        optionNumber2.value = ""
        optionNumber3.value = ""
        optionNumber4.value = ""
    }


    // method to initialize the brute force to find the solutions
    fun startBruteForce():Boolean{
        if(targetNumber.value == "" || optionNumber1.value == "" || optionNumber2.value == "" || optionNumber3.value == "" || optionNumber4.value == "")
            return false

        val num1:Int = optionNumber1.value.toString().toInt()
        val num2:Int = optionNumber2.value.toString().toInt()
        val num3:Int = optionNumber3.value.toString().toInt()
        val num4:Int = optionNumber4.value.toString().toInt()
        val targetNum:Int = targetNumber.value.toString().toInt()
        resultString.value = BruteForce.startBruteForceFor(arrayOf(num1, num2, num3, num4).toIntArray(), targetNum)
        return true
    }


    // method to set EntryBox Empty
    fun setEntryEmpty(){
        optionNumber1.value = ""
        optionNumber2.value = ""
        optionNumber3.value = ""
        optionNumber4.value = ""
        targetNumber.value = ""
        resultString.value = ""
    }

}