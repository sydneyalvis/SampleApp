package com.example.popup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.popup.databinding.ActivityRegBinding
import com.skydoves.bindables.BindingActivity
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


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


        sendServer(POST,"register")

    }

    private fun sendServer(type: String, method: String){

        val fullURL = url + "/" + method

        val client = OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).build()

        /* If it is a post request, then we have to pass the parameters inside the request body*/
      //  val request =  if (type.equals(POST)) {

            val formBody: RequestBody = FormBody.Builder()
                    .add("username", binding.inputUsername.text.toString())
                    .add("email", binding.inputEmail.text.toString())
                    .add("password", binding.inputPassword.text.toString())
                    .build()

           val request = Request.Builder()
                .url(fullURL)
                .post(formBody)
                .build()

//            } else {
//
//                /*If it's our get request, it doen't require parameters, hence just sending with the url*/
//                Request.Builder()
//                    .url(fullURL)
//                    .build()
//
//            }

        /* this is how the callback get handled */
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Read data on the worker thread
                    val responseData = response.body!!.string()

                    // Run view-related code back on the main thread.
                    // Here we display the response message in our text view
                    this@RegActivity.runOnUiThread(Runnable {
                        Toast.makeText(this@RegActivity, "Registered successfully $responseData", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegActivity, LoginActivity::class.java))

                    })
                }
            })


    }

}