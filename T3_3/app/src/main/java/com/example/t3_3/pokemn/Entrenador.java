package com.example.t3_3.pokemn;

public class Entrenador {
    private String nombres;
    private String imagen;
    private String pueblo;

    public Entrenador() {
    }

    public Entrenador(String nombres, String imagen, String pueblo) {
        this.nombres = nombres;
        this.imagen = imagen;
        this.pueblo = pueblo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPueblo() {
        return pueblo;
    }

    public void setPueblo(String pueblo) {
        this.pueblo = pueblo;
    }
}
