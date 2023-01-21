package com.example.expensesplitter.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.expensesplitter.databinding.ActivitySignInBinding
import com.example.expensesplitter.models.user
import com.google.firebase.auth.FirebaseAuth

class SignIn : BaseActivity() {
    private var binding: ActivitySignInBinding?=null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        auth = FirebaseAuth.getInstance()

        binding?.btnSignin?.setOnClickListener {
            signInRegisteredUser()
        }
    }

        private fun signInRegisteredUser(){
            val email:String=binding!!.etEmailSignin!!.text!!.toString().trim{it <= ' '}
            val password:String=binding!!.etPasswordSignin!!.text!!.toString().trim{it <= ' '}

            if(validateForm(email, password)){
                showProgressDialog("please wait...")

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        hideProgressDialog()
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Log.w("Sign in", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        private fun validateForm(email:String,password:String):Boolean{
            return when{
                TextUtils.isEmpty(email)->{
                    showErrorSnackBar("please enter email")
                    false
                }
                TextUtils.isEmpty(password)->{
                    showErrorSnackBar("please enter password")
                    false
                }else ->{
                    true
                }
            }
        }

        fun signInSuccess(user: user){
            hideProgressDialog()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
}