package com.example.popup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.popup.databinding.ActivityLoginBinding
import com.skydoves.bindables.BindingActivity

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        if (prefs.isLoggedIn()){

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }


        binding.btnlogin.setOnClickListener {

            if (binding.inputUsername.text.isNullOrEmpty() ){

                Toast.makeText(this@LoginActivity, "Input username", Toast.LENGTH_SHORT).show()

            }else if(binding.inputPassword.text.isNullOrEmpty()){

                Toast.makeText(this@LoginActivity, "Input password", Toast.LENGTH_SHORT).show()

            }else {

                login()
            }

        }

        binding.textViewSignUp.setOnClickListener {

            startActivity(Intent(this, RegActivity::class.java))


        }

    }

    private fun login(){

        if (binding.inputUsername.text.toString() != prefs.getString("username")){

            Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()

        }else if(binding.inputPassword.text.toString() != prefs.getString("password")){

            Toast.makeText(this@LoginActivity, "Password incorrect", Toast.LENGTH_SHORT).show()

        }else {

            prefs.setLogin()
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }

    }


}