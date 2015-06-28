/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinematographer.rest.services;

import com.cinematographer.core.manager.IServiceManager;
import com.cinematographer.core.manager.ServiceManager;
import com.cinematographer.core.reservation.IReservationService;
import com.cinematographer.core.reservation.Reservation;
import com.cinematographer.core.reservation.ReservationDTO;
import com.cinematographer.core.reservation.ReservationException;
import com.cinematographer.core.utils.JsonUtils;
import com.cinematographer.rest.utils.ResponseHelper;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Aleksandar
 */
@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationRestAPI {
    private IServiceManager getServiceManager() {
		return ServiceManager.getInstance();
	}
    
    private IReservationService getReservationService() {
		return getServiceManager().findService(IReservationService.class);
	}
    
    @POST
    public Response reserveSeats(String payload){
        try{
        IReservationService service = getReservationService();
        
        ReservationDTO reservation =  JsonUtils.fromJson(payload, ReservationDTO.class);
        
        service.reserveSeats(reservation.getUsername(),reservation.getTitle(),
                               reservation.getSeats());        
        return ResponseHelper.createResponse(Response.Status.CREATED);
        }
        catch(ReservationException e){
            return ResponseHelper.createResponse(Response.Status.CONFLICT, e);
        }
        catch(Exception e){
            return ResponseHelper.createResponse(Response.Status.INTERNAL_SERVER_ERROR, e);
        }        
    }
    
    @GET
    @Path("/{username}")
    public Response getActiveReservations(@PathParam("username") String username){
        try{
            IReservationService service = getReservationService();
            Collection<Reservation> reservations = service.getActiveReservations(username);
            
            String json = JsonUtils.toJson(reservations);
            return ResponseHelper.createResponse(Response.Status.OK, json);            
        }
        catch(Exception e){
            return ResponseHelper.createResponse(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }
    
    @GET
    @Path("/{username}")
    public Response getHistoricReservations (@PathParam("username") String username){
        try{
           IReservationService service = getReservationService();
           Collection <Reservation> reservations = service.getHistoricReservations(username);
           
           String json = JsonUtils.toJson(reservations);
           return ResponseHelper.createResponse(Response.Status.OK, json);
        }
        catch(Exception e){
            return ResponseHelper.createResponse(Response.Status.INTERNAL_SERVER_ERROR, e);
        }        
    }
    
    @POST
    public Response payReservation(String payload){
        try{
            IReservationService service = getReservationService();
            ReservationDTO reservation =  JsonUtils.fromJson(payload, ReservationDTO.class);
            
            service.payReservation(reservation.getUsername(), reservation.getTitle());
            return ResponseHelper.createResponse(Response.Status.ACCEPTED);
        }
        catch(ReservationException e){
            return ResponseHelper.createResponse(Response.Status.SERVICE_UNAVAILABLE, e);
        }
        catch(Exception e){
            return ResponseHelper.createResponse(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }
}
