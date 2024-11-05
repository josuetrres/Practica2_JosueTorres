package com.example.rest;


import java.util.HashMap;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.tercerotest.controller.dao.services.RegistroServices;
import com.tercerotest.controller.tda.LinkedList;
import com.tercerotest.controller.tda.Registro;


@Path("historial")
public class RegistroApi {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRegistros() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        RegistroServices registroServices = new RegistroServices(); // Usar RegistroServices
        LinkedList<Registro> registros = registroServices.listAll(); // Obtener todos los registros
        
        map.put("msg", "OK");
        map.put("data", registros.toArray()); // Convertir a array para la respuesta

        if (registros.isEmpty()) {
            map.put("data", new Object[]{}); // Asegurarse de que sea un array vacío si no hay registros
        }

        return Response.ok(map).build(); // Construir la respuesta
    }
}