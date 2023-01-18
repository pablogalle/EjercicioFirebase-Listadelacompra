package com.example.ejerciciofirebase_listadelacompra.modelos;

import java.text.NumberFormat;

public class Producto {
    private String nombre;
    private int cantidad;
    private float precio;
    private float precioTotal;
    private static final NumberFormat numberFormat;

    static {
        numberFormat = NumberFormat.getCurrencyInstance();
    }

    public Producto(){}

    public Producto(String nombre, int cantidad, float precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        precioTotal = precio*cantidad;
    }

    public String getPrecioMoneda(){
        return numberFormat.format(this.precioTotal);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }
}
