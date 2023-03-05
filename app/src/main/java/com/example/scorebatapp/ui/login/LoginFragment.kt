package com.example.scorebatapp.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.scorebatapp.MainActivity
import com.example.scorebatapp.R
import com.example.scorebatapp.databinding.FragmentLoginBinding
import com.example.scorebatapp.ui.home.HomeFragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var transaction: FragmentTransaction

    /**
     * Google Client authentication
     */
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001


    private lateinit var mAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager


    companion object {
        private const val TAG = "LoginFragment"
        private const val RC_SIGN_IN = 9001
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




//        /**
//         * Google Client authentication
//         */
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
        /**
         * Initialize Firebase Auth
         */
        auth = FirebaseAuth.getInstance()

        // Initialize Facebook Login button
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id))
        FacebookSdk.sdkInitialize(requireContext())
        callbackManager = CallbackManager.Factory.create()
//
//
//        /**
//         * Configure Google sign-in client
//         */
//        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)


        /**
         * Inflate the layout for this fragment
         */
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        /**
         * Sing In to the app
         */
        binding.mbLogin.setOnClickListener {
            val email = binding.tileUser.text.toString()
            val password = binding.tilePassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {

                //Check that the email contains a valid mail
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (password.length < 8 || !password.matches(".*[A-Z].*".toRegex())
                        || !password.matches(".*[a-z].*".toRegex()) || !password.matches(".*[@#$%^&*=+].*".toRegex())
                    ) {
                        firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val intent = Intent(activity, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        context,
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            context,
                            "Password must contain least 8 characters: Uppercase,lowercase special characters",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(context, "Please insert a valid email", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }

        //Send to sign up
        binding.mbSignup.setOnClickListener {
            transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.loginContainer, SignupFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

//        // Check if user is already signed in
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            // User is already signed in, go to HomeFragment
//            findNavController().navigate(R.id.action_navigation_signin_to_navigation_home)
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mAuth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()

        binding.tvSigninWithGoogle.setOnClickListener {
            signInWithGoogle()
        }

        binding.tvSigninWithFacebook.setOnClickListener {
            signInWithFacebook()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = GoogleSignIn.getClient(
            requireActivity(),
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
            .signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                updateUI(null)
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                updateUI(null)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        } else {
            // User is signed out
            Log.d(TAG, "User is signed out")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(
                    requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
                updateUI(null)
            }
        } else {
// Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

//
//    /**
//     * Google Client authentication
//     */
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Configure sign-in button
//        val signInButton = view.findViewById<TextView>(R.id.tv_signin_with_google)
//        signInButton.setOnClickListener {
//            signInWithGoogle()
//        }
//    }
//
//    private fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)!!
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                // ...
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(requireActivity()) {
//                if (it.isSuccessful) {
//                    val intent = Intent(requireContext(), HomeFragment::class.java)
//                    startActivity(intent)
//
////                    val intent = Intent(activity, HomeFragment::class.java)
////                    startActivity(intent)
//
//                    // Sign in success, update UI with the signed-in user's information
//                    val user = auth.currentUser
//
//                    //findNavController().navigate(R.id.action_navigation_signin_to_navigation_home)
//                    // ...
//                } else {
//                    // If sign in fails, display a message to the user.
//                    // ...
//                }
//            }
//    }



