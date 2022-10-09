package com.example.popup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.popup.databinding.ActivityRegBinding
import com.example.popup.model.Person
import com.example.popup.utils.ApiInterface
import com.skydoves.bindables.BindingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegActivity : BindingActivity<ActivityRegBinding>(R.layout.activity_reg){

    private val url = "http://127.0.0.1:5000"
    private val POST = "POST"
    private val GET = "GET"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_reg)


        binding.textViewSignUp.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))

        }

        binding.btnRegister.setOnClickListener {

            val name = binding.inputUsername.text.toString()

            if (binding.inputUsername.text.isNullOrEmpty() ){

                Toast.makeText(this@RegActivity, "Input username", Toast.LENGTH_SHORT).show()

            }else if(binding.inputEmail.text.isNullOrEmpty()){

                Toast.makeText(this@RegActivity, "Input email", Toast.LENGTH_SHORT).show()

            }else {
                prefs.setUserName(binding.inputUsername.text.toString())
                prefs.setEmail(binding.inputEmail.text.toString())
                prefs.setPassword(binding.inputPassword.text.toString())

                //UNCOMMENT TO SEND CREDENTIALS TO SERVER
                //postServer()

                Toast.makeText(this@RegActivity, "Welcome $name", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegActivity, LoginActivity::class.java))
            }


        }

    }

    private fun postServer() {

        val name = binding.inputUsername.text.toString()

        val p = Person(
            binding.inputUsername.text.toString(),
            binding.inputEmail.text.toString(),
            binding.inputPassword.text.toString())

        val apiInterface = ApiInterface.create().register(p)

        apiInterface.enqueue( object : Callback<Person> {
            override fun onResponse(call: Call<Person>?, response: Response<Person>?) {

                if (response != null) {
                    if (response.isSuccessful){
                        Toast.makeText(this@RegActivity, "Welcome $name", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegActivity, LoginActivity::class.java))

                    }else {
                        Toast.makeText(this@RegActivity, "Registration unsuccessful", Toast.LENGTH_SHORT).show()


                    }
                }
                //if(response?.body() != null)
                    //recyclerAdapter.setMovieListItems(response.body()!!)
            }

            override fun onFailure(call: Call<Person>?, t: Throwable?) {
                if (t != null) {
                    Toast.makeText(this@RegActivity, "A problem ${t.message}", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }

}

