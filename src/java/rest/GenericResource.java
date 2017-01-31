package rest;

import bd.Autobus;
import bd.Conexion;
import bd.Posicion;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Lluis_2
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of rest.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarAutobuses() {

        Conexion conexion = new Conexion();
        List<Autobus> autobuses = null;
        try {
            autobuses = conexion.obtenerAutobuses();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();
        return gson.toJson(autobuses);
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarPosicion(String pos) {
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Posicion posicion;
        posicion = gson.fromJson(pos, Posicion.class);
        boolean result = true;
        try {

            conexion.insertarPosiciones(posicion);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

    @GET
    @Path("matricula/{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public String mostrarPosicion(@PathParam("matricula") String matricula) {
        Posicion pos = null;
        Conexion conexion = new Conexion();
        try {
            pos = conexion.obtenerPosicion(matricula);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return gson.toJson(pos);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean actualizarPosicion(String pos) {
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Posicion posicion;
        posicion = gson.fromJson(pos, Posicion.class);
        boolean result = true;
        try {
            conexion.actualizarPosicion(posicion);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;

        }
        return result;
    }

//    @DELETE
//    @Path("/delete/{id}")
//
//    public boolean eliminarCliente(@PathParam("id") int id) {
//        Conexion conexion = new Conexion();
//        boolean result = true;
//
//        try {
//
//            conexion.eliminarCliente(id);
//        } catch (SQLException ex) {
//            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
//            result = false;
//
//        }
//        return result;
//    }
}
