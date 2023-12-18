package com.example.pokedex.PokeApi;


import com.example.pokedex.ClasesComunes.DataList;
import com.example.pokedex.ListaPokemons;
import com.example.pokedex.PokemonDetails;
import com.example.pokedex.models.ColorPokemon;
import com.example.pokedex.models.PokeDetails;
import com.example.pokedex.models.PokeList;
import com.example.pokedex.models.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Esta interfaz se utiliza para obtener los datos de la API
public interface JsonPlaceHolderApi {
    @GET("pokemon/")
    Call<DataList> getPokemonList(@Query("limit") int limit);

    @GET("pokemon/{id}")
    Call<PokeDetails> getPokemonDetails(@Path("id") String id);

    @GET("pokemon-species/{id}")
    Call<ColorPokemon> getColor(@Path("id") String id);
}
