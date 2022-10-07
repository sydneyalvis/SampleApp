package com.example.popup

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.popup.R
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.popup.adapter.itemsAdapter
import com.example.popup.databinding.ActivityMainBinding
import com.example.popup.model.Person
import com.example.popup.model.items
import com.example.popup.utils.ApiInterface
import com.example.popup.utils.itemsClickListener
import com.google.android.material.textfield.TextInputEditText
import com.skydoves.bindables.BindingActivity
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main), itemsClickListener {

   // lateinit var itemsRV: RecyclerView
    lateinit var itemsRVAdapter: itemsAdapter
    lateinit var itemsList: ArrayList<items>
    lateinit var newItemsList: ArrayList<items>


    var dialog: Dialog? = null
    var walletMoney: Int? = null

    private val url = "http://127.0.0.1:5000"
    private val POST = "POST"
    private val GET = "GET"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.login_activity)

        walletMoney = if (prefs.getWallet() == null ) {
            0
        } else {
            prefs.getWallet()

        }

        //Set an initial amount of money in the wallet
        binding.txtMoney.text = walletMoney.toString()

        dialog = Dialog(this)

        binding.btnAddMoney.setOnClickListener {

            addMoney(3)

        }

        // on below line we are initializing our list
        itemsList = ArrayList()

        // on below line we are initializing our adapter
        itemsRVAdapter = itemsAdapter(itemsList, this)

        // on below line we are setting adapter to our recycler view.
        binding.recyclerView.adapter = itemsRVAdapter

        // on below line we are adding data to our list
        itemsList.add(items("GRAPES", R.drawable.grapes, "Grapes contain powerful antioxidants known as polyphenols. These are thought to have anti-inflammatory and antioxidant properties. ",  "100", 3,System.currentTimeMillis()))
        itemsList.add(items("CARROT", R.drawable.carrot, "Carrots are rich in vitamins, minerals, and fiber. They are also a good source of antioxidants. Antioxidants are nutrients present in plant-based foods. " ,  "150",10, System.currentTimeMillis()))
        itemsList.add(items("LAMP", R.drawable.lamp, "A van is a type of road vehicle used for transporting goods or people. Depending on the type of van, it can be bigger or smaller than a pickup truck and SUV, and bigger than a common car. There is some varying in the scope of the word across the different  ", "200", 5,System.currentTimeMillis()))
        itemsList.add(items("VAN", R.drawable.van, "An electric light, lamp, or colloquially called light bulb is an electrical device that produces light. It is the most common form of artificial lighting. Lamps usually have a base made of ceramic, metal, glass, or plastic, which secures the lamp in the socket of a light fixture. " , "1500", 6,System.currentTimeMillis()))

        itemsRVAdapter.notifyDataSetChanged()

        keyWordFilter()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })

        binding.btnLogOut.setOnClickListener {

            prefs.setLogOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }

        binding.relaFilter.setOnClickListener {
            sort()

        }

        binding.btnTransactions.setOnClickListener {

            startActivity(Intent(this, TransactionsActivity::class.java))

        }

    }

    private fun sort(){
        dialog!!.setContentView(R.layout.sort_alert_dialog)

        val sortButton = dialog!!.findViewById<Button>(R.id.sortButton)
        val CancelButton = dialog!!.findViewById<Button>(R.id.CancelButton)

        val sortName = dialog!!.findViewById<CheckBox>(R.id.sortName)
        val sortPrice = dialog!!.findViewById<CheckBox>(R.id.sortPrice)
        val sortLatest = dialog!!.findViewById<CheckBox>(R.id.sortLatest)

        sortButton.setOnClickListener {
            sortName.setOnCheckedChangeListener { compoundButton, b ->

                itemsList.sortWith(
                    compareBy(String.CASE_INSENSITIVE_ORDER, { it.name })
                )

            }

            sortPrice.setOnCheckedChangeListener { compoundButton, b ->

                itemsList.sortByDescending { it.price }

            }

            sortLatest.setOnCheckedChangeListener { compoundButton, b ->
                itemsList.sortByDescending { it.createdAt }

            }

            dialog!!.dismiss()

        }

        CancelButton.setOnClickListener { dialog!!.dismiss() }
        dialog!!.create()
        dialog!!.show()

    }

    private fun filter(text: String) {

        val filteredlist: ArrayList<items> = ArrayList()

        for (item in itemsList) {

            if (item.name.toLowerCase().contains(text.toLowerCase()) || item.description.toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item)

            }
        }
        if (filteredlist.isEmpty()) {

            Toast.makeText(this, "No items Found..", Toast.LENGTH_SHORT).show()

        } else {

            itemsRVAdapter.filterList(filteredlist)

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
                prefs.setWalletMoney(walletMoney!!.toInt())
                binding.txtMoney.text = walletMoney.toString()

                dialog!!.dismiss()
                Toast.makeText(this@MainActivity, "$walletMoney", Toast.LENGTH_SHORT).show()
            }

        }

        dialog!!.create()
        dialog!!.show()

    }

    override fun onItemClickLister(data: items) {
        showDialogAlert(data)
    }

    private fun showDialogAlert(data: items) {

        val filteredlist: ArrayList<items> = ArrayList()

        dialog!!.setContentView(R.layout.alert_dialog)

        val txtName = dialog!!.findViewById<TextView>(R.id.txtName)
        val itemImage = dialog!!.findViewById<ImageView>(R.id.image)
        val txtDescription = dialog!!.findViewById<TextView>(R.id.txtDescription)
        val txtPrice = dialog!!.findViewById<TextView>(R.id.txtPrice)

        val btnAdd = dialog!!.findViewById<ImageView>(R.id.add)
        val txtNumItems = dialog!!.findViewById<TextView>(R.id.itemNum)
        val btnRemove = dialog!!.findViewById<ImageView>(R.id.remove)

        val lnCounter = dialog!!.findViewById<LinearLayout>(R.id.lnCounter)

        var num = 0
        txtName.text = data.name
        txtDescription.text = data.description 
        txtPrice.text = data.price
        itemImage.setImageResource(data.image)

        val PurchaseButton = dialog!!.findViewById<Button>(R.id.PurchaseButton)
        val CancelButton = dialog!!.findViewById<Button>(R.id.CancelButton)

        btnAdd.setOnClickListener {
            if (num > data.itemCount){
                btnAdd.visibility = View.INVISIBLE
            }else {
                num += 1
                txtNumItems.text = num.toString()

            }

        }

        btnRemove.setOnClickListener {
            if (num > 0 && num != 0){
                num -= 1
                txtNumItems.text = num.toString()
            }
        }

        PurchaseButton.setOnClickListener {
            
            if (walletMoney!! < txtPrice.text.toString().toInt()){

                Toast.makeText(this@MainActivity, "Insufficient funds", Toast.LENGTH_SHORT).show()

            } else {
                walletMoney = walletMoney?.minus(txtPrice.text.toString().toInt())
                binding.txtMoney.text = walletMoney.toString()

                prefs.setWalletMoney(walletMoney!!.toInt())
                data.itemCount = data.itemCount - num

                try {

                    val purchasedItems = prefs.getArrayList()
                    purchasedItems.add(data)
                    prefs.saveArrayList(purchasedItems)

                } catch (e: NullPointerException){

                    val purchasedItems: ArrayList<items> = ArrayList()
                    purchasedItems.add(data)
                    prefs.saveArrayList(purchasedItems)

                }

                if (data.itemCount == 0){

                    itemsList.addAll(itemsList.filter { items -> items.name != data.name })
                    itemsRVAdapter.filterList(itemsList)
                }

                purchasedItemToServer(data)

                dialog!!.dismiss()
            }

        }

        CancelButton.setOnClickListener { dialog!!.dismiss() }
        dialog!!.create()
        dialog!!.show()
    }

    private fun keyWordFilter(){

        val filteredlist: ArrayList<items> = ArrayList()

        binding.keyGrapes.setOnClickListener {

            filteredlist.addAll(itemsList.filter { items -> items.name == "GRAPES" })
            itemsRVAdapter.filterList(filteredlist)

        }

        binding.keyCarrot.setOnClickListener {

            filteredlist.addAll(itemsList.filter { items -> items.name == "CARROT" })
            itemsRVAdapter.filterList(filteredlist)

        }

        binding.keyLamp.setOnClickListener {

            filteredlist.addAll(itemsList.filter { items -> items.name == "LAMP" })
            itemsRVAdapter.filterList(filteredlist)

        }

        binding.keyVan.setOnClickListener {

            filteredlist.addAll(itemsList.filter { items -> items.name == "VAN" })
            itemsRVAdapter.filterList(filteredlist)

        }


    }

    private fun purchasedItemToServer(data: items){

        val apiInterface = ApiInterface.create().buy(data)

        apiInterface.enqueue( object : Callback<items> {
            override fun onResponse(call: Call<items>?, response: Response<items>?) {

                if (response != null) {
                    if (response.isSuccessful){
                        Toast.makeText(this@MainActivity, "Purchased", Toast.LENGTH_SHORT).show()

                    }else {
                        Toast.makeText(this@MainActivity, "Unsuccessful Purchase", Toast.LENGTH_SHORT).show()

                    }
                }
                //if(response?.body() != null)
                //recyclerAdapter.setMovieListItems(response.body()!!)
            }

            override fun onFailure(call: Call<items>?, t: Throwable?) {
                if (t != null) {
                    Toast.makeText(this@MainActivity, "A problem ${t.message}", Toast.LENGTH_SHORT).show()
                }

            }
        })


    }


}