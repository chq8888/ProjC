package se.newton.testtool;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("myresource")
@Produces({"text/plain"})
public class MyResource {

    @GET
    public String getRequest() {
        return Util.log("MyResource", "GET", null, null);
    }

    @GET
    @Path("/{Id}")
    public String getName(@PathParam("Id") int Id) {
        Util.log("MyResource", "GET", "/{Id}", Integer.toString(Id));
        return "{/Id}: " + Id;
    }

    @GET
    @Path("/{Id}/{name}")
    public String getName(@PathParam("Id") int Id, @PathParam("name") String name) {
        Util.log("MyResource", "GET", "/{Id}/{name}", Integer.toString(Id) + "/" + name);
        return "{/Id}/{name}: " + Id + "/" + name;
    }

    @POST
    public void postRequest(String msg) {
        Util.log("User", "POST", null, null);
    }
}
