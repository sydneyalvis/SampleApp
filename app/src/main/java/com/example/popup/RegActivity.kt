package com.example.popup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.popup.databinding.ActivityRegBinding
import com.skydoves.bindables.BindingActivity

class RegActivity : BindingActivity<ActivityRegBinding>(R.layout.activity_reg){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_reg)

        binding.textViewSignUp.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))


        }

        binding.btnRegister.setOnClickListener {

           // val name = binding.inputUsername.text.toString()
           // Toast.makeText(this@RegActivity, "$name", Toast.LENGTH_SHORT).show()

            if (binding.inputUsername.text.isNullOrEmpty() ){

                Toast.makeText(this@RegActivity, "Input username", Toast.LENGTH_SHORT).show()


            }else if(binding.inputEmail.text.isNullOrEmpty()){

                Toast.makeText(this@RegActivity, "Input email", Toast.LENGTH_SHORT).show()


            }else {

                register()
            }


        }

    }


    private fun register(){

        val name = binding.inputUsername.text.toString()
        prefs.setUserName(binding.inputUsername.text.toString())
        prefs.setEmail(binding.inputEmail.text.toString())
        prefs.setPassword(binding.inputPassword.text.toString())

        Toast.makeText(this@RegActivity, "Registered successfully $name", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, LoginActivity::class.java))

    }


}