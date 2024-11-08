package Services;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Classes.Bike;
import Repositories.BikeRepo;

@Path("/api/bikes")
public class BikeService {
    
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAllBikes() {
        try {
            Map<Long, Bike> allBikes = BikeRepo.afficherTous();
            return Response.ok(allBikes).build(); 
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erreur lors de la récupération des vélos : " + e.getMessage())
                           .build();
        }
    }
}
