package com.dfeverx.dcert.ui.layout.auth

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

}