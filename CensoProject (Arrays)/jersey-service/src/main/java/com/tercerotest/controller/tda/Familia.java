package com.tercerotest.controller.tda;

import com.tercerotest.controller.tda.Array;

public class Familia {
    private Integer id;
    private boolean tieneGenerador;
    private int cantidadPersonas;
    private String razonUso;
    private Array<Generador> generadores;

    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTieneGenerador() {
        return this.tieneGenerador;
    }

    public boolean getTieneGenerador() {
        return this.tieneGenerador;
    }

    public void setTieneGenerador(boolean tieneGenerador) {
        this.tieneGenerador = tieneGenerador;
    }

    public int getCantidadPersonas() {
        return this.cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getRazonUso() {
        return this.razonUso;
    }

    public void setRazonUso(String razonUso) {
        this.razonUso = razonUso;
    }
    
}
