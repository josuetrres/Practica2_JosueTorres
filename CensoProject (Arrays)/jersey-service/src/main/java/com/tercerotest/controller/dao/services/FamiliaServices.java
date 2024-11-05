package com.tercerotest.controller.dao.services;

import com.google.gson.Gson;
import com.tercerotest.controller.dao.implement.FamiliaDao;
import com.tercerotest.controller.dao.implement.RegistroDao;
import com.tercerotest.controller.tda.Censo;
import com.tercerotest.controller.tda.Familia;
import com.tercerotest.controller.tda.Array;
import com.tercerotest.controller.tda.Registro;

public class FamiliaServices {
    private FamiliaDao obj;
    private RegistroDao registroDao;

    public FamiliaServices() {
        this.obj = new FamiliaDao();
        this.registroDao = new RegistroDao();
    }

    public Boolean save() throws Exception {
        registroDao.registrarEvento("Familia", "Creación de familia con ID: " + obj.getFamilia().getId());
        return this.obj.save();
    }

    public Array<Familia> listAll() {
        return this.obj.listAll();
    }

    public Familia getFamilia() {
        return this.obj.getFamilia();
    }

    public void setFamilia(Familia familia) {
        this.obj.setFamilia(familia);
    }

    public FamiliaDao getFamiliaDao() {
        return this.obj;
    }

    public Familia get(Integer id) throws Exception{
        registroDao.registrarEvento("Familia", "Obtención de familia con ID: " + obj.getFamilia().getId());
        
        return (Familia) obj.get(id);
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

     public String toJson() {
        Array<Familia> familias = listAll(); 
        return new Gson().toJson(familias); 
    }

    public void deleteFamilia(Integer id) throws Exception {
        registroDao.registrarEvento("Familia", "Eliminación de familia con ID: " + obj.getFamilia().getId());
        obj.deleteFamilia(id);
    }

}
