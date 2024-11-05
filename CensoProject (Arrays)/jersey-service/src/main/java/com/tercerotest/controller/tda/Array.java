package com.tercerotest.controller.tda;

import com.tercerotest.controller.exception.ArregloException;

public class Array<T> {
    private T[] elementos;
    private transient int tamaño;
    private transient int capacidad;

    public Array() {
        this.capacidad = 0;
        this.elementos = (T[]) new Object[capacidad];
        this.tamaño = 0;
    }

    public Boolean isEmpty (){
        return tamaño == 0;
    }

    public int getSize() {
        return tamaño;
    }

    public void reset() {
        elementos = (T[]) new Object[capacidad];
        tamaño = 0;
    }

    public void add(T elemento) {
        if (tamaño == capacidad) {
            redimensionar();
        }
        elementos[tamaño++] = elemento;
    }

    public void addbyId(T elemento, int indice) throws ArregloException {
        if (indice >= 0 && indice <= tamaño) {
            if (tamaño == capacidad) {
                redimensionar();
            }
            System.arraycopy(elementos, indice, elementos, indice + 1, tamaño - indice);
            elementos[indice] = elemento;
            tamaño++;
        } else {
            throw new ArregloException("Índice fuera de los límites");
        }
    }

    private void redimensionar() {
        capacidad += 1;
        T[] nuevoArreglo = (T[]) new Object[capacidad];
        System.arraycopy(elementos, 0, nuevoArreglo, 0, tamaño);
        elementos = nuevoArreglo;
    }

    public T get(int indice) throws ArregloException {
        if (indice >= 0 && indice < tamaño) {
            return elementos[indice];
        } else {
            throw new ArregloException("Índice fuera de los límites");
        }
    }

    public void update(T elemento, int indice) throws ArregloException {
        if (indice >= 0 && indice < tamaño) {
            elementos[indice] = elemento;
        } else {
            throw new ArregloException("Índice fuera de los límites");
        }
    }

    public void delete(int indice) throws ArregloException {
        if (indice >= 0 && indice < tamaño) {
            System.arraycopy(elementos, indice + 1, elementos, indice, tamaño - indice - 1);
            elementos[--tamaño] = null;
        } else {
            throw new ArregloException("Índice fuera de los límites");
        }
    }

    public  T[] toArray(){
        T[] array = (T[]) new Object[tamaño];
        System.arraycopy(elementos, 0, array, 0, tamaño);
        return array;
    }   

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < tamaño; i++) {
            sb.append(elementos[i]);
            if (i < tamaño - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public T[] getElementos() {
        return elementos;
    }

    public void setElementos(T[] elementos) {
        this.elementos = elementos;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setSize(int tamaño) {
        this.tamaño = tamaño;
    }

    public void toList(T[] matrix) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toList'");
    }

    
}

