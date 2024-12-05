package com.tercerotest.controller.dao.services;

import com.google.gson.Gson;
import com.tercerotest.controller.dao.implement.RegistroDao;
import com.tercerotest.controller.tda.Registro;
import com.tercerotest.controller.tda.Familia;
import com.tercerotest.controller.tda.LinkedList;

public class RegistroServices {
    private RegistroDao registroDao;

    public RegistroServices() {
        this.registroDao = new RegistroDao();
    }

    public Boolean save() throws Exception {
        return this.registroDao.save();
    }

    public LinkedList<Registro> listAll() {
        return this.registroDao.getListAll();
    }

    public Registro getRegistro() {
        return this.registroDao.getRegistro();
    }

    public void setRegistro(Registro registro) {
        this.registroDao.setRegistro(registro);
    }

    public RegistroDao getRegistroDao() {
        return this.registroDao;
    }

    public Registro get(Integer id) throws Exception {
        return (Registro) registroDao.get(id);
    }

    public Boolean update() throws Exception {
        return registroDao.update();
    }

    public String toJson() {
        LinkedList<Registro> registros = listAll(); 
        return new Gson().toJson(registros); 
    }
    
    public void deleteRegistro(Integer id) throws Exception {
        registroDao.deleteRegistro(id);
    }

    public void registrarEvento(String accion, String descripcion) throws Exception {
        registroDao.registrarEvento(accion, descripcion);
    }

}
