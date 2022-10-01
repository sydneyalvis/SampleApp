package com.example.popup

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.popup.R
import android.widget.Toast
import com.example.popup.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.skydoves.bindables.BindingActivity
import kotlin.properties.Delegates

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    var dialog: Dialog? = null
    var walletMoney: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.login_activity)

        walletMoney = 3000

        //Set an initial amount of money in the wallet
        binding.txtMoney.text = walletMoney.toString()

        dialog = Dialog(this)

        binding.btnGrapes.setOnClickListener {
            ShowDialog(1)
        }
        binding.btnCarrots.setOnClickListener {
            ShowDialog(2)
        }
        binding.btnvan.setOnClickListener {
            ShowDialog(3)
        }
        binding.btnlamp.setOnClickListener {
            ShowDialog(4)
        }
        binding.btnAddMoney.setOnClickListener {

            addMoney(3)

        }

        binding.btnLogOut.setOnClickListener {

            prefs.setLogOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }
    }

    private fun ShowDialog(btnNumber: Int) {
        if (btnNumber == 1) {
            dialog!!.setContentView(R.layout.alert_dialog)
        } else {
            dialog!!.setContentView(R.layout.alert_dialog2)
        }
        if (btnNumber == 3){
            dialog!!.setContentView(R.layout.alert_dialog3)
        }else{
            dialog!!.setContentView(R.layout.alert_dialog4)
        }

        val txtPrice = dialog!!.findViewById<TextView>(R.id.txtPrice)

        val PurchaseButton = dialog!!.findViewById<Button>(R.id.PurchaseButton)
        val CancelButton = dialog!!.findViewById<Button>(R.id.CancelButton)

        PurchaseButton.setOnClickListener {
            if (walletMoney!! < txtPrice.text.toString().toInt()){

                Toast.makeText(this@MainActivity, "Insufficient funds", Toast.LENGTH_SHORT).show()

            } else {
                walletMoney = walletMoney?.minus(txtPrice.text.toString().toInt())
                binding.txtMoney.text = walletMoney.toString()
                if (btnNumber == 1) {
                    binding.btnGrapes.visibility = View.GONE
                } else {
                    binding.btnCarrots.visibility = View.GONE
                }
                if (btnNumber == 3) {
                    binding.btnvan.visibility = View.GONE
                }else {
                    binding.btnlamp.visibility = View.GONE
                }

                dialog!!.dismiss()
                Toast.makeText(this@MainActivity, "Successfully Purchased!", Toast.LENGTH_SHORT).show()
            }

        }

        CancelButton.setOnClickListener { dialog!!.dismiss() }
        dialog!!.create()
        dialog!!.show()
    }

    private fun addMoney(num: Int){
        dialog!!.setContentView(R.layout.add)

        val txtAddMoney = dialog!!.findViewById<TextInputEditText>(R.id.edtMoney)

        val addButton = dialog!!.findViewById<Button>(R.id.btnAddCash)

        addButton.setOnClickListener {
            if (txtAddMoney.text.toString().isEmpty()){
                Toast.makeText(this@MainActivity, "Enter the amount you wish to add", Toast.LENGTH_SHORT).show()

            }else {

                var new = txtAddMoney.text.toString()
                walletMoney = walletMoney?.plus(new.toInt())
                binding.txtMoney.text = walletMoney.toString()

                dialog!!.dismiss()
                Toast.makeText(this@MainActivity, "$walletMoney", Toast.LENGTH_SHORT).show()
            }

        }

        dialog!!.create()
        dialog!!.show()

    }
}