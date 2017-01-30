package se.newton.testtool.webservice;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import se.newton.testtool.*;
import se.newton.testtool.model.*;
import se.newton.testtool.service.*;

@Path("/tests")
@Produces(MediaType.APPLICATION_JSON)
public class TestWebService {

    private final TestService service = new TestService();
        
    @GET
    public List<Test> getAllTests() {
        Util.log("Test", "GET", "/", "tests");
        return service.getAllTests();
    }

    @GET
    @Path("/{testId}")
    public Test getTestById(@PathParam("testId") int tId) {
        Util.log("Test", "GET", "/{testId}/", Integer.toString(tId));
        return service.getTestById(tId);
    }

    @GET
    @Path("/users/{userId}")
    public List<Test> getTestByUserId(@PathParam("testId") int uId) {
        Util.log("Test", "GET", "/users/{userId}/", Integer.toString(uId));
        return service.getTestByUserId(uId);
    }
    
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Test createTest(Test test) {
        Util.log("Test", "POST", "/create", "test");
//        if (ans == null)  throw BadRequestException;
        
        return service.createTest(test);
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTest(Test test) {
        Util.log("Test", "PUT", "/update", "test");
//        if (ans == null)  throw BadRequestException;
        
        service.updateTest(test);
    } 
    
    @PUT
    @Path("/update/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTests(List<Test> tests) {
        Util.log("Test", "PUT", "/update/list", "tests");
//        if (ans == null)  throw BadRequestException;
        
        service.updateTests(tests);
    }   

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteTest(Test test) {
        Util.log("Test", "DELETE", "/delete", "test");
        service.deleteTest(test);
    }
}
