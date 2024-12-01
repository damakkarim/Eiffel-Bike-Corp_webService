package Services;


import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Class.ExternalUser;
import Repositories.ExternalUserRepo;



@Path("/ExternalUsers")
public class ExternalUserService {
	
	
	
	
	 // Méthode pour enregistrer un utilisateur
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(ExternalUser user) {
        // Vérification des champs obligatoires
        if (user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Tous les champs (nom, email, mot de passe) sont obligatoires.")
                           .build();
        }

        // Vérifier si l'email est déjà pris
        if (ExternalUserRepo.getUserByEmail(user.getEmail()) != null) {
            return Response.status(Response.Status.CONFLICT)
                           .entity("Cet email est déjà utilisé.")
                           .build();
        }

        // Enregistrer l'utilisateur
        ExternalUser registeredUser = ExternalUserRepo.register(user);
        return Response.ok(registeredUser).build();
    }

    
    
    
    
    
    
    
    
    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(ExternalUser user) {
        try {
            ExternalUser authenticatedUser = ExternalUserRepo.signIn(user.getEmail(), user.getPassword());
            return Response.ok(authenticatedUser).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage()).build();
        }
    }
    
    
    
    
    

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        System.out.println("ID received: " + id); // Debugging line
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID is null").build();
        }
        
        ExternalUser user = ExternalUserRepo.getUserById(id);
        if (user != null) {
            return Response.ok(user).build(); // Renvoie l'utilisateur en JSON
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build(); // Renvoie un code 404 si l'utilisateur n'existe pas
        }
    }

    


    
    
    
    
    // Méthode pour récupérer tous les utilisateurs
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.ok(ExternalUserRepo.getAll()).build();
    }
    
    
    
    
    
    
    
    
    
    

}
