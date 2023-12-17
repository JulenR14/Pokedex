package com.example.pokedex;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pokedex.ClasesComunes.DataList;
import com.example.pokedex.ClasesComunes.DataSimple;
import com.example.pokedex.PokeApi.PokeApi;
import com.example.pokedex.models.PokeDetails;
import com.example.pokedex.models.Pokemon;

import java.util.List;

public class ViewModelPokemon extends AndroidViewModel {

    MutableLiveData<List<DataSimple>> pokemonList = new MutableLiveData<>();
    MutableLiveData<PokeDetails> pokemonSeleccionado = new MutableLiveData<>();

    void selectPokemon(int pokemon) {
        PokeApi.getPokemonDetails(pokemon, pokemonSeleccionado);
        //this.pokemonSeleccionado.setValue(pokemon);
    }

    MutableLiveData<PokeDetails> pokemonSelected() {
        return pokemonSeleccionado;
    }
    public ViewModelPokemon(@NonNull Application application) {
        super(application);

        PokeApi.getPokemonList(pokemonList);
    }

    MutableLiveData<List<DataSimple>> getPokemonList() {
        return pokemonList;
    }


}
