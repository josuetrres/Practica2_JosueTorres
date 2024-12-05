package com.example.rest;

import java.util.HashMap;
import com.tercerotest.controller.tda.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.tercerotest.controller.dao.services.FamiliaServices;
import com.tercerotest.controller.tda.Familia;

@Path("familia")
public class FamiliaApi {
    
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFamilias() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServices fs = new FamiliaServices();
        map.put("msg", "OK");
        map.put("data", fs.listAll().toArray());
        if (fs.listAll().isEmpty()){
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveFamilia(String json) {
        String jsonResponse = "";
        FamiliaServices fs = new FamiliaServices();
        Gson gson = new Gson();
        
        try {
            // Deserializa el JSON en un objeto Familia
            Familia familia = gson.fromJson(json, Familia.class);
    
            // Guarda la familia utilizando el servicio
            fs.setFamilia(familia); 
            fs.save();  // Llama al método save, que maneja la asignación
    
            // Preparar respuesta de éxito
            jsonResponse = "{\"msg\":\"OK\",\"data\":\"Familia guardada correctamente\", \"info\":" + fs.toJson() + "}";
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
    public Response getFamilia(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServices fs = new FamiliaServices();
        
        try {
            fs.setFamilia(fs.get(id));
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        
        map.put("msg", "OK");
        map.put("data", fs.getFamilia());

        if (fs.getFamilia().getId() == 0) { 
            map.put("data", "No existe una familia con ese identificador");
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
        FamiliaServices fs = new FamiliaServices();
        
        try {
            Familia existingFamilia = fs.get(Integer.parseInt(map.get("id").toString()));
            
            if (existingFamilia == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe una familia con ese identificador");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
            
            existingFamilia.setTieneGenerador(Boolean.parseBoolean(map.get("tieneGenerador").toString()));
            existingFamilia.setCantidadPersonas(Integer.parseInt(map.get("cantidadPersonas").toString()));
            existingFamilia.setRazonUso(map.get("razonUso").toString());
            
            fs.setFamilia(existingFamilia);
            fs.update();
            
            res.put("msg", "OK");
            res.put("data", "Familia actualizada correctamente");
            return Response.ok(res).build();
            
        } catch (Exception e) {
            System.out.println("Error al actualizar la familia: " + e.toString());
            res.put("msg", "ERROR");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteFamilia(@PathParam("id") Integer id) {
        String jsonResponse = "";
        FamiliaServices fs = new FamiliaServices();

        try {
            fs.deleteFamilia(id); // Llama al método de eliminación de familias
            jsonResponse = "{\"data\":\"Familia eliminada!\"}";
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
        FamiliaServices fs = new FamiliaServices();
        
        try {
            map.put("msg", "OK");
        
            LinkedList<Familia> listita = fs.mergeOrder(attribute, type);
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
        FamiliaServices fs = new FamiliaServices();
        
        try {
            map.put("msg", "OK");
            LinkedList<Familia> listita = fs.quickOrder(attribute, type);
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
        FamiliaServices fs = new FamiliaServices();
        
        try {
            map.put("msg", "OK");
            LinkedList<Familia> listita = fs.shellOrder(attribute, type);
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
        FamiliaServices fs = new FamiliaServices();
        try{
            map.put("msg", "OK");
            LinkedList<Familia> listita = fs.linearSearch(attribute, value);
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
        FamiliaServices fs = new FamiliaServices();
        try{
            map.put("msg", "OK");
            LinkedList<Familia> listita = fs.quickOrder(attribute, 1);
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

