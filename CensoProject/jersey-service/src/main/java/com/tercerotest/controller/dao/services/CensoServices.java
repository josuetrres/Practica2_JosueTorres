package com.tercerotest.controller.dao.services;

import com.google.gson.Gson;
import com.tercerotest.controller.dao.implement.CensoDao;
import com.tercerotest.controller.dao.implement.RegistroDao;
import com.tercerotest.controller.tda.Censo;
import com.tercerotest.controller.tda.Familia;
import com.tercerotest.controller.tda.LinkedList;

public class CensoServices {
    private CensoDao obj;
    private RegistroDao registroDao;

    public CensoServices() {
        this.obj = new CensoDao();
        this.registroDao = new RegistroDao(); 
    }

    public Boolean save() throws Exception{
        
        return this.obj.save();
    }

    public LinkedList<Censo> listAll() {
        return this.obj.listAll();
    }
    public Censo getCenso(){
        return this.obj.getCenso();
    }
    public void setCenso(Censo censo){
        this.obj.setCenso(censo);
    }

    public CensoDao getCensoDao() throws Exception{
        registroDao.registrarEvento("Censo", "Obtención de censo con ID: " + obj.getCenso().getId());
        return this.obj;
    }
    public Censo get(Integer id) throws Exception{
        registroDao.registrarEvento("Guardar Censo", "Censo con ID " +  obj.getCenso().getId() + " ha sido guardado.");
        return (Censo) obj.get(id);
    }

    public Boolean update() throws Exception {
        registroDao.registrarEvento("Censo", "Actualización de censo exitoso.");
        return obj.update();
    }

    public String toJson() {
        LinkedList<Censo> censos = listAll(); 
        return new Gson().toJson(censos); 
    }
    
    public void deleteCenso(Integer id) throws Exception {
        obj.deleteCenso(id);
        registroDao.registrarEvento("Censo", "Eliminación de censo exitoso.");
    }

    public LinkedList<Censo> mergeOrder(String attribute, Integer type) throws Exception {
        return obj.listAll().mergeOrder(attribute, type);
    }

    public LinkedList<Censo> quickOrder(String attribute, Integer type) throws Exception {
        return obj.listAll().quickOrder(attribute, type);
    }

    public LinkedList<Censo> shellOrder(String attribute, Integer type) throws Exception {
        return obj.listAll().shellOrder(attribute, type);
    }
    public LinkedList<Censo> linearSearch(String attribute, String value) throws Exception {
        return obj.listAll().linearSearch(attribute, value);
    }

    public LinkedList<Censo> binarySearch(String attribute, String value) throws Exception {
        return obj.listAll().binarySearch(attribute, value);
    }
}