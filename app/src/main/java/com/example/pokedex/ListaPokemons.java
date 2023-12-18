package com.example.pokedex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
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
    //atributos

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

        binding.toolbar.setTitle("Pokedex");

        //creamos el objeto de la clase ViewModelPokemon y nav controller
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModelPokemon.class);
        navController = Navigation.findNavController(view);

        //creamos el objeto de la clase PokemonAdapter
        PokemonAdapter pokemonAdapter = new PokemonAdapter();

        //creamos el objeto de la clase GridLayoutManager dentro del metodo
        // para poder mostrar los pokemons en forma de grid
        binding.recyclerViewPokemons.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //asignamos el adaptador al recycler
        binding.recyclerViewPokemons.setAdapter(pokemonAdapter);

        //observamos el mutableLiveData que contiene la lista de pokemons
        viewModel.getPokemonList().observe(getViewLifecycleOwner(), new Observer<List<DataSimple>>() {
            @Override
            public void onChanged(List<DataSimple> pokemons) {
                //cuando cambie la lista de pokemons se actualiza el adaptador
                pokemonAdapter.setPokemons(pokemons);
            }
        });
    }

    //clase que define el ViewHolder
    class PokemonViewHolder extends RecyclerView.ViewHolder {
        //texto y imagen que se muetra en la card de cada pokemon
        TextView name;
        ImageView imageView;
        ProgressBar pb;
        CardView cardView;
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nombrePokemon);
            imageView = itemView.findViewById(R.id.imagePokemon);
            pb = itemView.findViewById(R.id.progress);
            cardView = itemView.findViewById(R.id.cardPokemon);
        }
    }

    //clase que define el adaptador de Recyler View
    class PokemonAdapter extends RecyclerView.Adapter<PokemonViewHolder> {

        //lista de pokemons
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
            holder.pb.setVisibility(View.VISIBLE);
            //se obtiene el pokemon de la lista
            DataSimple pokemon = getPokemon(position);
            //se asigna el nombre del pokemon
            holder.name.setText(pokemon.getName());
            //se obtiene el id del pokemon mediante la url
            String id = pokemon.getUrl().split("/")[pokemon.getUrl().split("/").length - 1];
            //se asigna la imagen del pokemon con el id
            Glide.with(holder.itemView)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+ id +".png")
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.pb.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.pb.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.imageView);

            Glide.with(holder.cardView).asBitmap().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+ id +".png")
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                    Palette.from(resource).generate(palette -> {
                                        if (palette!=null){
                                            GradientDrawable grad = new GradientDrawable();
                                            grad.setColors(new int[]{palette.getDominantColor(Color.rgb(80, 80,80)), Color.rgb(80, 80,80)});
                                            grad.setCornerRadius(20f);
                                            holder.cardView.setBackground(grad);
                                        }
                                    });
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });


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