package Services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import Class.Bike;
import Class.GustaveUser;
import Repositories.BikeRepo;

@Path("/bikes")



public class BikeService {
	
	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBikes() {
        Map<Long, Bike> allBikes = BikeRepo.afficherTous();
        return Response.ok(allBikes).build();
    }

    
    
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBikeById(@PathParam("id") Long id) {
	    // Récupérer le vélo par ID depuis BikeRepo
	    Bike bike = BikeRepo.afficherParId(id);

	    // Vérifier si le vélo existe
	    if (bike != null) {
	        // Calculer et afficher le prix de location (facultatif)
	        System.out.println(BikeRepo.calculateRentalPrice(bike));

	        // Retourner une réponse avec le vélo trouvé
	        return Response.ok(bike).build();
	    } else {
	        // Retourner une réponse 404 si le vélo n'existe pas
	        return Response.status(Response.Status.NOT_FOUND)
	                .entity("Le vélo avec l'ID " + id + " n'existe pas.")
	                .build();
	    }
	}

    @POST
    @Path("/rent/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rentBike(@PathParam("id") Long id) {
        boolean rented = BikeRepo.louer(id);
        if (rented) {
            return Response.ok("Vélo loué avec succès !").build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Le vélo n'est pas disponible actuellement.").build();
        }
    }

    @POST
    @Path("/return/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response returnBike(@PathParam("id") Long id, String notes) {
        // Retourner le vélo et ajouter la note
        BikeRepo.retourner(id, notes);
        return Response.ok("Vélo retourné avec succès et note ajoutée !").build();
    }

    
    
    
    
    @POST
    @Path("/waitlist/{idbike}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToWaitlist(@PathParam("idbike") Long id, @PathParam("userId") Long userId) {

        try {
            // Faire une requête GET à l'API "intern" pour récupérer l'utilisateur
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/InternalService/intern/gustaveUsers/" + userId))
                .GET()
                .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            
            
            if (response.statusCode() == 404) {
                return Response.status(Response.Status.NOT_FOUND)
                             .entity("L'utilisateur avec l'ID " + userId + " n'a pas été trouvé.").build();
            } else if (response.statusCode() != 200) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                             .entity("Erreur lors de la récupération de l'utilisateur depuis le service intern.").build();
            }

            // Convertir la réponse JSON en objet GustaveUser
            GustaveUser user = convertJsonToGustaveUser(response.body());
            
            
            System.out.print("ENNNNNNAAAAAAA USER  \n " + user+  " \n" );

            // Ajouter l'utilisateur à la liste d'attente
            boolean added = BikeRepo.ajouterALaListeDAttente(id, user);
            if (added) {
                return Response.ok("Utilisateur ajouté à la liste d'attente.").build();
            } else {
                return Response.status(Response.Status.CONFLICT)
                             .entity("L'utilisateur est déjà dans la liste d'attente ou le vélo n'existe pas.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Erreur lors de l'ajout à la liste d'attente: " + e.getMessage()).build();
        }
    }

    
    
    // Méthode pour convertir une chaîne JSON en objet GustaveUser (exemple utilisant Jackson)
    private GustaveUser convertJsonToGustaveUser(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, GustaveUser.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    @DELETE
    @Path("/waitlist/{idbike}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeFromWaitlist(@PathParam("idbike") Long id,  @PathParam("userId") Long userId) {
        boolean removed = BikeRepo.retirerDeLaListeDAttente(id, userId);
        if (removed) {
            return Response.ok("Utilisateur retiré de la liste d'attente.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("L'utilisateur n'est pas dans la liste d'attente ou le vélo n'existe pas ou vélo deja disponible.").build();
        }
    }

}
