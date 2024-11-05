package com.tercerotest.controller.tda;


public class Censo {
    private Integer id;
    private int totalFamilias;
    private int familiasConGenerador;
    private String provincia;   
    private Array<Familia> familias;

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
