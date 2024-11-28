package Services;


import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Class.GustaveUser;
import Repositories.GustaveUserRepo;

@Path("/gustaveUsers")
public class GustaveUserService {
	 // Méthode pour enregistrer un utilisateur
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(GustaveUser user) {
        // Vérification des champs obligatoires
        if (user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Tous les champs (nom, email, mot de passe) sont obligatoires.")
                           .build();
        }

        // Vérifier si l'email est déjà pris
        if (GustaveUserRepo.getUserByEmail(user.getEmail()) != null) {
            return Response.status(Response.Status.CONFLICT)
                           .entity("Cet email est déjà utilisé.")
                           .build();
        }

        // Enregistrer l'utilisateur
        GustaveUser registeredUser = GustaveUserRepo.register(user);
        return Response.ok(registeredUser).build();
    }
    

    
    
    
    
    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(GustaveUser user) {
        try {
            // Tentative de connexion
            GustaveUser authenticatedUser = GustaveUserRepo.signIn(user.getEmail(), user.getPassword());
            
            // Si l'authentification réussie, on renvoie l'utilisateur
            return Response.ok(authenticatedUser).build();
        } catch (IncorrectPasswordException e) {
            // Si le mot de passe est incorrect, on renvoie un message d'erreur spécifique
            String errorMessage = "{\"error\": \"Incorrect  password.\"}";
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity(errorMessage)  // Envoi du message d'erreur en JSON
                           .build();
        } catch (IllegalArgumentException e) {
            // Autres erreurs d'argument (par exemple, utilisateur non trouvé)
            String errorMessage = "{\"error\": \"" + e.getMessage() + "\"}";
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(errorMessage)  // Envoi du message d'erreur en JSON
                           .build();
        } catch (Exception e) {
            // Gestion des erreurs générales
            String errorMessage = "{\"error\": \"Une erreur interne est survenue.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(errorMessage)  // Envoi du message d'erreur en JSON
                           .build();
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
        
        GustaveUser user = GustaveUserRepo.getUserById(id);
        if (user != null) {
            return Response.ok(user).build(); // Renvoie l'utilisateur en JSON
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build(); // Renvoie un code 404 si l'utilisateur n'existe pas
        }
    }
        @GET
        @Path("/FindByEmail/{email}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getUserByEmail(@PathParam("email") String email) {
            System.out.println("Email received: " + email); // Ligne de débogage
            if (email == null || email.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Email is null or empty").build();
            }

            GustaveUser user = GustaveUserRepo.getUserByEmail(email);
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
        return Response.ok(GustaveUserRepo.getAll()).build();
    }

}
