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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Google Client authentication
         */
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        /**
         * Initialize Firebase Auth
         */
        auth = FirebaseAuth.getInstance()


        /**
         * Configure Google sign-in client
         */
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)


        /**
         * Inflate the layout for this fragment
         */
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()

        /**
         * Sing In to the app
         */
        binding.mbLogin.setOnClickListener {
            val email = binding.tileUser.text.toString()
            val password = binding.tilePassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){

                //Check that the email contains a valid mail
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if (password.length < 8 || !password.matches(".*[A-Z].*".toRegex())
                        || !password.matches(".*[a-z].*".toRegex()) || !password.matches(".*[@#$%^&*=+].*".toRegex())){
                        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                            if (it.isSuccessful){
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(context,it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(context,
                            "Password must contain least 8 characters: Uppercase,lowercase special characters",
                            Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Please insert a valid email",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }

        //Send to sign up
        binding.mbSignup.setOnClickListener {
            transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.loginContainer, SignupFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return binding.root
    }

    /**
     * Google Client authentication
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configure sign-in button
        val signInButton = view.findViewById<TextView>(R.id.tv_signin_with_google)
        signInButton.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val intent = Intent(activity, HomeFragment::class.java)
                    startActivity(intent)
                    // ...
                } else {
                    // If sign in fails, display a message to the user.
                    // ...
                }
            }
    }


}



