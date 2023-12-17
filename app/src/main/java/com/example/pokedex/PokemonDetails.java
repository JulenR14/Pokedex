package com.example.pokedex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pokedex.databinding.FragmentListaPokemonsBinding;
import com.example.pokedex.databinding.FragmentPokemonDetailsBinding;
import com.example.pokedex.models.PokeDetails;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokemonDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokemonDetails extends Fragment {

    FragmentPokemonDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelPokemon viewModel = new ViewModelProvider(requireActivity()).get(ViewModelPokemon.class);

        viewModel.pokemonSelected().observe(getViewLifecycleOwner(), new Observer<PokeDetails>() {
            @Override
            public void onChanged(PokeDetails pokemonDetails) {
                Glide.with(requireView())
                        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+ pokemonDetails.getId() +".png")
                        .into(binding.pokemonImage);
                //binding.nombrePokemon.setText(pokemonDetails.getName());
            }
        });
    }
}