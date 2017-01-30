package se.newton.testtool.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.*;

import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class CreateSampleTestService {

    private List<Question> questions;
    private Collection<Answer> answers;
    private User us;
    private Question qt;
    private Answer ans;
    private Session session;
            
    public void createSampleTest() {        

        session = Util.getNewSession();
        session.beginTransaction();

        us = new User("User1", "yy", "zz", "STUDENT");
        session.save(us);
        
        us = new User("User2", "yy", "zz", "TEACHER");
        session.save(us);
        
        us = new User("User3", "yy", "zz", "ADMIN");
        session.save(us);
        
        Test t = new Test("Java grund kurs");
        questions = t.getQuestions();
        
        qt = new Question("Vad är SQL förkortning för", "SELECTION", 0);
        qt.setTest(t);
        questions.add(qt);

        answers = qt.getAnswers();
        
        ans = new Answer("Simple Query Language");
        ans.setQuestion(qt);
        answers.add(ans);
        
        ans = new Answer("Selection Query Language");
        ans.setQuestion(qt);
        answers.add(ans);
        
        ans = new Answer("Sequel Query Language");
        ans.setQuestion(qt);
        answers.add(ans);
        
        ans = new Answer("Standard Query Language");
        ans.setQuestion(qt);
        answers.add(ans);
        
        qt = new Question("Vilken av följande är frukt", "CHOICE", 0);
        qt.setTest(t);
        questions.add(qt);
        
        answers = qt.getAnswers();
        
        ans = new Answer("Banan");
        ans.setQuestion(qt);
        answers.add(ans);
        
        ans = new Answer("Flygplan");
        ans.setQuestion(qt);
        answers.add(ans);
        
        ans = new Answer("Lägenhet");
        ans.setQuestion(qt);
        answers.add(ans);
        
        ans = new Answer("Äpple");
        ans.setQuestion(qt);
        answers.add(ans);
        
        qt = new Question("Vilken land har mest befolkning", "REORDER", 0);
        qt.setTest(t);
        questions.add(qt);
        
        answers = qt.getAnswers();
        
        ans = new Answer("Island");
        ans.setQuestion(qt);
        answers.add(ans);
        
        ans = new Answer("Finland");
        ans.setQuestion(qt);
        answers.add(ans);
        
        ans = new Answer("Sverige");
        ans.setQuestion(qt);
        answers.add(ans);
        
        ans = new Answer("Danmark");
        ans.setQuestion(qt);
        answers.add(ans);
        
        qt = new Question("Vad är skillnad mellan static och instans variable", "ANSWER", 0);
        qt.setTest(t);
        questions.add(qt);
        
        answers = qt.getAnswers();
        
        ans = new Answer("Vad är skillnad mellan static och instans variable");
        ans.setQuestion(qt);
        answers.add(ans);
        
        session.save(t);
        
        session.getTransaction().commit();
        session.close();
    }
}
