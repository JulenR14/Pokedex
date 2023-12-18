package com.example.pokedex;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.example.pokedex.ClasesComunes.DataSimple;
import com.example.pokedex.PokeApi.PokeApi;
import com.example.pokedex.databinding.FragmentListaPokemonsBinding;
import com.example.pokedex.models.Pokemon;
import com.google.android.material.color.utilities.CorePalette;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaPokemons#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPokemons extends Fragment {

    FragmentListaPokemonsBinding binding;
    ViewModelPokemon viewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentListaPokemonsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModelPokemon.class);
        navController = Navigation.findNavController(view);

        PokemonAdapter pokemonAdapter = new PokemonAdapter();

        binding.recyclerViewPokemons.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerViewPokemons.setAdapter(pokemonAdapter);

        viewModel.getPokemonList().observe(getViewLifecycleOwner(), new Observer<List<DataSimple>>() {
            @Override
            public void onChanged(List<DataSimple> pokemons) {
                pokemonAdapter.setPokemons(pokemons);
            }
        });
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nombrePokemon);
            imageView = itemView.findViewById(R.id.imagePokemon);
        }
    }

    class PokemonAdapter extends RecyclerView.Adapter<PokemonViewHolder> {

        List<DataSimple> pokemons;

        public DataSimple getPokemon(int posicion){
            return pokemons.get(posicion);
        }

        @NonNull
        @Override
        public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PokemonViewHolder(getLayoutInflater().inflate(R.layout.card_pokemon, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
            DataSimple pokemon = pokemons.get(position);
            holder.name.setText(pokemon.getName());
            String id = pokemon.getUrl().split("/")[pokemon.getUrl().split("/").length - 1];
            Glide.with(holder.itemView)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+ id +".png")
                    .into(holder.imageView);
            viewModel.selectColor(Integer.parseInt(id));
            //holder.imageView.setBackgroundColor(Color.parseColor(viewModel.colorSelected().getValue()));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.selectPokemon(Integer.parseInt(id));
                    navController.navigate(R.id.pokemonDetails);
                }
            });
        }

        @Override
        public int getItemCount() {
            return pokemons == null ? 0 : pokemons.size();
        }

        void setPokemons(List<DataSimple> pokemons) {
            this.pokemons = pokemons;
            notifyDataSetChanged();
        }
    }
}