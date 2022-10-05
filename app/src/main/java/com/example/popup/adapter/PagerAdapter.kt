package com.example.popup.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.popup.fragments.TransactionsFrag
import com.example.popup.fragments.TrendsFrag

class PagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                TransactionsFrag()
            }
            else -> {
                return TrendsFrag()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Transactions"
            else -> {
                return "Trends"
            }
        }
    }
}