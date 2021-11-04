package com.example.t3_3.services;

import com.example.t3_3.pokemn.Entrenador;
import com.example.t3_3.pokemn.PokemonClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {
    @GET("pokemons/N0036447/")
    Call<List<PokemonClass>> getALL();

    @POST("pokemons/N0036447/crear")
    Call<PokemonClass> create(@Body PokemonClass pokemon);

    @GET("entrenador/N0036447")
    Call<Entrenador> entrenador();

    @POST("entrenador/N0036447")
    Call<Entrenador> crear(@Body Entrenador entrenador);
}
