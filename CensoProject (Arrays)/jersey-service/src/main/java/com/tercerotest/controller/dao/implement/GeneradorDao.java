package com.tercerotest.controller.dao.implement;

import com.tercerotest.controller.tda.Generador;
import com.tercerotest.controller.tda.Array;

public class GeneradorDao extends AdapterDao {
     private Generador generador;
    private Array<Generador> listAll;

    public GeneradorDao() {
        super(Generador.class);
    }

    public void setGenerador(Generador generador) {
        this.generador = generador;
    }

    public Generador getGenerador() {
        if (this.generador == null) {
            this.generador = new Generador();
        }
        return this.generador;
    }

    public Array<Generador> getListAll() {
        if (this.listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        generador.setId(id); 
        this.persist(this.generador);
        return true;
    }

    public Boolean update() throws Exception {
        Integer index = getGenerador().getId() - 1;
    
        if (index < 0 || index >= getListAll().getSize()) {
            throw new Exception("Índice de Generador inválido");
        }
    
        this.merge(getGenerador(), index);
        this.listAll = listAll();
    
        return true;
    }

    public void deleteGenerador(Integer id) throws Exception {
        // Asegúrate de que listAll esté inicializado
        if (listAll == null) {
            this.listAll = listAll(); // Inicializa si es necesario
        }
    
        // Buscar el índice del generador con el ID proporcionado
        for (int i = 0; i < listAll.getSize(); i++) {
            Generador generador = listAll.get(i); // Obtener el generador en la posición i
            if (generador.getId().equals(id)) {
                // Si se encuentra el generador, eliminarlo usando el método delete
                listAll.delete(i);
                return; // Salir del método después de eliminar
            }
        }
    
        // Si no se encontró, lanzar una excepción
        throw new Exception("Generador no encontrado");
    }
    
}
