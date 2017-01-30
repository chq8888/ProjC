package se.newton.testtool.webservice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import se.newton.testtool.Util;

@Path("/result")
@Produces(MediaType.APPLICATION_JSON)
public class ResultWebService {

    @GET
    public String getRequest() {
        return Util.log("Result", "GET", null, null);
    }

    @POST
    public void postRequest(String msg) {
        Util.log("Result", "POST", null, null);
    }
}
