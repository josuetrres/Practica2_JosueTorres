package com.tercerotest.controller.dao.implement;
import com.tercerotest.controller.tda.Array;


public interface InterfazDao <T> {
    public void persist(T object) throws Exception;
    public void merge(T object, Integer index) throws Exception;
    public Array listAll();
    public T get(Integer ID) throws Exception;
}


