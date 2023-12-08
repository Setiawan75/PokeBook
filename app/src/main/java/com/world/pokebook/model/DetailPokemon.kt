package com.world.pokebook.model

data class DetailPokemon(val sprites: Sprite, val abilities: ArrayList<Abilities>)

data class Sprite(val front_default: String)

data class Abilities(val ability: Ability){
    data class Ability(val name: String)
}
