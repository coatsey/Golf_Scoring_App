package com.example.golf_scoring.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.Toast
import com.example.golf_scoring.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        setupActionBar()

        window.setFlags(
            FLAG_FULLSCREEN,
            FLAG_FULLSCREEN,
        )
        btn_sign_in.setOnClickListener {
            signInRegisteredUser()
        }

    }
    private fun setupActionBar(){
        setSupportActionBar(toolbar_sign_in_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        toolbar_sign_in_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun signInRegisteredUser(){
        val email: String = et_email_sign_in.text.toString().trim { it <= ' '}
        val password: String = et_password_sign_in.text.toString().trim { it <= ' '}

        if (validateForm(email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {

                        Toast.makeText(
                            this@SignInActivity,
                            "You have successfully signed in.",
                            Toast.LENGTH_LONG
                        ).show()

                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

        private fun validateForm(email: String, password: String) : Boolean {
            return when {
                TextUtils.isEmpty(email)->{
                    showErrorSnackBar("Please enter a email")
                    false
                }
                TextUtils.isEmpty(password)->{
                    showErrorSnackBar("Please enter a password")
                    false
                }else -> {
                    true
                }
            }
        }

}