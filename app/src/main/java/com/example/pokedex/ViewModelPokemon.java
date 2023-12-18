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

/*
    Esta clase contendra los datos que se utilizaran en la aplicacion
    conectaremos con ella para obtener los datos.
    Esta clase se encargara de llamar a los metodos de la clase PokeApi
    y de almacenar los datos que se obtengan de la API
 */
public class ViewModelPokemon extends AndroidViewModel {

    //atributos
    MutableLiveData<List<DataSimple>> pokemonList = new MutableLiveData<>();
    MutableLiveData<PokeDetails> pokemonSeleccionado = new MutableLiveData<>();
    MutableLiveData<String> colorSeleccionado = new MutableLiveData<>();

    //metodo que cogera los detalles del pokemon seleccionado en el Recyler
    void selectPokemon(int pokemon) {
        PokeApi.getPokemonDetails(pokemon, pokemonSeleccionado);
        //this.pokemonSeleccionado.setValue(pokemon);
    }

    //metodo que cogera el color del pokemon seleccionado en el Recyler
    void selectColor(int id) {
        PokeApi.getColorPokemon(id, colorSeleccionado);
    }

    //observaremos este metodo paa obtener el color del pokemon seleccionado
    MutableLiveData<String> colorSelected() {
        return colorSeleccionado;
    }

    //observaremos este metodo paa obtener los detalles del pokemon seleccionado
    MutableLiveData<PokeDetails> pokemonSelected() {
        return pokemonSeleccionado;
    }
    public ViewModelPokemon(@NonNull Application application) {
        super(application);
        //al crear el objeto llamamos al metodo que nos devuelve la lista de pokemons
        PokeApi.getPokemonList(pokemonList);
    }

    //metodo que devuelve la lista de pokemons
    MutableLiveData<List<DataSimple>> getPokemonList() {
        return pokemonList;
    }


}
