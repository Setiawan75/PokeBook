package com.world.pokebook.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.world.pokebook.R
import com.world.pokebook.databinding.ItemPokemonBinding
import com.world.pokebook.model.Pokemon
import java.util.Locale

class MainAdapter(var data: ArrayList<Pokemon>): RecyclerView.Adapter<MainAdapter.MainHolder>() {
    var dataToShow= ArrayList<Pokemon>()
    var listener: ClickListener? = null

    init {
        dataToShow.addAll(data)
    }

    override fun getItemCount(): Int {
        return dataToShow.size
    }

    override fun onBindViewHolder(holder: MainAdapter.MainHolder, position: Int) {
        holder.bind(dataToShow[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return MainHolder(view)
    }

    inner class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPokemonBinding.bind(itemView)
        private val context = binding.root.context
        fun bind(pokemon: Pokemon, listener: ClickListener?) {
            with(binding) {
                title.text = pokemon.name
                root.setOnClickListener {
                    listener?.onItemClickRoot(pokemon)
                }
            }
        }
    }

    interface ClickListener {
        fun onItemClickRoot(pokemon: Pokemon)
    }

    fun onClickItem(listener: ClickListener){
        this.listener = listener
    }
    fun search(charText: String) {
        val charTextSearch = charText.lowercase(Locale.getDefault())
        dataToShow.clear()
        if (charTextSearch.isEmpty()) {
            dataToShow.addAll(data)
            notifyDataSetChanged()
        } else {
            for (menu in data) {
                if (menu.name.lowercase(Locale.getDefault()).contains(charTextSearch)) {
                    dataToShow.add(menu)
                }
            }
            notifyDataSetChanged()
        }
    }
    fun sortData(isAscending: Boolean = true){
        if (isAscending) dataToShow.sortBy { it.name }
        else dataToShow.sortByDescending { it.name }
        notifyDataSetChanged()
    }
}