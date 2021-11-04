package com.example.t3_3.pokemn;

public class PokemonClass {
    private String nombre;
    private String tipo;
    private String url_imagen;
    private Number latitude;
    private Number longitude;

    public PokemonClass() {
    }

    public PokemonClass(String nombre, String tipo, String url_imagen, Number latitude, Number longitude) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.url_imagen = url_imagen;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public Number getLatitude() {
        return latitude;
    }

    public void setLatitude(Number latitude) {
        this.latitude = latitude;
    }

    public Number getLongitude() {
        return longitude;
    }

    public void setLongitude(Number longitude) {
        this.longitude = longitude;
    }
}
