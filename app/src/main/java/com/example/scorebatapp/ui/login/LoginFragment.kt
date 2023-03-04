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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var transaction: FragmentTransaction
    private lateinit var tv_signin_with_google: GoogleSignInOptions





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {





        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()

        //Sing In to the app
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


}

    /**
     * Google Client authentication
     */

