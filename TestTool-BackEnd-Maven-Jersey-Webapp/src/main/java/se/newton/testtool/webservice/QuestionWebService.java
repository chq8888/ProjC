package se.newton.testtool.webservice;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import se.newton.testtool.*;
import se.newton.testtool.model.*;
import se.newton.testtool.service.*;

@Path("/questions")
public class QuestionWebService {

    private final QuestionService service = new QuestionService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getAllQuestions() {
        Util.log("Question", "GET", null, null);
        return service.getAllQuestions();
    }

    @GET
    @Path("/{questionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Question getQuestionById(@PathParam("questionId") int qId) {
        Util.log("Question", "GET", "/{questionId}/", Integer.toString(qId));
        return service.getQuestionById(qId);
    }

    @GET
    @Path("/tests/{testId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getQuestionByTestId(@PathParam("testId") int tId) {
        Util.log("Question", "GET", "/tests/{testId}/", Integer.toString(tId));
        return service.getQuestionByTestId(tId);
    }

    @POST
    @Path("/create/{testId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Question createQuestion(Question question, @PathParam("testId") int tId) {
        Util.log("Question", "POST", "/create", "question");
//        if (ans == null)  throw BadRequestException;
        
        return service.createQuestion(question, tId);
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateQuestion(Question question) {
        Util.log("Question", "PUT", "update", "question");
//        if (ans == null)  throw BadRequestException;
        
        service.updateQuestion(question);
    } 
    
    @PUT
    @Path("/update/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateQuestions(List<Question> questions) {
        Util.log("Question", "PUT", "/update/list", "questions");
//        if (ans == null)  throw BadRequestException;
        
        service.updateQuestions(questions);
    }   

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteQuestion(Question question) {
        Util.log("Question", "DELETE", "/delete", "question");
        service.deleteQuestion(question);
    }
}
