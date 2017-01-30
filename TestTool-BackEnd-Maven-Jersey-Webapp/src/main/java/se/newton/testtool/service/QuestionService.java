package se.newton.testtool.service;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.*;

import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class QuestionService {

    private Session session;
    private List<Question> questions;
    private Question question;

    public List<Question> Query(String hqlQuery) {
        session = Util.getNewSession();

        Query query = session.createQuery(hqlQuery);
        questions = query.list();

        session.close();
        return questions.isEmpty() ? null : questions;
    }

    public Integer ExecuteQuery(String hqlQuery) {
        session = Util.getNewSession();

        Query query = session.createQuery(hqlQuery);
        Integer rowAffected = query.executeUpdate();

        session.close();
        return rowAffected;
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = Query("FROM Question ORDER BY qId");

        if (questions != null)      disableRecursion(questions);

        return questions;
    }

    public Question getQuestionById(int qId) {
        session = Util.getNewSession();
        question = (Question) session.get(Question.class, qId);
        session.close();

        if (question != null)    disableRecursion(question);

        return question;
    }

    public List<Question> getQuestionByTestId(int tId) {
        questions = Query(String.format("FROM Question WHERE tId=%d ORDER BY qId", tId));

        if (questions != null)     disableRecursion(questions);

        return questions;
    }
    
    public Question createQuestion(Question qt, int tId) {
        Transaction tr = null;

        TestService service = new TestService();
        session = Util.getNewSession();
        try {
            tr = session.beginTransaction();
            question = new Question(qt.getTitle(), qt.getCategory(), qt.getScore());
            
            // set parent to this question
            Test test = service.getTestById(tId);
            // test.getQuestions().add(question);
            question.setTest(test);

            session.save(question);
            tr.commit();

        } catch (HibernateException ex) {
            if (tr != null) tr.rollback();
        } finally {
            session.close();
        }

        if (question != null)    disableRecursion(question);
        
        // return a new question that contain the primary key
        return question;    
    }

    public Integer updateQuestion(Question qt) {
        questions = new ArrayList<>();
        questions.add(qt);
        return updateQuestions(questions);
    }

    public Integer updateQuestions(List<Question> qts) {
        Integer rowAffected = 0;
        Transaction tr = null;

        session = Util.getNewSession();
        Query query = session.createQuery("UPDATE Question SET title=:qt, category=:qc, score=:qs WHERE qId=:qId");

        try {
            tr = session.beginTransaction();
            for (Question q : qts) {
                query.setParameter("Id", q.getqId());
                query.setParameter("qt", q.getTitle());
                query.setParameter("qc", q.getCategory());
                query.setParameter("qs", q.getScore());
                rowAffected += query.executeUpdate();
            }
            tr.commit();
        } catch (HibernateException ex) {
            if (tr != null)     tr.rollback();
        } finally {
            session.close();
        }

        return rowAffected;
    }

    public Integer deleteQuestion(Question question) {
        return ExecuteQuery(String.format("DELETE FROM Question WHERE qId=:%d", question.getqId()));
    }

    private void disableRecursion(Question question) {
        question.setTest(null);
        for (Answer ans : question.getAnswers()) {
            ans.setQuestion(null);
        }
    }

    private void disableRecursion(List<Question> questions) {
        for (Question q : questions) {
            q.setTest(null);
            for (Answer ans : q.getAnswers()) {
                ans.setQuestion(null);
            }
        }
    }
}
