package com.example.t3_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.t3_3.pokemn.Entrenador;
import com.example.t3_3.services.RetrofitService;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearEntrenador extends AppCompatActivity {

    static final int REQUEST_PICK_FROM_GALLERY = 2;
    public String fileBase64;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_entrenador);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
        }

        Button btnGallery = findViewById(R.id.imagen);
        imageView = findViewById(R.id.galeria);
        EditText etName = findViewById(R.id.editTextTextPersonName6);
        EditText etPueblo = findViewById(R.id.editTextTextPersonName7);
        Button btnEnviar = findViewById(R.id.guardar);

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
                Entrenador entrenador = new Entrenador();
                entrenador.setNombres(etName.getText().toString());
                entrenador.setPueblo(etPueblo.getText().toString());
                entrenador.setImagen(fileBase64);

                Call<Entrenador> call = service.crear(entrenador);

                call.enqueue(new Callback<Entrenador>() {
                    @Override
                    public void onResponse(Call<Entrenador> call, Response<Entrenador> response) {
                        Intent intent = new Intent(CrearEntrenador.this, Entrenador.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<Entrenador> call, Throwable t) {

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


            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            fileBase64 = gtFile(picturePath);

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