package com.tercerotest.controller.tda;

import com.google.gson.Gson;
import com.tercerotest.controller.exception.ListEmptyException;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;


public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;

    public LinkedList(){
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public boolean isEmpty(){
        return (this.header == null || this.size == 0);
    }

    public void addHeader (E dato){
        Node <E> help;
        if(isEmpty()){
            help =  new Node<>(dato);
            this.header = help;
        } else {
            Node<E> helpHeader = this.header;
            help = new Node<>(dato,helpHeader);
            this.header = help;
        }
        this.size++;
        
    }

    private void addLast(E info) {
        Node<E> help; 
        if (isEmpty()) { 
            help = new Node<>(info); 
            header = help; 
            last = help; 
        } else {
            help = new Node<>(info, null); 
            last.setNext(help); 
            last = help; 
        }
        this.size++; 
    }


    public void add(E info){
        addLast(info);
    }


    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException{
        if(isEmpty()){
            throw new ListEmptyException("Error, list empty");
        } else if(index < 0 || index >= this.size){
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if(index == 0){
            return header;
        } else if(index == (this.size -1)){
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while(cont < index){
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    @SuppressWarnings("unused")
    private E getFirst() throws IndexOutOfBoundsException, ListEmptyException{
        return this.getNode(0).getInfo();
    }

    @SuppressWarnings("unused")
    private E getLast() throws IndexOutOfBoundsException, ListEmptyException{
        return this.getNode(this.size-1).getInfo();
    }

    public E get(Integer index)throws IndexOutOfBoundsException, ListEmptyException{
        return this.getNode(index).getInfo();
    }

   

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        // Validación de índice fuera de los límites
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    
        if (isEmpty() || index == 0) {
            // Insertar al inicio de la lista
            addHeader(info);
        } else if (index == this.size) {
            // Insertar al final de la lista
            addLast(info);
        } else {
            // Obtener el nodo previo al índice donde se quiere insertar
            Node<E> search_preview = getNode(index - 1);
            // Crear un nuevo nodo que apunta al siguiente del nodo previo
            Node<E> help = new Node<>(info, search_preview.getNext());
            // Conectar el nodo previo al nuevo nodo
            search_preview.setNext(help);
            this.size++;
        }
    }
    
    /** Reset de la lista, eliminando todos los elementos */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
        // Liberar otros recursos si es necesario
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List Data \n");
        Node<E> help = header;
        // Recorrer la lista y generar una representación en String
        while (help != null) {
            sb.append(help.getInfo()).append(" -> ");
            help = help.getNext();
        }
        return sb.toString();
    }
    
    public Integer getSize() {
        return this.size;
    }
    
   public E[] toArray(){
        E[] matrix = null;
        if(!isEmpty()){
            Class<?> clazz = header.getInfo().getClass();
            matrix = (E[])java.lang.reflect.Array.newInstance(clazz, size);    
            Node<E> aux = header;
            for(int i = 0; i < size; i++){
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }  
         }
        return matrix;
   }
   
   public LinkedList<E> toList(E[] matrix){
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
   }

   public void update(E data, Integer post) throws Exception{
    if (isEmpty()) {
        throw new ListEmptyException("Error, lista vacia");
    }else if (post < 0 || post >= size){
        throw new IndexOutOfBoundsException("Error, fuera de rango");
    }else if (post == 0){
        header.setInfo(data);
    }else if (post == (size-1)){
        last.setInfo(data);
    }else{
        Node<E> search = header;
        Integer cont = 0;
        while (cont < post) {
            cont++;
            search = search.getNext();
        }
        search.setInfo(data);
    }
}

public E deleteFirst() throws ListEmptyException {
    if (isEmpty()) {
        throw new ListEmptyException("Error, lista vacia");
    } else {
        E element = header.getInfo();
        Node<E> aux = header.getNext();
        header = aux;
        if (size.intValue() == 1) {
            last = null;   
        }
        size--;
        return element;
    }
}

public E deleteLast() throws ListEmptyException {
    if (isEmpty()) {
        throw new ListEmptyException("Error, lista vacia");
    } else {
        E element = last.getInfo();
        Node<E> aux = getNode(size - 2);
        if (aux == null) {
            last = null;
            if (size == 2) {
                last = header;
            } else {
                header = null;
            }
        } else {
            last = null;
            last = aux;
            last.setNext(null);
        }
        size--;
        return element;
        }
}

public E delete(Integer post) throws Exception {
    if (isEmpty()) {
        throw new ListEmptyException("Error, lista vacia");
    } else if (post < 0 || post >= size) {
        throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
    } else if (post == 0) {
        return deleteFirst();
    } else if (post == (size - 1)) {
        return deleteLast();
    } else {
        Node<E> preview = getNode(post - 1);
        Node<E> actually = getNode(post);
        E element = actually.getInfo(); // Cambiar a actually.getInfo()
        Node<E> next = actually.getNext();
        preview.setNext(next); // Enlaza el nodo previo al siguiente
        size--;
        return element; // Retorna el elemento eliminado
    }
}


}

