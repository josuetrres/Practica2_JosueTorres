package com.tercerotest.controller.tda;

import java.util.LinkedList;

public class Censo {
    private Integer id;
    private int totalFamilias;
    private int familiasConGenerador;
    private String provincia;   
    private LinkedList<Familia> familias;

    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalFamilias() {
        return this.totalFamilias;
    }

    public void setTotalFamilias(int totalFamilias) {
        this.totalFamilias = totalFamilias;
    }

    public int getFamiliasConGenerador() {
        return this.familiasConGenerador;
    }

    public void setFamiliasConGenerador(int familiasConGenerador) {
        this.familiasConGenerador = familiasConGenerador;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

}
