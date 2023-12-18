package com.example.pokedex;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pokedex.databinding.FragmentListaPokemonsBinding;
import com.example.pokedex.databinding.FragmentPokemonDetailsBinding;
import com.example.pokedex.models.PokeDetails;

import org.w3c.dom.Text;

import java.util.List;

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
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(PokeDetails pokemonDetails) {
                Glide.with(requireView())
                        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+ pokemonDetails.getId() +".png")
                        .into(binding.pokemonImage);
                binding.pokemonName.setText(pokemonDetails.getName());
                binding.recyclerViewTypes.setAdapter(new itemTypeViewHolder(pokemonDetails.getTypes()));
                binding.pesoPokemon.setText(pokemonDetails.getWeight()/10 + " KG");
                binding.alturaPokemon.setText(pokemonDetails.getHeight()/10 + " M");
                if(pokemonDetails.getStats().get(0).getStat().getName().equals("hp")){
                    binding.hpIndicator.setProgress(pokemonDetails.getStats().get(0).getBase_stat());
                }
                if(pokemonDetails.getStats().get(1).getStat().getName().equals("attack")){
                    binding.atkIndicator.setProgress(pokemonDetails.getStats().get(1).getBase_stat());
                }
                if(pokemonDetails.getStats().get(2).getStat().getName().equals("defense")){
                    binding.defIndicator.setProgress(pokemonDetails.getStats().get(2).getBase_stat());
                }
                if(pokemonDetails.getStats().get(3).getStat().getName().equals("special-attack")){
                    binding.spdIndicator.setProgress(pokemonDetails.getStats().get(3).getBase_stat());
                }
                binding.expIndicator.setProgress(pokemonDetails.getBase_experience());

                Glide.with(binding.pokemonImage).asBitmap().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+ pokemonDetails.getId() +".png")
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                Palette.from(resource).generate(palette -> {
                                    if (palette!=null){
                                        binding.pokemonImage.setBackgroundColor(palette.getDominantColor(Color.parseColor("#000000")));
                                    }
                                });
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }
        });
    }

    public int changeBackgroundType(String type){
        int color = 0;
        switch (type) {
            case "fighting":
                color = R.color.fighting;
                break;
            case "flying":
                color = R.color.flying;
                break;
            case "poison":
                color = R.color.poison;
                break;
            case "ground":
                color = R.color.ground;
                break;
            case "rock":
                color = R.color.rock;
                break;
            case "bug":
                color = R.color.bug;
                break;
            case "ghost":
                color = R.color.ghost;
                break;
            case "steel":
                color = R.color.steel;
                break;
            case "fire":
                color = R.color.fire;
                break;
            case "water":
                color = R.color.water;
                break;
            case "grass":
                color = R.color.grass;
                break;
            case "electric":
                color = R.color.electric;
                break;
            case "psychic":
                color = R.color.psychic;
                break;
            case "ice":
                color = R.color.ice;
                break;
            case "dragon":
                color = R.color.dragon;
                break;
            case "dark":
                color = R.color.dark;
                break;
            case "fairy":
                color = R.color.fairy;
                break;
            default:
                color = R.color.gray_21;
                break;
        }

        return color;
    }

    class itemTypeViewHolder extends RecyclerView.Adapter<PokemonDetails.itemViewHolder>{

        List<PokeDetails.Types> types;

        public itemTypeViewHolder(List<PokeDetails.Types> types){
            this.types = types;
        }

        public PokeDetails.Types obtenerType(int position){
            return types.get(position);
        }

        @NonNull
        @Override
        public PokemonDetails.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PokemonDetails.itemViewHolder(getLayoutInflater().inflate(R.layout.item_recyclertype_pokemon, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PokemonDetails.itemViewHolder holder, int position) {
            PokeDetails.Types type = types.get(position);
            holder.type.setText(type.getType().getName());
            holder.cardView.setCardBackgroundColor(getResources().getColor(changeBackgroundType(type.getType().getName())));
            holder.cardView.setRadius(50);
        }

        @Override
        public int getItemCount() {
            return types.size();
        }
    }

    class itemViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView type;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.typePokemon);
            cardView = itemView.findViewById(R.id.cardViewType);
        }
    }

}