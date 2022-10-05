package com.example.popup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popup.adapter.PagerAdapter
import com.example.popup.adapter.itemsAdapter
import com.example.popup.adapter.transactionAdapter
import com.example.popup.databinding.ActivityMainBinding
import com.example.popup.databinding.ActivityTransactionsBinding
import com.example.popup.model.items
import com.skydoves.bindables.BindingActivity

class TransactionsActivity : BindingActivity<ActivityTransactionsBinding>(R.layout.activity_transactions) {

    var walletMoney: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_transactions)

        walletMoney = if (prefs.getWallet() == null ) {
            0
        } else {
            prefs.getWallet()

        }

        //Set an initial amount of money in the wallet
        binding.txtMoney.text = walletMoney.toString()

        val fragmentAdapter = PagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = fragmentAdapter
        binding.tabsMain.setupWithViewPager(binding.viewPager)

    }


}