package com.example.t3_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t3_3.adapter.PokemonAdapter;
import com.example.t3_3.pokemn.PokemonClass;
import com.example.t3_3.services.RetrofitService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAct extends AppCompatActivity {

    private androidx.recyclerview.widget.RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        recyclerView = findViewById(R.id.rvPokemons);
        ImageView img = findViewById(R.id.imagenPoke);
        TextView nombre = findViewById(R.id.nombrePoke);
        TextView tipo = findViewById(R.id.tipoPoke);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        service.getALL().enqueue(new Callback<List<PokemonClass>>() {
            @Override
            public void onResponse(Call<List<PokemonClass>> call, Response<List<PokemonClass>> response) {
                if (response.code()==200){
                    List<PokemonClass> pokemons = response.body();

                    recyclerView.setAdapter(new PokemonAdapter(pokemons));
                }else {
                    Log.i("MY_APP" , "falla en la app");
                }
            }

            @Override
            public void onFailure(Call<List<PokemonClass>> call, Throwable t) {
                Log.i("MY_APP" , "falla en el servidor");
            }
        });
    }
}