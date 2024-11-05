package com.tercerotest.controller.dao.services;

import com.google.gson.Gson;
import com.tercerotest.controller.dao.implement.CensoDao;
import com.tercerotest.controller.dao.implement.RegistroDao;
import com.tercerotest.controller.tda.Censo;
import com.tercerotest.controller.tda.Array;

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

    public Array<Censo> listAll() {
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
        Array<Censo> censos = listAll(); 
        return new Gson().toJson(censos); 
    }
    
    public void deleteCenso(Integer id) throws Exception {
        obj.deleteCenso(id);
        registroDao.registrarEvento("Censo", "Eliminación de censo exitoso.");
    }
}