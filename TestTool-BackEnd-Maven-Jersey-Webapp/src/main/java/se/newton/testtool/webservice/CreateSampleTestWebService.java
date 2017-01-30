package se.newton.testtool.webservice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import se.newton.testtool.*;
import se.newton.testtool.model.*;
import se.newton.testtool.service.*;

@Path("/createsampletest")
public class CreateSampleTestWebService {

    private final CreateSampleTestService service = new CreateSampleTestService();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void createSampleTest(Test t) {
        Util.log("CreateTest", "POST", null, null);
//        if (ans == null)  throw BadRequestException;
        
        service.createSampleTest();
    }
}
