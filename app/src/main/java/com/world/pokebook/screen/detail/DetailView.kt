package com.world.pokebook.screen.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.world.pokebook.R
import com.world.pokebook.databinding.ActivityDetailBinding
import com.world.pokebook.model.DetailPokemon
import com.world.pokebook.model.Pokemon
import com.world.pokebook.model.Result
import com.world.pokebook.model.network.APIService
import com.world.pokebook.screen.home.MainAdapter
import com.world.pokebook.screen.shared.utils.getParcelableIntent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class DetailView : AppCompatActivity() {

    companion object{
        private const val EXTRA_DATA = "extra_data"
        var url = ""
        fun newIntent(context: Context, pokemon: Pokemon = Pokemon()):Intent = Intent(context, DetailView::class.java).apply {
            putExtra(EXTRA_DATA, pokemon)
        }
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var mainAdapter : MainAdapter
    private var pokemon = Pokemon()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBundle()
        getDataAPI()
        initView()
    }

    private fun initBundle(){
        pokemon = intent.getParcelableIntent(EXTRA_DATA)?:Pokemon()
        url = "pokemon/"+pokemon.name+"/"
    }
    private fun initView(){
        binding.modifyToolbar.apply {
            setTitle(getString(R.string.app_name))
        }
        binding.namePokemon.text = pokemon.name
    }

    private fun getDataAPI(){
        binding.progressbar.visibility = View.VISIBLE
        APIService().endpoint.getDetailPokemon("pokemon/"+pokemon.name+"/")
            .enqueue(object : Callback<DetailPokemon> {
                override fun onResponse(
                    call: Call<DetailPokemon>,
                    response: Response<DetailPokemon>,
                ) {
                    binding.progressbar.visibility = View.GONE
                    if (response.isSuccessful){
                        val result = response.body()
                        Glide.with(this@DetailView)
                            .load(result?.sprites?.front_default)
                            .dontAnimate()
                            .placeholder(R.drawable.pokeball)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(binding.imgPokemon)
                        Log.e("HAHAHA", "respon: ${result?.abilities.toString()}")
                        if (result?.abilities?.isNotEmpty() == true) {
                            val pokemon : ArrayList<Pokemon> = arrayListOf()
                            result.abilities.map {
                                pokemon.add(Pokemon(it.ability.name))
                            }
                            mainAdapter = MainAdapter(pokemon)
                            binding.rvPokemon.layoutManager = LinearLayoutManager(this@DetailView)
                            binding.rvPokemon.adapter = mainAdapter

                            mainAdapter.onClickItem(object : MainAdapter.ClickListener{
                                override fun onItemClickRoot(pokemon: Pokemon) {
                                }

                            })
                        }
                    }
                }

                override fun onFailure(call: Call<DetailPokemon>, t: Throwable) {
                    Log.e("HAHAHA", "error: $t")
                    binding.progressbar.visibility = View.GONE
                }

            })
    }
}