package se.newton.testtool.webservice;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import se.newton.testtool.*;
import se.newton.testtool.model.*;
import se.newton.testtool.service.*;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserWebService {

    private final UserService service = new UserService();

    @GET
    public List<User> getAllUser() {
        Util.log("User", "GET", null, null);
        return service.getAllUsers();
    }

    @GET
    @Path("/{userId}")
    public User getUserById(@PathParam("userId") int uId) {
        Util.log("User", "GET", "/{userId}/", Integer.toString(uId));
        return service.getUserById(uId);
    }
    
    @POST
    public void postRequest(String msg) {
        Util.log("User", "POST", null, null);
    }

    @DELETE
    @Path("/{userId}")
    public void deleteUserById(@PathParam("userId") int uId) {
        Util.log("User", "DELETE", "/{userId}/", Integer.toString(uId));
        service.deleteUserById(uId);
    }
}
