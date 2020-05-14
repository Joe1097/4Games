package com.example.pantallainicio.Memorama;

import android.widget.ImageButton;

public class Memoria {

    private ImageButton imagen;
    private int idimagen;

    public ImageButton getImagen(){
        return this.imagen;
    }

    public void setImagen(ImageButton imagen){
        this.imagen=imagen;
    }

    public int getIdimagen(){
        return this.idimagen;
    }

    public void setIdimagen(int idimagen){
        this.idimagen=idimagen;
    }

    public Memoria(ImageButton imagen, int idimagen){
        this.imagen=imagen;
        this.idimagen=idimagen;

    }

    public void cambiarImagen(){
        imagen.setImageResource(idimagen);
    }

    public void onClick() {
        cambiarImagen();
    }
}
