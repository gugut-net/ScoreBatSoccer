package com.example.scorebatapp.ui.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.scorebatapp.R
import com.example.scorebatapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var transaction: FragmentTransaction


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()

        //Send the information from the fields
        binding.mbSignup.setOnClickListener{
            val email = binding.tileUser.text.toString()
            val password = binding.tilePassword.text.toString()
            val confirmPassword = binding.tileConfirmPassword.text.toString()

            //Valid that the fields are not empty
            if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){

                //Check that the email contains a valid mail
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    //Check that the password was created with the parameters requested
                    if (password.length < 8 || !password.matches(".*[A-Z].*".toRegex())
                        || !password.matches(".*[a-z].*".toRegex()) || !password.matches(".*[@#$%^&*=+].*".toRegex())){

                        //Check that the passwords match
                        if(password == confirmPassword){
                            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                                if (it.isSuccessful){
                                    transaction = parentFragmentManager.beginTransaction()
                                    transaction.replace(R.id.loginContainer, LoginFragment())
                                    transaction.addToBackStack(null)
                                    transaction.commit()
                                }else{
                                    Toast.makeText(context,
                                        "Password must contain least 8 characters: Uppercase,lowercase special characters",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }else{
                            Toast.makeText(context,"Passwords don't match..",Toast.LENGTH_SHORT).show()
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
                Toast.makeText(context,"Empty fields are not allowed",Toast.LENGTH_SHORT).show()
            }
        }

        //Send to the fragment login in case the user is already registered
        binding.tvAccount.setOnClickListener{
            transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.loginContainer, LoginFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return binding.root
    }

}