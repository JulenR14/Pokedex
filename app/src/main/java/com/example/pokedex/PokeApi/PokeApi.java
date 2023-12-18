package com.example.pokedex.PokeApi;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.pokedex.ClasesComunes.DataList;
import com.example.pokedex.ClasesComunes.DataSimple;
import com.example.pokedex.PokemonDetails;
import com.example.pokedex.models.ColorPokemon;
import com.example.pokedex.models.PokeDetails;
import com.example.pokedex.models.PokeList;
import com.example.pokedex.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
    En esta clase vamos a definir los métodos que se van a utilizar para obtener los datos de la API
 */
public class PokeApi {

    // Se crea un objeto retrofit para poder obtener los datos de la API
    //con la url base y el convertidor de JSON a objetos
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // Se crea un objeto de la interfaz JsonPlaceHolderApi para poder utilizar los métodos
    public static JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    /*
     Este metodo obtiene la lista de pokemons de la API y la guarda en el
     mutableLiveData que se le pasa como parámetro
     */
    public static void getPokemonList(MutableLiveData<List<DataSimple>> pokemonList){
        // Se crea un objeto de tipo Call para poder hacer la llamada a la API
        //le pasamos el limite -1 para que nos coja todos los pokemons
        Call<DataList> call = jsonPlaceHolderApi.getPokemonList(-1);

        // Se hace la llamada a la API
        call.enqueue(new Callback<DataList>() {

            //en el caso de que la llamada sea correcta
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                //almacenamos en una lista los pokemons que nos devuelve la API
                List<DataSimple> pokemons = response.body().getResults();
                //si la lista no es nula
                if(pokemons != null){
                    //se guarda en el mutableLiveData
                    pokemonList.setValue(pokemons);
                }
                else{
                    Log.d("POKEDEX", "onResponse: " + response.errorBody());
                }
            }

            //en el caso de que la llamada no sea correcta
            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Log.d("POKEDEX", "onFailure: " + t.getMessage());
            }
        });
    }

    /*
        Este metodo obtiene los detalles de un pokemon de la API y los guarda en el
        mutableLiveData que se le pasa como parámetro
     */
    public static void getPokemonDetails(int id, MutableLiveData<PokeDetails> pokemon){
        // Se crea un objeto de tipo Call para poder hacer la llamada a la API
        Call<PokeDetails> call = jsonPlaceHolderApi.getPokemonDetails(String.valueOf(id));
        // Se hace la llamada a la API
        call.enqueue(new Callback<PokeDetails>() {

            //en el caso de que la llamada sea correcta
            @Override
            public void onResponse(Call<PokeDetails> call, Response<PokeDetails> response) {
                //se guarda en un objeto de tipo PokeDetails los detalles del pokemon
                PokeDetails pokemonResponse = response.body();
                //si el objeto no es nulo
                if(pokemonResponse != null){
                    //se guarda en el mutableLiveData
                    pokemon.setValue(pokemonResponse);
                }
                else{
                    //si es nulo mostraremos un log con el error
                    Log.d("POKEDEX", "onResponse: es nulo");
                }
            }
            //en el caso de que la llamada no sea correcta
            @Override
            public void onFailure(Call<PokeDetails> call, Throwable t) {
                Log.d("POKEDEX", "onFailure: " + t.getMessage());
            }
        });
    }

    /*
        Este metodo obtiene el color de un pokemon de la API y lo guarda en el
        mutableLiveData que se le pasa como parámetro
     */

    public static void getColorPokemon(int id, MutableLiveData<String> color) {
        // Se crea un objeto de tipo Call para poder hacer la llamada a la API
        Call<ColorPokemon> call = jsonPlaceHolderApi.getColor(String.valueOf(id));

        // Se hace la llamada a la API
        call.enqueue(new Callback<ColorPokemon>() {
            //en el caso de que la llamada sea correcta
            @Override
            public void onResponse(Call<ColorPokemon> call, Response<ColorPokemon> response) {
                //se guarda en un objeto de tipo ColorPokemon el color del pokemon
                ColorPokemon colorPokemon = response.body();
                //si el objeto no es nulo
                if (colorPokemon != null) {
                    //se guarda en el mutableLiveData
                    color.setValue(colorPokemon.getColor().getName());
                } else {
                    //si es nulo mostraremos un log con el error
                    Log.d("POKEDEX", "onResponse: es nulo");
                }
            }
            //en el caso de que la llamada no sea correcta
            @Override
            public void onFailure(Call<ColorPokemon> call, Throwable t) {
                Log.d("POKEDEX", "onFailure: " + t.getMessage());
            }
        });
    }

}
