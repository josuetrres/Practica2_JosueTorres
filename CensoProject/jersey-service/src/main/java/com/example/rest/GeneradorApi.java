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

import com.google.gson.Gson;
import com.tercerotest.controller.dao.services.CensoServices;
import com.tercerotest.controller.dao.services.FamiliaServices;
import com.tercerotest.controller.dao.services.GeneradorServices;
import com.tercerotest.controller.tda.Censo;
import com.tercerotest.controller.tda.Familia;
import com.tercerotest.controller.tda.Generador;
import com.tercerotest.controller.tda.LinkedList;

@Path("generador")
public class GeneradorApi {
    
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGeneradores() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        GeneradorServices gs = new GeneradorServices();
        map.put("msg", "OK");
        map.put("data", gs.listAll().toArray());
        if (gs.listAll().isEmpty()){
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveGenerador(String json) {
        String jsonResponse = "";
        GeneradorServices gs = new GeneradorServices(); // Asegúrate de que GeneradorServices esté correctamente implementado
        Gson gson = new Gson();
        
        try {
            // Deserializa el JSON en un objeto Generador
            Generador generador = gson.fromJson(json, Generador.class);
    
            // Guarda el generador utilizando el servicio
            gs.setGenerador(generador); // Suponiendo que tienes un método para establecer el generador
            gs.save();  // Llama al método save, que maneja la asignación
    
            // Preparar respuesta de éxito
            jsonResponse = "{\"msg\":\"OK\",\"data\":\"Generador guardado correctamente\", \"info\":" + gs.toJson() + "}";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Preparar respuesta de error
            jsonResponse = "{\"msg\":\"ERROR\",\"data\":\"" + e.getMessage() + "\"}";
        }
        
        return Response.ok(jsonResponse).build();
    }


    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenerador(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        GeneradorServices gs = new GeneradorServices();
        
        try {
            gs.setGenerador(gs.get(id));
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        
        map.put("msg", "OK");
        map.put("data", gs.getGenerador());

        if (gs.getGenerador().getId() == null) {
            map.put("data", "No existe un generador con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        GeneradorServices gs = new GeneradorServices();
        
        try {
            Generador existingGenerador = gs.get(Integer.parseInt(map.get("id").toString()));
            
            if (existingGenerador == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe un generador con ese identificador");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
            
            existingGenerador.setMarca(map.get("marca").toString());
            existingGenerador.setPotenciaGenerada(Float.parseFloat(map.get("potenciaGenerada").toString()));
            existingGenerador.setCosto(Float.parseFloat(map.get("costo").toString()));
            existingGenerador.setConsumoPorHora(Float.parseFloat(map.get("consumoPorHora").toString()));
            
            gs.setGenerador(existingGenerador);
            gs.update();
            
            res.put("msg", "OK");
            res.put("data", "Generador actualizado correctamente");
            return Response.ok(res).build();
            
        } catch (Exception e) {
            System.out.println("Error al actualizar el generador: " + e.toString());
            res.put("msg", "ERROR");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteGenerador(@PathParam("id") Integer id) {
        String jsonResponse = "";
        GeneradorServices gs = new GeneradorServices();

        try {
            gs.deleteGenerador(id); // Llama al método de eliminación de generadores
            jsonResponse = "{\"data\":\"Generador eliminado!\"}";
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"data\":\"Error\",\"info\":\"" + e.getMessage() + "\"}";
        }

        return Response.ok(jsonResponse).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mergeOrder/{attribute}/{type}")
    public Response mergeOrder(@PathParam("attribute") String attribute, @PathParam("type") Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        GeneradorServices gs = new GeneradorServices();
        
        try {
            map.put("msg", "OK");
            LinkedList<Generador> listita = gs.mergeOrder(attribute, type);
            map.put("data", listita.toArray());
            if (listita.isEmpty()){
                map.put("data", new Object[]{});
            }
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        
        return Response.ok(map).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/quickOrder/{attribute}/{type}")
    public Response quickOrder(@PathParam("attribute") String attribute, @PathParam("type") Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        GeneradorServices gs = new GeneradorServices();
        
        try {
            map.put("msg", "OK");
            LinkedList<Generador> listita = gs.quickOrder(attribute, type);
            map.put("data", listita.toArray());
            if (listita.isEmpty()){
                map.put("data", new Object[]{});
            }
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        
        return Response.ok(map).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/shellOrder/{attribute}/{type}")
    public Response shellOrder(@PathParam("attribute") String attribute, @PathParam("type") Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        GeneradorServices gs = new GeneradorServices();
        
        try {
            map.put("msg", "OK");
            LinkedList<Generador> listita = gs.shellOrder(attribute, type);
            map.put("data", listita.toArray());
            if (listita.isEmpty()){
                map.put("data", new Object[]{});
            }
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        
        return Response.ok(map).build();
    }

    @Path("/linearSearch/{attribute}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response linearSearch(@PathParam("attribute") String attribute, @PathParam("value") String value) {
        HashMap map = new HashMap<>();
        GeneradorServices gs = new GeneradorServices();
        try{
            map.put("msg", "OK");
            LinkedList<Generador> listita = gs.linearSearch(attribute, value);
            map.put("data", listita.toArray());
            if (listita.isEmpty()) {
                map.put("data", new Object[] {});
        }
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/binarySearch/{attribute}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response binarySearch(@PathParam("attribute") String attribute, @PathParam("value") String value) {
        HashMap map = new HashMap<>();
        GeneradorServices gs = new GeneradorServices();
        try{
            map.put("msg", "OK");
            LinkedList<Generador> listita = gs.quickOrder(attribute, 1);
            listita = listita.binarySearch(attribute, value);
            map.put("data", listita.toArray());
            if (listita.isEmpty()) {
                map.put("data", new Object[] {});
        }
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

        return Response.ok(map).build();
    }
    
}
