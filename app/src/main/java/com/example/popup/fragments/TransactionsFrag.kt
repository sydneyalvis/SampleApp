package com.example.popup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popup.R
import com.example.popup.adapter.transactionAdapter
import com.example.popup.databinding.FragmentTransactionsBinding
import com.example.popup.model.items
import com.example.popup.prefs
import com.skydoves.bindables.BindingFragment

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionsFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionsFrag : BindingFragment<FragmentTransactionsBinding>(R.layout.fragment_transactions) {


    lateinit var itemsRVAdapter: transactionAdapter
    lateinit var itemsList: ArrayList<items>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState) // we should call `super.onCreateView`.
        return binding {


        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            itemsList = ArrayList()
            itemsRVAdapter = transactionAdapter(itemsList)

            itemsList.addAll(prefs.getArrayList())

            itemsRVAdapter.notifyDataSetChanged()

            // on below line we are setting adapter to our recycler view.
            binding.recyclerView.adapter = itemsRVAdapter

        } catch (e: NullPointerException){

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = TransactionsFrag()
    }
}