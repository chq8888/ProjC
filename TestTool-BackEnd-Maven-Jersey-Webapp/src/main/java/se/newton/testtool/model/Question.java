package se.newton.testtool.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Question implements Serializable {

    @Id @GeneratedValue
    private int qId;

    private int score;
    private String title;
    private String category;

    // mapp to Test class (Parent)
    @ManyToOne
    @JoinColumn(name = "tId")
    @JsonBackReference("Question-Parent-Child")
    private Test test;

    // mapp to Answer class (Children)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question")
    @JsonManagedReference("Answer-Parent-Child")
    private List<Answer> answers;

    // require by Jersey JAR-RX specification
    public Question() {
        answers = new ArrayList<>();
    }

    public Question(String title, String category, int score) {
        this();
        this.title = title;
        this.category = category;
        this.score = score;
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
    
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question [qid=" + qId + ", title=" + title + ", category=" + category /* + ", testId=" + test.gettId() */ + "]";
    }
}
