package com.example.t3_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.t3_3.pokemn.PokemonClass;
import com.example.t3_3.services.RetrofitService;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Capturar extends AppCompatActivity {

    static final int REQUEST_PICK_FROM_GALLERY = 2;
    public ImageView imageView;
    String fileBase64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar);
        EditText nombre = findViewById(R.id.editTextTextPersonName3);
        EditText tipo = findViewById(R.id.editTextTextPersonName5);
        EditText latitud = findViewById(R.id.editTextTextPersonName);
        EditText longitud = findViewById(R.id.editTextTextPersonName4);
        Button btnEnviar = findViewById(R.id.button3);
        Button btnGallery = findViewById(R.id.button4);
        imageView = findViewById(R.id.galeria);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryAddPic();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PokemonClass pokemon = new PokemonClass();
                pokemon.setNombre(nombre.getText().toString());
                pokemon.setTipo(tipo.getText().toString());
                pokemon.setUrl_imagen(fileBase64);
                pokemon.setLatitude(Float.parseFloat(latitud.getText().toString()) * -1);
                pokemon.setLongitude(Float.parseFloat(longitud.getText().toString()) * -1);

                Call<PokemonClass> call = service.create(pokemon);

                call.enqueue(new Callback<PokemonClass>() {
                    @Override
                    public void onResponse(Call<PokemonClass> call, Response<PokemonClass> response) {
                        Intent intent = new Intent(Capturar.this, MainActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<PokemonClass> call, Throwable t) {
                    }
                });
            }
        });
    }

    private void galleryAddPic() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_PICK_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_PICK_FROM_GALLERY && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            //Añadir a base64
            fileBase64 = gtFile(picturePath);
        }
    }
    public static String gtFile(String filePath){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG,100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.NO_WRAP);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }
}