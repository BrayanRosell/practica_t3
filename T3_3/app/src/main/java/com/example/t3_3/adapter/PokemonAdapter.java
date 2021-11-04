package com.example.t3_3.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t3_3.Detalle;
import com.example.t3_3.R;
import com.example.t3_3.pokemn.PokemonClass;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>{

    private List<PokemonClass> listaPokemons;

    public PokemonAdapter(List<PokemonClass> listaPokemons) {
        this.listaPokemons = listaPokemons;
    }

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon, parent, false);
        PokemonViewHolder vh = new PokemonViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder holder, int position) {
        TextView nombre = holder.itemView.findViewById(R.id.nombrePoke);
        TextView tipo = holder.itemView.findViewById(R.id.tipoPoke);
        ImageView imagen = holder.itemView.findViewById(R.id.imagenPoke);

        PokemonClass pokemon = listaPokemons.get(position);
        nombre.setText(pokemon.getNombre());
        String url = "https://upn.lumenes.tk" + pokemon.getUrl_imagen();
        tipo.setText(pokemon.getTipo());

        Picasso.get().load(url).into(imagen);

        Button verDetalle = holder.itemView.findViewById(R.id.verDetalle);

        verDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), Detalle.class);
                intent.putExtra("Pokemon", new Gson().toJson(pokemon));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPokemons.size();
    }

    // Por cada clase adapter, se necesita una clase ViewHolder
    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}