package se.newton.testtool;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Team {

    private int id;
    private String name;
    private String country;

    public Team() {
    }

    public Team(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
