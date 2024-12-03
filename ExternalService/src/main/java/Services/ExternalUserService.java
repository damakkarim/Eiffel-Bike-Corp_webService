package Services;


import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Class.ExternalUser;
import Class.UserAccount;
import Repositories.ExternalUserRepo;
import Services.IncorrectPasswordException;

@Path("/ExternalUsers")
public class ExternalUserService {
	
	
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(ExternalUser user) {
	    // Check required fields for ExternalUser
	    if (user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
	        return Response.status(Response.Status.BAD_REQUEST)
	                       .entity("All fields (name, email, password) are required.")
	                       .build();
	    }

	    
	    Long c =3L;
	    // Check required fields for UserAccount
	    UserAccount account = user.getUserAccount();
	    account.setBalance(400);
	    account.setId(c);


	    
	    if (account == null || account.getUserName() == null || account.getCardnumber() == null ||
	        account.getExpirationDate() == null || account.getCvv() == null) {
	        return Response.status(Response.Status.BAD_REQUEST)
	                       .entity("User account information (username, card number, expiration date, CVV) is required.")
	                       .build();
	    }

	    // Check if the email is already in use
	    if (ExternalUserRepo.getUserByEmail(user.getEmail()) != null) {
	        return Response.status(Response.Status.CONFLICT)
	                       .entity("This email is already in use.")
	                       .build();
	    }

	    // Send a request to save the UserAccount using the external API
	    try {
	        Client client = ClientBuilder.newClient();
	        WebTarget target = client.target("http://localhost:8080/BanqueService/bank/Bank/save");
	        Response apiResponse = target.request(MediaType.APPLICATION_JSON)
	                                     .post(Entity.entity(account, MediaType.APPLICATION_JSON));

	        if (apiResponse.getStatus() != Response.Status.OK.getStatusCode()) {
	            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                           .entity("Failed to save account: " + apiResponse.readEntity(String.class))
	                           .build();
	        }
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity("Error while calling the external API: " + e.getMessage())
	                       .build();
	    }

	    // Map additional account details
	    UserAccount newAccount = new UserAccount();
	    newAccount.setUserName(account.getUserName());
	    newAccount.setBalance(400);
	    newAccount.setCardnumber(account.getCardnumber());  // Cardnumber is now a String
	    newAccount.setExpirationDate(account.getExpirationDate());
	    newAccount.setCvv(account.getCvv());
	    newAccount.setId(user.getId());


	    // Link the UserAccount object to ExternalUser
	    user.setUserAccount(newAccount);

	    // Register the user
	    ExternalUser registeredUser = ExternalUserRepo.register(user);

	    // Return the registered user information
	    return Response.ok(registeredUser).build();
	}

    
    

    
    
    
    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(ExternalUser user) {
        try {
            // Tentative de connexion
            ExternalUser authenticatedUser = ExternalUserRepo.signIn(user.getEmail(), user.getPassword());
            
            
            System.out.print(user);
            // Si l'authentification réussit, on renvoie l'utilisateur
            return Response.ok(authenticatedUser).build();
        } catch (IncorrectPasswordException e) {
            // Si le mot de passe est incorrect, on renvoie un message d'erreur spécifique
            String errorMessage = "{\"error\": \"Incorrect password.\"}";
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
            String errorMessage = "{\"error\": \"An internal error occurred.\"}";
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
    
    
    @GET
    @Path("/FindByEmail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("email") String email) {
        System.out.println("Email received: " + email); // Ligne de débogage
        if (email == null || email.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email is null or empty").build();
        }

        ExternalUser ExternalUser = ExternalUserRepo.getUserByEmail(email);
        if (ExternalUser != null) {
            return Response.ok(ExternalUser).build(); // Renvoie l'utilisateur en JSON
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build(); // Renvoie un code 404 si l'utilisateur n'existe pas
        }
    
}


    
    
    
    
    
    
    
    

}
