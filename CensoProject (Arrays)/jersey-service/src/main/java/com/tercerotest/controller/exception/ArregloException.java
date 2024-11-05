package com.tercerotest.controller.exception;

public class ArregloException extends Exception {
    // Constructor que recibe un mensaje
    public ArregloException(String message) {
        super(message); // Llama al constructor de la clase padre con el mensaje
    }

    // Constructor que recibe un mensaje y una causa
    public ArregloException(String message, Throwable cause) {
        super(message, cause); // Llama al constructor de la clase padre con el mensaje y la causa
    }
}
