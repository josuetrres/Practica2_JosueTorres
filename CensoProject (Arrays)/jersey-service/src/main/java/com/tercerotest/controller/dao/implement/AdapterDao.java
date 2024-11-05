package com.tercerotest.controller.dao.implement;

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.tercerotest.controller.tda.Array;

public class AdapterDao<T> implements InterfazDao<T> {
    private Class clazz;
    private Gson g;
    public static String URL = "media/";

    public AdapterDao(Class clazz) {
        this.clazz = clazz;
        g = new Gson();
    }

    public T get(Integer id) throws Exception {
        Array<T> list = listAll();
        if (!list.isEmpty()) {
            T [] matriz = list.toArray();
            return matriz[id - 1];
            
        }
        return null;
    }
    

    public Array listAll() {
        Array list = new Array<>();

        try {
           String data = readFile();
           T[] matrix = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
           list.toList(matrix);
        } catch (Exception e) {
            

        }
        return list;
    }

    public void merge(T object, Integer index) throws Exception {
        Array<T> list = listAll();
        list.update(object, index);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }
    
    public void persist(T object) throws Exception {
        Array list = listAll();
        list.add(object);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }

    private String readFile() throws Exception {
        File file = new File(URL + clazz.getSimpleName() + ".json");

        if (!file.exists()) {
            System.out.println("El archivo no existe, creando uno nuevo: " + file.getAbsolutePath());
            saveFile("[]");
        }

        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner(new FileReader(file))) {
            while (in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
        }
        return sb.toString().trim();
    }


    private void saveFile(String data) throws Exception {
        File file = new File(URL + clazz.getSimpleName() + ".json");
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            System.out.println("Creando el archivo JSON: " + file.getAbsolutePath());
            file.createNewFile();
        }

        try (FileWriter f = new FileWriter(file)) {
            f.write(data);
            f.flush();
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    
}

