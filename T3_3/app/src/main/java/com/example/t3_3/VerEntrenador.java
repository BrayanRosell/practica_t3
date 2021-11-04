package com.example.t3_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t3_3.pokemn.Entrenador;
import com.example.t3_3.services.RetrofitService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerEntrenador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_entrenador);
        TextView tvNombre = findViewById(R.id.nombre);
        TextView tvTipo = findViewById(R.id.tipo);
        ImageView ivImagen = findViewById(R.id.imagen);
        Button btnUbicacion = findViewById(R.id.pokemones);

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerEntrenador.this, MainActivity.class);

                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        service.entrenador().enqueue(new Callback<Entrenador>() {
            @Override
            public void onResponse(Call<Entrenador> call, Response<Entrenador> response) {
                if (response.code()==200){
                    Entrenador entrenador = response.body();

                    String urlImage = entrenador.getImagen();
                    tvNombre.setText(entrenador.getNombres());
                    tvTipo.setText(entrenador.getPueblo());

                    Picasso.get().load(urlImage).into(ivImagen);
                }
            }

            @Override
            public void onFailure(Call<Entrenador> call, Throwable t) {

            }
        });
    }
}