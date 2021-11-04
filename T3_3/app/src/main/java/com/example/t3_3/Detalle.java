package com.example.t3_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t3_3.pokemn.PokemonClass;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class Detalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Intent intent = getIntent();
        String pokemon = intent.getStringExtra("Pokemon");
        PokemonClass pokemons = new Gson().fromJson(pokemon, PokemonClass.class);

        TextView tvNombre = findViewById(R.id.tvNombre);
        TextView tvTipo = findViewById(R.id.tvTipo);
        ImageView ivImagen = findViewById(R.id.ivImage);
        Button btnUbicacion = findViewById(R.id.btnUicacion);

        String urlImage = pokemons.getUrl_imagen();
        tvNombre.setText(pokemons.getNombre());
        tvTipo.setText(pokemons.getTipo());
        tvNombre.setText(pokemons.getNombre());

        String latitud = String.valueOf(pokemons.getLatitude());
        String longitud = String.valueOf(pokemons.getLongitude());

        Picasso.get().load(urlImage).into(ivImagen);

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detalle.this, MapsActivity.class);
                intent.putExtra("Latitud", latitud);
                intent.putExtra("Longitud", longitud);
                startActivity(intent);
            }
        });
    }
}