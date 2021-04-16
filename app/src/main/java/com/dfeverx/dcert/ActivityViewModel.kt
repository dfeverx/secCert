package com.dfeverx.dcert


import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    val gSC: GoogleSignInClient,
     val fAuth: FirebaseAuth,
) :
    ViewModel() {

    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,        // The user has authenticated successfully

    }


    private val _authenticationState = MutableLiveData<AuthenticationState>()


    fun state() = liveData {
        emitSource(_authenticationState)
    }


    fun setState(state: AuthenticationState) {
        _authenticationState.postValue(state)
    }


    init {
        val currentUser = fAuth.currentUser
        if (currentUser == null) {
            _authenticationState.value = AuthenticationState.UNAUTHENTICATED
        } else {
            _authenticationState.value = AuthenticationState.AUTHENTICATED
        }

    }


    fun refuseAuthentication() {
        _authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }


    //    auth
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken


    var imgUri:Uri?=null
    var pdf:String=""

}