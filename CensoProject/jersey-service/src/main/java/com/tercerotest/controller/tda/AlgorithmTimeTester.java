package com.tercerotest.controller.tda;

import java.util.Random;

public class AlgorithmTimeTester {

    
    static Numero[] crearArreglo(int size) {
        Numero[] arr = new Numero[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = new Numero(random.nextInt(1000000)); 
        }
        return arr;
    }

    
    static Long medirTiempoOrden(Numero[] arregloOriginal, String metodo, Integer tipo) throws Exception {
        
        LinkedList<Numero> lista = new LinkedList<>();
        lista = lista.toList(arregloOriginal);

        Long start = System.nanoTime();
        switch (metodo) {
            case "shell":
                lista.shellOrder("value", tipo);
                break;
            case "merge":
                lista.mergeOrder("value", tipo);
                break;
            case "quick":
                lista.quickOrder("value", tipo);
                break;
            default:
                throw new IllegalArgumentException("Método no válido: " + metodo);
        }
        Long end = System.nanoTime();
        return end - start;
    }


    static Long medirTiempoBusqueda(LinkedList<Numero> lista, String metodo, Numero objetivo) throws Exception {
       
        Long start = System.nanoTime();
        switch (metodo) {
            case "linear":
                lista.linearSearch("value", String.valueOf(objetivo.getValue()));
                break;
            case "binary":
            
                lista.binarySearch("value", String.valueOf(objetivo.getValue()));
                break;
            default:
                throw new IllegalArgumentException("Método no válido: " + metodo);
        }
        Long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) {
        try {
            int[] tamanios = {10000, 20000, 25000}; 
            String[] algoritmos = {"shell", "merge", "quick"}; 
            String[] busquedas = {"linear", "binary"}; 

            for (int tamanio : tamanios) {
                System.out.println("----- Pruebas para tamaño: " + tamanio + " -----");
                Numero[] arregloOriginal = crearArreglo(tamanio);

                for (String algoritmo : algoritmos) {
                    Long tiempoOrden = medirTiempoOrden(arregloOriginal, algoritmo, 1); 
                    System.out.println(algoritmo.toUpperCase() + " Sort: " + tiempoOrden + " ns");
                }

                LinkedList<Numero> lista = new LinkedList<>();
                lista = lista.toList(arregloOriginal);
                lista = lista.quickOrder("value", 1);
                Random random = new Random();
                Numero objetivo = lista.get(random.nextInt(lista.getSize())); 
                System.out.println("Buscando el valor: " + objetivo.getValue());

                Long tiempoBinary = medirTiempoBusqueda(lista, "binary", objetivo);
                System.out.println("Binary Search: " + tiempoBinary + " ns");

                Long tiempoLinear = medirTiempoBusqueda(lista, "linear", objetivo);
                System.out.println("Linear Search: " + tiempoLinear + " ns");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
