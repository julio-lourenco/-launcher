package com.blueoceansolutions.launcher;

import android.graphics.drawable.Drawable;

public class Item {

    private String nome, pack;
    private Drawable icon;

    public Item(String nome, String pack, Drawable icon){

        this.nome = nome;
        this.pack = pack;
        this.icon = icon;

    }

    public String getNome() {
        return nome;
    }

    public String getPack() {
        return pack;
    }

    public Drawable getIcon() {
        return icon;
    }
}
