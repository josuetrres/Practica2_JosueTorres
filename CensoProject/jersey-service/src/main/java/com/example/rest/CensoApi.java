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
import com.tercerotest.controller.tda.Censo;

@Path("censo")
public class CensoApi {
    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCensos() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        CensoServices cs = new CensoServices();
        map.put("msg", "OK");
        map.put("data",cs.listAll().toArray());
        if (cs.listAll().isEmpty()){
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }


    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCenso(String json) {
        String jsonResponse = "";
        CensoServices cs = new CensoServices();
        Gson gson = new Gson();
        
        try {
            // Deserializa el JSON en un objeto Censo
            Censo censo = gson.fromJson(json, Censo.class);
    
            // Guarda el censo utilizando el servicio
            cs.setCenso(censo); 
            cs.save();  // Llama al método save, que maneja la asignación
    
            // Preparar respuesta de éxito
            jsonResponse = "{\"msg\":\"OK\",\"data\":\"Censo guardado correctamente\", \"info\":" + cs.toJson() + "}";
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
    public Response getCenso(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        CensoServices cs = new CensoServices();
        
        try {
            cs.setCenso(cs.get(id));
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        
        map.put("msg", "OK");
        map.put("data", cs.getCenso());

        if (cs.getCenso().getId() == null) {
            map.put("data", "No existe un censo con ese identificador");
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
        CensoServices cs = new CensoServices();
        
        try {
            // Obtener el Censo existente por ID
            Censo existingCenso = cs.get(Integer.parseInt(map.get("id").toString()));
            
            if (existingCenso == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe un censo con ese identificador");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
            
            // Actualizar los campos del Censo
            existingCenso.setProvincia(map.get("provincia").toString());
            existingCenso.setTotalFamilias(Integer.parseInt(map.get("totalFamilias").toString()));
            
            // Actualizar el censo en el servicio
            cs.setCenso(existingCenso);
            cs.update(); // Suponiendo que tienes un método update en CensoServices
            
            res.put("msg", "OK");
            res.put("data", "Censo actualizado correctamente");
            return Response.ok(res).build();
            
        } catch (Exception e) {
            System.out.println("Error al actualizar el censo: " + e.toString());
            res.put("msg", "ERROR");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
            }
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteCenso(@PathParam("id") Integer id) {
        String jsonResponse = "";
        CensoServices cs = new CensoServices();

        try {
            cs.deleteCenso(id);
            jsonResponse = "{\"data\":\"Censo eliminado!\"}";
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"data\":\"Error\",\"info\":\"" + e.getMessage() + "\"}";
        }

        return Response.ok(jsonResponse).build();
    }
}



