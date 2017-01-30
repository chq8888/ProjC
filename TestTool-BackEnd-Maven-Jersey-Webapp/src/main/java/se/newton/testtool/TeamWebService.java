package se.newton.testtool;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/teams")
public class TeamWebService {

    private TeamService service = new TeamService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Team> getTeams() {
        Util.log("Team", "GET", null, null);
        return service.getTeams();
    }
    
    @GET
    @Path("/{Id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Team getTeamById(@PathParam("Id") int Id) {
        Util.log("Team", "GET", "/{testId}/", Integer.toString(Id));
        return service.getTeamById(Id);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Team postRequest(Team team) {
        Util.log("Team", "POST", null, null);
        //if (user == null) throws new BadRequestException();

        team.setName("POST RESPONSE");
        return null;
    }
}