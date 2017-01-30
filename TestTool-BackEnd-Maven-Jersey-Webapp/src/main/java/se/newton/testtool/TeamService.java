package se.newton.testtool;

import java.util.ArrayList;
import java.util.List;

public class TeamService {

    private List<Team> teams;

    public TeamService() {
        teams = new ArrayList();

        teams.add(new Team(1, "batman", "batman@hotmail.com"));
        teams.add(new Team(2, "superman", "superman@hotmail.com"));
        teams.add(new Team(3, "catwoman", "catwoman@hotmail.com"));
    }
    
    public List<Team> getTeams() {
        return teams;
    }
    
    public Team getTeamById(int Id) {
        return teams.get(Id);
    }
}
