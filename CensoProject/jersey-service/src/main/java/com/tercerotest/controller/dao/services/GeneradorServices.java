package com.tercerotest.controller.dao.services;

import com.google.gson.Gson;
import com.tercerotest.controller.dao.implement.GeneradorDao;
import com.tercerotest.controller.dao.implement.RegistroDao;
import com.tercerotest.controller.tda.Censo;
import com.tercerotest.controller.tda.Generador;
import com.tercerotest.controller.tda.LinkedList;
import com.tercerotest.controller.tda.Registro;

public class GeneradorServices {
    private GeneradorDao obj;
    private RegistroDao registroDao;

    public GeneradorServices() {
        this.obj = new GeneradorDao();
        this.registroDao = new RegistroDao();
    }

    public Boolean save() throws Exception {
        registroDao.registrarEvento("Generador", "Creación de generador con ID: " + obj.getGenerador().getId());
        return this.obj.save();
    }

    public LinkedList<Generador> listAll() {
        return this.obj.listAll();
    }

    public Generador getGenerador() {
        return this.obj.getGenerador();
    }

    public void setGenerador(Generador generador) {
        this.obj.setGenerador(generador);
    }

    public GeneradorDao getGeneradorDao() {
        return this.obj;
    }

    public Generador get(Integer id) throws Exception{
        registroDao.registrarEvento("Generador", "Obtención de generador con ID: " + obj.getGenerador().getId());
        return (Generador) obj.get(id);
    }

    public Boolean update() throws Exception {
        registroDao.registrarEvento("Generador", "Actualización de generador exitoso.");
        return obj.update();
    }

     public String toJson() {
        LinkedList<Generador> generadores = listAll(); 
        return new Gson().toJson(generadores); 
    }

    public void deleteGenerador(Integer id) throws Exception {
        registroDao.registrarEvento("Generador", "Eliminación de generador con ID: " + obj.getGenerador().getId());
        obj.deleteGenerador(id);
    }
}