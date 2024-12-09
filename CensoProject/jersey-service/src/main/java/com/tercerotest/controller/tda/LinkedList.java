package com.tercerotest.controller.tda;

import com.google.gson.Gson;
import com.tercerotest.controller.exception.ListEmptyException;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
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

public E delete(Integer post) throws ListEmptyException {
    if (isEmpty()) {
        throw new ListEmptyException("Error, la lista esta vacia");
    } else if (post < 0 || post >= size) {
        throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
    } else if (post == 0) {
        return deleteFirst();
    } else if (post == (size - 1)) {
        return deleteLast();
    } else {
        Node<E> preview = getNode(post - 1);
        Node<E> actually = getNode(post);
        E element = preview.getInfo();
        Node<E> next = actually.getNext();
        actually = null;
        preview.setNext(next);
        size--;
        return element;
    }
}



private Boolean compare(Object a, Object b, Integer type) {
    switch (type) {
        case 0:
            if (a instanceof Number) {
                Number a1 = (Number) a;
                Number b1 = (Number) b;
                return a1.doubleValue() > b1.doubleValue();
            } else {
                // "casa" > "pedro"
                return (a.toString()).compareTo(b.toString()) > 0;
            }
            // break;

        default:
            // mayor a menor
            if (a instanceof Number) {
                Number a1 = (Number) a;
                Number b1 = (Number) b;
                return a1.doubleValue() < b1.doubleValue();
            } else {
                // "casa" > "pedro"
                return (a.toString()).compareTo(b.toString()) < 0;
            }
            // break;
    }

}

// compare class
private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
    return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
}

private Object exist_attribute(E a, String attribute) throws Exception {
    Method method = null;
    attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
    attribute = "get" + attribute;
    for (Method aux : a.getClass().getMethods()) {           
        if (aux.getName().contains(attribute)) {
            method = aux;
            break;
        }
    }
    if (method == null) {
        for (Method aux : a.getClass().getSuperclass().getMethods()) {              
            if (aux.getName().contains(attribute)) {
                method = aux;
                break;
            }
        }
    }
    if (method != null) {            
        return method.invoke(a);
    }
    
    return null;
}


public LinkedList<E> mergeOrder(String attribute, Integer type) throws Exception {
    if (!isEmpty()) {
        E[] array = this.toArray();
        array = mergeSort(array, attribute, type);
        reset(); 
        this.toList(array); 
    }
    return this;
}

private E[] createSubArray(E[] array, int start, int end) {
    @SuppressWarnings("unchecked")
    E[] subArray = (E[]) new Object[end - start];
    for (int i = start, j = 0; i < end; i++, j++) {
        subArray[j] = array[i]; 
    }
    return subArray;
}

private E[] mergeSort(E[] array, String attribute, Integer type) throws Exception {
    if (array.length <= 1) {
        return array;
    }

    int mid = array.length / 2;

    E[] left = createSubArray(array, 0, mid);
    E[] right = createSubArray(array, mid, array.length);


    left = mergeSort(left, attribute, type);
    right = mergeSort(right, attribute, type);

    return merge(left, right, attribute, type);
}

private E[] merge(E[] left, E[] right, String attribute, Integer type) throws Exception {
    @SuppressWarnings("unchecked")
    E[] result = (E[]) new Object[left.length + right.length];
    int i = 0, j = 0, k = 0;

    while (i < left.length && j < right.length) {
        if (!atrribute_compare(attribute, right[j],left[i], type)) {
            result[k++] = left[i++];
        } else {
            result[k++] = right[j++];
        }
    }

    while (i < left.length) {
        result[k++] = left[i++];
    }

    while (j < right.length) {
        result[k++] = right[j++];
    }

    return result;
}




public LinkedList<E> quickOrder(String attribute, Integer type) throws Exception {
    if (!isEmpty()) {
        E[] array = this.toArray(); 
        quickSort(array, 0, array.length - 1, attribute, type); 
        reset();
        this.toList(array); 
    }
    return this;
}

private void quickSort(E[] array, int low, int high, String attribute, Integer type) throws Exception {
    if (low < high) {
        int pivotIndex = partition(array, low, high, attribute, type); 
        quickSort(array, low, pivotIndex - 1, attribute, type); 
        quickSort(array, pivotIndex + 1, high, attribute, type); 
    }
}

