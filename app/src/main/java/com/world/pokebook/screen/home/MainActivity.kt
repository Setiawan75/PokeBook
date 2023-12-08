package com.world.pokebook.screen.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.world.pokebook.R
import com.world.pokebook.databinding.ActivityMainBinding
import com.world.pokebook.model.Pokemon
import com.world.pokebook.model.Result
import com.world.pokebook.model.network.APIService
import com.world.pokebook.screen.detail.DetailView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object{
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private var isAscending = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataAPI()
        initView()
    }

    private fun initView(){
        binding.modifyToolbar.apply {
            setTitle(getString(R.string.app_name))
        }

        binding.txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val searchKey = binding.txtSearch.text.toString()

                mainAdapter.search(searchKey)
                if (mainAdapter.dataToShow.isEmpty()) {
                    binding.lblNotFound.visibility = View.VISIBLE
                }else{
                    binding.lblNotFound.visibility = View.GONE
                }
                // update UI
                when (searchKey.isNotEmpty()) {
                    true -> {
                        binding.txtSearch.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, 0, 0
                        )
                        binding.imgClear.visibility = View.VISIBLE
                    }
                    false -> {
                        binding.txtSearch.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_search, 0
                        )
                        binding.imgClear.visibility = View.GONE
                    }
                }
            }
        })

        binding.imgClear.setOnClickListener { binding.txtSearch.setText("") }
        binding.sort.setOnClickListener {
            if (isAscending) {
                mainAdapter.sortData(isAscending)
                isAscending = false
            }else{
                mainAdapter.sortData(isAscending)
                isAscending = true
            }
        }
    }

    private fun getDataAPI(){
        binding.progressbar.visibility = View.VISIBLE
        APIService().endpoint.getListPokemon()
            .enqueue(object : Callback<Result>{
                override fun onResponse(
                    call: Call<Result>,
                    response: Response<Result>,
                ) {
                    binding.progressbar.visibility = View.GONE
                    if (response.isSuccessful){
                        val result = response.body()
                        Log.e("HAHAHA", "respon: ${result?.results.toString()}")
                        if (result?.results?.isNotEmpty() == true) {
                            mainAdapter = MainAdapter(result.results)
                            binding.rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
                            binding.rvPokemon.adapter = mainAdapter

                            mainAdapter.onClickItem(object : MainAdapter.ClickListener{
                                override fun onItemClickRoot(pokemon: Pokemon) {
                                    startActivity(DetailView.newIntent(this@MainActivity, pokemon))
                                }

                            })
                        }
                    }
                }

                override fun onFailure(call: Call<Result>, t: Throwable) {
                    Log.e("HAHAHA", "error: $t")
                    binding.progressbar.visibility = View.GONE
                }

            })
    }
}