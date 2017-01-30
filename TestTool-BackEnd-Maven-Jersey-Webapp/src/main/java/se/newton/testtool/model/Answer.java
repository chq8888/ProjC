package se.newton.testtool.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonBackReference;

@XmlRootElement
@Entity
public class Answer implements Serializable {

    @Id @GeneratedValue
    private int aId;

    private String title;
    private String ansStudent;
    private String ansTeacher;

    // mapp to Question class (Parent)
    @ManyToOne
    @JoinColumn(name = "qId")
    @JsonBackReference("Answer-Parent-Child")
    private Question question;
    
    // require by Jersey JAR-RX specification
    public Answer() {
    }

    public Answer(String title) {
        this.title = title;
    }
    
    public Answer(String title, String ansStudent, String ansTeacher) {
        this(title);
        this.ansStudent = (ansStudent != null && ansStudent.isEmpty()) ? null : ansStudent;
        this.ansTeacher = (ansTeacher != null && ansTeacher.isEmpty()) ? null : ansTeacher;
    }

    public int getaId() {
        return this.aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnsStudent() {
        return ansStudent;
    }

    public void setAnsStudent(String ansStudent) {
        this.ansStudent = (ansStudent != null && ansStudent.isEmpty()) ? null : ansStudent;
    }

    public String getAnsTeacher() {
        return ansTeacher;
    }

    public void setAnsTeacher(String ansTeacher) {
        this.ansTeacher = (ansTeacher != null && ansTeacher.isEmpty()) ? null : ansTeacher;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer [aid=" + aId + ", title=" + title /* + ", questionId=" + question.getqId() */ + "]";
    }
}