//divide y reorganiza en torno al pivote(swap) y retorna la posición del pivote
private int partition(E[] array, int low, int high, String attribute, Integer type) throws Exception {
    E pivot = array[high]; 
    int i = low - 1; 

    for (int j = low; j < high; j++) {
        if (!atrribute_compare(attribute, pivot, array[j], type)) {
            i++;
            E temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    E temp = array[i + 1];
    array[i + 1] = array[high];
    array[high] = temp;

    return i + 1; 
}






public LinkedList<E> shellOrder(String attribute, Integer type) throws Exception {
    if (!isEmpty()) {
        E data = this.header.getInfo();
        if (data instanceof Object) {
            E[] lista = this.toArray();
            reset();
            shellSort(lista, attribute, type);  
            this.toList(lista);
        }
    }
    return this;
}

private void shellSort(E[] array, String attribute, Integer type) throws Exception {
    int n = array.length;

    for (int gap = n / 2; gap > 0; gap /= 2) {  
        for (int i = gap; i < n; i++) {
            E temp = array[i];
            int j = i;
            
            while (j >= gap && atrribute_compare(attribute,temp, array[j - gap], type)) {
                array[j] = array[j - gap];  
                j -= gap;  
            }
            array[j] = temp;  
        }
    }
}



public LinkedList<E> linearSearch(String attribute, String value) throws Exception {
    LinkedList<E> listita = new LinkedList<>();
    E[] array = this.toArray(); 

    for (int i = 0; i < array.length; i++) {
        E element = array[i];
        Object attributeValue = exist_attribute(element, attribute); 

        if (attributeValue != null) {
            String attributeValueStr = attributeValue.toString().toLowerCase(); 
            String valueStr = value.toLowerCase(); 

            if (attributeValueStr.startsWith(valueStr)) {
                listita.add(element); 
            }
        }
    }

    return listita;

}




public LinkedList<E> binarySearch(String attribute, String value) throws Exception {
    LinkedList<E> resultList = new LinkedList<>();
    E[] array = this.toArray();
    int low = 0;
    int high = array.length - 1;

    while (low <= high) {
        int mid = low + (high - low) / 2;
        E midElement = array[mid];
        Object attributeValue = exist_attribute(midElement, attribute);

        if (attributeValue != null) {
            String attributeValueStr = attributeValue.toString().toLowerCase();
            String valueStr = value.toLowerCase();

            if (attributeValue instanceof Number) {
                try {
                    Float attributeValueInt = Float.parseFloat(attributeValueStr);
                    Float valueInt = Float.parseFloat(valueStr);

                    if (attributeValueInt.equals(valueInt)) {
                        resultList.add(midElement);
                        break;
                    } else if (compare(attributeValueInt, valueInt, 0)) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                } catch (Exception e) {
                }
            } else {
                if (attributeValueStr.equals(valueStr)) {
                    resultList.add(midElement);
                    break;
                } else if (compare(attributeValueStr, valueStr, 0)) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
    }

    return resultList;
}
/*private void collectResults(E[] array, int mid, String attribute, String value, LinkedList<E> resultList) throws Exception {

    E midElement = array[mid];
    Object attributeValue = exist_attribute(midElement, attribute);
    String attributeValueStr = attributeValue.toString().toLowerCase();
    String valueStr = value.toLowerCase();
    

    if (attributeValueStr.startsWith(valueStr)) {
        resultList.add(midElement);
    }


    int left = mid - 1;
    while (left >= 0) {
        E leftElement = array[left];
        Object leftAttributeValue = exist_attribute(leftElement, attribute);
        String leftAttributeValueStr = leftAttributeValue.toString().toLowerCase();

        if (leftAttributeValueStr.startsWith(valueStr)) {
            resultList.add(leftElement);
            left--;
        } else {
            break;
        }
    }

    int right = mid + 1;
    while (right < array.length) {
        E rightElement = array[right];
        Object rightAttributeValue = exist_attribute(rightElement, attribute);
        String rightAttributeValueStr = rightAttributeValue.toString().toLowerCase();

        if (rightAttributeValueStr.startsWith(valueStr)) {
            resultList.add(rightElement);
            right++;
        } else {
            break;
        }
    }
}
*/
}

