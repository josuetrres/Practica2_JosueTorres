package com.tercerotest.controller.dao.implement;

import com.tercerotest.controller.tda.Familia;
import com.tercerotest.controller.tda.LinkedList;

public class FamiliaDao extends AdapterDao{
    private Familia familia;
    private LinkedList<Familia> listAll;

    public FamiliaDao() {
        super(Familia.class);
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Familia getFamilia() {
        if(this.familia == null) {
            this.familia = new Familia();
        }
        return this.familia;
    }

    public LinkedList<Familia> getListAll() {
        if(listAll == null){
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        familia.setId(id);
        this.persist(this.familia);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        Integer index = getFamilia().getId() - 1;
    
        if (index < 0 || index >= getListAll().getSize()) {
            throw new Exception("Índice de Familia inválido");
        }
    
        this.merge(getFamilia(), index);
        this.listAll = listAll();
    
        return true;
    }
    
    public void deleteFamilia(Integer id) throws Exception {
        // Asegúrate de que listAll esté inicializado
        if (listAll == null) {
            this.listAll = listAll(); // Inicializa si es necesario
        }
    
        // Buscar el índice de la familia con el ID proporcionado
        for (int i = 0; i < listAll.getSize(); i++) {
            Familia familia = listAll.get(i); // Obtener la familia en la posición i
            if (familia.getId().equals(id)) {
                // Si se encuentra la familia, eliminarla usando el método delete
                listAll.delete(i);
                return; // Salir del método después de eliminar
            }
        }
    
        // Si no se encontró, lanzar una excepción
        throw new Exception("Familia no encontrada");
    }
    
}
