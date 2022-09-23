package com.example.popup

import android.app.Dialog
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
        setContentView(R.layout.activity_main)

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

        binding.btnAddMoney.setOnClickListener {

            addMoney(3)

        }
    }

    private fun ShowDialog(btnNumber: Int) {
        if (btnNumber == 1) {
            dialog!!.setContentView(R.layout.alert_dialog)
        } else {
            dialog!!.setContentView(R.layout.alert_dialog2)
        }

        val txtPrice = dialog!!.findViewById<TextView>(R.id.txtPrice)

        val PurchaseButton = dialog!!.findViewById<Button>(R.id.PurchaseButton)
        val CancelButton = dialog!!.findViewById<Button>(R.id.CancelButton)
        PurchaseButton.setOnClickListener {
            if (walletMoney!! < txtPrice.text.toString().toInt()){

                Toast.makeText(this@MainActivity, "Insufficient funds", Toast.LENGTH_SHORT).show()

            } else {
                walletMoney = walletMoney?.minus(txtPrice.text.toString().toInt())
                if (btnNumber == 1) {
                    binding.btnGrapes.visibility = View.GONE
                } else {
                    binding.btnCarrots.visibility = View.GONE
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

                walletMoney = walletMoney?.plus(txtAddMoney.text.toString().toInt())

                dialog!!.dismiss()
                Toast.makeText(this@MainActivity, "Money added to wallet successfully", Toast.LENGTH_SHORT).show()
            }

        }

        dialog!!.create()
        dialog!!.show()

    }
}