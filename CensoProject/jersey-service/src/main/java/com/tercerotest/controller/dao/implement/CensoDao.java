package com.tercerotest.controller.dao.implement;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Line;

import com.tercerotest.controller.tda.Censo;
import com.tercerotest.controller.tda.LinkedList;

public class CensoDao extends AdapterDao  {
    private Censo censo;
    private LinkedList<Censo> listAll;

    public CensoDao() { 
        super(Censo.class);
    }
    

    public void setCenso(Censo censo) {
        this.censo = censo;
    }

    public Censo getCenso() {
        if (this.censo == null) {
            this.censo = new Censo();
        }
        return this.censo;
    }

    public LinkedList getListAll() {
        if(listAll == null){
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        censo.setId(id);
        this.persist(this.censo);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        Integer index = getCenso().getId() - 1;
    
        if (index < 0 || index >= getListAll().getSize()) {
            throw new Exception("Índice de Censo inválido");
        }
        this.merge(getCenso(), index);
        this.listAll = listAll();
        return true;
    }

    public void deleteCenso(Integer id) throws Exception {
        // Asegúrate de que listAll esté inicializado
        if (listAll == null) {
            this.listAll = listAll(); // Inicializa si es necesario
        }
    
        // Buscar el índice del censo con el ID proporcionado
        for (int i = 0; i < listAll.getSize(); i++) {
            Censo censo = listAll.get(i); // Obtener el censo en la posición i
            if (censo.getId().equals(id)) {
                // Si se encuentra el censo, eliminarlo usando el método delete
                listAll.delete(i);
                return; // Salir del método después de eliminar
            }
        }
    
        // Si no se encontró, lanzar una excepción
        throw new Exception("Censo no encontrado");
    }
    


    
    
    
}
