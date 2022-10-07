package com.example.popup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.popup.databinding.ActivityLoginBinding
import com.example.popup.model.Person
import com.example.popup.utils.ApiInterface
import com.skydoves.bindables.BindingActivity

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val url = "http://127.0.0.1:5000"
    private val POST = "POST"
    private val GET = "GET"

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
            postServer()

            prefs.setLogin()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()

        }

    }

    private fun postServer() {

        val name = binding.inputUsername.text.toString()

        val p = Person(
            binding.inputUsername.text.toString(),
            "",
            binding.inputPassword.text.toString())

        val apiInterface = ApiInterface.create().login(p)

        apiInterface.enqueue( object : retrofit2.Callback<Person> {
            override fun onResponse(call: retrofit2.Call<Person>?, response: retrofit2.Response<Person>?) {

                if (response != null) {
                    if (response.isSuccessful){
                        Toast.makeText(this@LoginActivity, "Welcome $name", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()

                    }else {
                        Toast.makeText(this@LoginActivity, "Unsuccessful login", Toast.LENGTH_SHORT).show()


                    }
                }
                //if(response?.body() != null)
                //recyclerAdapter.setMovieListItems(response.body()!!)
            }

            override fun onFailure(call: retrofit2.Call<Person>?, t: Throwable?) {
                if (t != null) {
                    Toast.makeText(this@LoginActivity, "A problem ${t.message}", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }

}