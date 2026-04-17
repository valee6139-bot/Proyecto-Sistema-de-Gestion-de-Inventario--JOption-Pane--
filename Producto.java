package com.mycompany.Travalo;

public class Producto {
    String nombre;
    String categoria;
    int stock;

    public Producto(String nombre, String categoria, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return nombre + " | " + categoria + " | Stock: " + stock;
    }
}
