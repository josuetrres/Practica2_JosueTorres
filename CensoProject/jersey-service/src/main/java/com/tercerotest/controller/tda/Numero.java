package com.tercerotest.controller.tda;

public class Numero {
    private int value;

    
    public Numero(int value) {
        this.value = value;
    }

    // Getter
    public int getValue() {
        return value;
    }


    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
