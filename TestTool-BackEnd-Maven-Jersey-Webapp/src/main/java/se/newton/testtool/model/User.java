package se.newton.testtool.model;

import javax.persistence.*;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class User implements Serializable {

    @Id @GeneratedValue
    private int uId;

    private String email;
    private String name;
    private String passw;
    private String category;

//    // mapp to Test class
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "users")
//    private List<Test> tests;
            
    // require by Jersey JAR-RX specification
    public User() {
        //tests = new ArrayList<>();
    }

    public User(String name, String email, String passw, String category) {
        this();
        this.name = name;
        this.email = email;
        this.passw = passw;
        this.category = category;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassw() {
        return this.passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public Collection<Test> getTests() {
//        return tests;
//    }
//
//    public void setTests(List<Test> tests) {
//        this.tests = tests;
//    }

    @Override
    public String toString() {
        return "User [uId=" + uId + ", name=" + name + ", email=" + email + ", category=" + category + "]";
    }
}
