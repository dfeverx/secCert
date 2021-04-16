package com.dfeverx.dcert

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dfeverx.dcert.databinding.FragmentAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFrag : Fragment() {
    private val activityViewModel: ActivityViewModel by activityViewModels()

    companion object {


        var bool = true
    }

    private val TAG = "AuthFrag"
    val RC_SIGN_IN = 9001
    lateinit var binding: FragmentAuthBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.progressTintList = ColorStateList.valueOf(Color.RED)

        binding.gbtnSignin.setOnClickListener {
            signIn()
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activityViewModel.state().observe(viewLifecycleOwner, {
            when (it) {
                ActivityViewModel.AuthenticationState.AUTHENTICATED -> {
                    findNavController().navigate(AuthFragDirections.actionAuthFragToNavigation())
                }
                else -> {
                    Log.d(TAG, "onActivityCreated: User not authenticated")
                }
            }
        })


        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = activityViewModel.fAuth.currentUser

        Log.d("MainCheckFrag", "current user $currentUser")
        updateUI(currentUser)
    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = activityViewModel.fAuth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    // [START onactivityresult]

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
        progressDialog(true)
        // [END_EXCLUDE]

        Log.d("MainCheckFrag", "test")

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        activityViewModel.fAuth.signInWithCredential(credential).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = activityViewModel.fAuth.currentUser
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                signOut()
                updateUI(null)
            }

            // [START_EXCLUDE]
//                hideProgressDialog()
            // [END_EXCLUDE]

        }

    }
    // [END auth_with_google]

    // [START signin]
    private fun signIn() {
        val signInIntent = activityViewModel.gSC.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    private fun signOut() {
        // Firebase sign out
        activityViewModel.fAuth.signOut()
        activityViewModel.gSC.signOut()
        // Google sign out
        activity?.parent?.let {
            Log.d("MainCheckFrag", "signout")
            activityViewModel.gSC.signOut().addOnCompleteListener(it) {
                updateUI(null)
            }
        }
    }

    private fun revokeAccess() {
        // Firebase sign out
        activityViewModel.fAuth.signOut()

        // Google revoke access
        activityViewModel.gSC.revokeAccess().addOnCompleteListener(activity?.parent!!) {
            updateUI(null)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        progressDialog(false)
        if (user != null) {
            activityViewModel.setState(ActivityViewModel.AuthenticationState.AUTHENTICATED)
//            findNavController().navigate(R.id.getMyExamFrag)
//            findNavController().navigate(SelectExamsFFragDirections.actionFinshauthToDashboard())
            /* status.text = getString(R.string.google_status_fmt, user.email)
             detail.text = getString(R.string.firebase_status_fmt, user.uid)*/

            Log.d("MainCheckFrag", "id is = ${user.displayName}")

//            binding.gbtnSignin.visibility = View.INVISIBLE
//            binding.progressBar.visibility = View.VISIBLE
        } else {
//            status.setText(R.string.signed_out)
//            detail.text = null
            Log.d("MainCheckFrag", "Not signed in ")
//            binding.gbtnSignin.visibility = View.VISIBLE
//            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun progressDialog(show: Boolean) {

//hide sign in btn and show loading
        if (show) {
            binding.gbtnSignin.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE

        } else {
            binding.gbtnSignin.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE


        }

//show sign in btn and show loading

    }


}