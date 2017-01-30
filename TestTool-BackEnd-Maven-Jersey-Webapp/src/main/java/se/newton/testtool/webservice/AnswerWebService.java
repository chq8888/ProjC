package se.newton.testtool.webservice;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import se.newton.testtool.*;
import se.newton.testtool.model.*;
import se.newton.testtool.service.*;

@Path("/answers")
public class AnswerWebService {

    private final AnswerService service = new AnswerService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Answer> getAllAnswers() {
        Util.log("Test", "GET", null, null);
        return service.getAllAnswers();
    }

    @GET
    @Path("/{answerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Answer getAnswerById(@PathParam("answerId") int aId) {
        Util.log("Answer", "GET", "/{answerId}/", Integer.toString(aId));
        return service.getAnswerById(aId);
    }

    @GET
    @Path("/questions/{questionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Answer> getAnswerByQuestionId(@PathParam("questionId") int qId) {
        Util.log("Answer", "GET", "/questions/{questionId}/", Integer.toString(qId));
        return service.getAnswerByQuestionId(qId);
    }

    @POST
    @Path("/create/{questionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Answer createAnswer(Answer ans, @PathParam("questionId") int qId) {
        Util.log("Answer", "POST", "/create", "answer");
//        if (ans == null)  throw BadRequestException;
        
        return service.createAnswer(ans, qId);
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateAnswer(Answer ans) {
        Util.log("Answer", "PUT", "/update", "answer");
//        if (ans == null)  throw BadRequestException;
        
        service.updateAnswer(ans);
    }
    
    @PUT
    @Path("/update/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateAnswers(List<Answer> ans) {
        Util.log("Answer", "PUT", "/update/list", "answers");
//        if (ans == null)  throw BadRequestException;
        
        service.updateAnswers(ans);
    }
    
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteAnswer(Answer ans) {
        Util.log("Answer", "DELETE", "/delete", "answer");
        service.deleteAnswer(ans);
    }
}
