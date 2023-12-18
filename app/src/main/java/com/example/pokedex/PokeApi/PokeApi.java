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

public class PokeApi {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    public static void getPokemonList(MutableLiveData<List<DataSimple>> pokemonList){
        Call<DataList> call = jsonPlaceHolderApi.getPokemonList(-1);
        call.enqueue(new Callback<DataList>() {

            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                List<DataSimple> pokemons = response.body().getResults();
                if(pokemons != null){
                    pokemonList.setValue(pokemons);
                }
                else{
                    Log.d("POKEDEX", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {

            }
        });
    }

    public static void getPokemonDetails(int id, MutableLiveData<PokeDetails> pokemon){
        Call<PokeDetails> call = jsonPlaceHolderApi.getPokemonDetails(String.valueOf(id));

        Log.d("El id del pokemon es: ", String.valueOf(id));
        call.enqueue(new Callback<PokeDetails>() {

            @Override
            public void onResponse(Call<PokeDetails> call, Response<PokeDetails> response) {
                PokeDetails pokemonResponse = response.body();
                if(pokemonResponse != null){
                    Log.d("POKEDEX", "onResponse: " + pokemonResponse.getName());
                    pokemon.setValue(pokemonResponse);
                }
                else{
                    Log.d("POKEDEX", "onResponse: es nulo");
                }
            }

            @Override
            public void onFailure(Call<PokeDetails> call, Throwable t) {
                Log.d("POKEDEX", "onFailure: " + t.getMessage());
            }
        });
    }

    public static void getColorPokemon(int id, MutableLiveData<String> color) {
        Call<ColorPokemon> call = jsonPlaceHolderApi.getColor(String.valueOf(id));

        call.enqueue(new Callback<ColorPokemon>() {
            @Override
            public void onResponse(Call<ColorPokemon> call, Response<ColorPokemon> response) {
                ColorPokemon colorPokemon = response.body();
                if (colorPokemon != null) {
                    color.setValue(colorPokemon.getColor().getName());
                } else {
                    Log.d("POKEDEX", "onResponse: es nulo");
                }
            }

            @Override
            public void onFailure(Call<ColorPokemon> call, Throwable t) {
                Log.d("POKEDEX", "onFailure: " + t.getMessage());
            }
        });
    }

}
