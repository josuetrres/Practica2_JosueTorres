package com.tercerotest.controller.tda;

public class Generador {
    private Integer id;
    private String marca;
    private float potenciaGenerada;
    private float costo;
    private float consumoPorHora;

    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getPotenciaGenerada() {
        return this.potenciaGenerada;
    }

    public void setPotenciaGenerada(float potenciaGenerada) {
        this.potenciaGenerada = potenciaGenerada;
    }

    public float getCosto() {
        return this.costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getConsumoPorHora() {
        return this.consumoPorHora;
    }

    public void setConsumoPorHora(float consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }
    
}
