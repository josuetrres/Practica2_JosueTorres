package com.tercerotest.controller.dao.implement;

import java.time.LocalDateTime;

import com.tercerotest.controller.tda.LinkedList;
import com.tercerotest.controller.tda.Registro;

public class RegistroDao extends AdapterDao {
    private Registro registro;
    private LinkedList<Registro> listAll;

    public RegistroDao() {
        super(Registro.class);

    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public Registro getRegistro() {
        if (this.registro == null) {
            this.registro = new Registro();
        }
        return this.registro;
    }

    public LinkedList<Registro> getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1; // Asignar un nuevo ID
        registro.setId(id);
        this.persist(this.registro); // Persistir el nuevo registro
        this.listAll = listAll(); // Actualizar la lista
        return true;
    }

    public Boolean update() throws Exception {
        Integer index = getRegistro().getId() - 1; // Obtener el índice correspondiente al ID

        if (index < 0 || index >= getListAll().getSize()) {
            throw new Exception("Índice de Registro inválido");
        }
        this.merge(getRegistro(), index); // Actualizar el registro en la lista
        this.listAll = listAll(); // Volver a cargar la lista
        return true;
    }

    public void deleteRegistro(Integer id) throws Exception {
        if (listAll == null) {
            this.listAll = listAll(); // Inicializa si es necesario
        }

        // Buscar el índice del registro con el ID proporcionado
        for (int i = 0; i < listAll.getSize(); i++) {
            Registro registro = listAll.get(i); // Obtener el registro en la posición i
            if (registro.getId().equals(id)) {
                // Si se encuentra el registro, eliminarlo usando el método delete
                listAll.delete(i);
                return; // Salir del método después de eliminar
            }
        }

        // Si no se encontró, lanzar una excepción
        throw new Exception("Registro no encontrado");
    }

    public void registrarEvento(String accion, String descripcion) throws Exception {
        Registro registro = new Registro();
        registro.setNombre(accion);
        registro.setDescripcion(descripcion);
        this.persist(registro); // Persistir el registro del evento
    }
}
