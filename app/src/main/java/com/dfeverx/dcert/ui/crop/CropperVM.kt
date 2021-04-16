package com.dfeverx.dcert.ui.crop


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CropperVM @Inject constructor(
    val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    fun uriString(): String? {
        return savedStateHandle.get<String>("uri_string")
    }

}