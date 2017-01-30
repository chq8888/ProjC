package se.newton.testtool.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Test implements Serializable {

    @Id @GeneratedValue
    private int tId;
    
    private int totalScore;
    private int grade;

    private boolean showAnswer;
    private boolean done;

    private String title;
    private String openDate;
    private String closeDate;
    private String timer;
    private String feedback;
    
    // mapp to Question class (Children)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "test")
    @JsonManagedReference("Question-Parent-Child")
    private List<Question> questions;
    
//    // mapp to User class
//    @ManyToMany
//    private List<User> users;

    // require by Jersey JAR-RX specification
    public Test() {
        // users = new ArrayList<>();
        questions = new ArrayList<>();
    }

    public Test(String title) {
        this();
        this.title = title;
    }

    public Test(String title, String openDate, String closeDate, String timer, String feedback, boolean showAnswer, boolean done, int totalScore, int grade) {
        this(title);
        this.totalScore = totalScore;
        this.grade = grade;
        this.showAnswer = showAnswer;
        this.done = done;
        this.openDate = (openDate != null && openDate.isEmpty()) ? null : openDate;
        this.closeDate = (closeDate != null && closeDate.isEmpty()) ? null : closeDate;
        this.timer = (timer != null && timer.isEmpty()) ? null : timer;
        this.feedback = (feedback != null && feedback.isEmpty()) ? null : feedback;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isShowAnswer() {
        return showAnswer;
    }

    public void setShowAnswer(boolean showAnswer) {
        this.showAnswer = showAnswer;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = (openDate != null && openDate.isEmpty()) ? null : openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = (closeDate != null && closeDate.isEmpty()) ? null : closeDate;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = (timer != null && timer.isEmpty()) ? null : timer;
    }
    
    public String getFeedback() {
        return this.feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = (feedback != null && feedback.isEmpty()) ? null : feedback;
    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return title; // "Test [tId=" + tId + ", title=" + title + "]";
    }
}
