package se.newton.testtool.service;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.*;

import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class AnswerService {

    private Session session;
    private List<Answer> answers;
    private Answer answer;

    public List<Answer> Query(String hqlQuery) {
        session = Util.getNewSession();
        
        Query query = session.createQuery(hqlQuery);
        answers = query.list();
        
        session.close();
        return answers.isEmpty() ? null : answers;
    }

    public Integer ExecuteQuery(String hqlQuery) {
        session = Util.getNewSession();
        
        Query query = session.createQuery(hqlQuery);
        Integer rowAffected = query.executeUpdate();
        
        session.close();
        return rowAffected;
    }

    public List<Answer> getAllAnswers() {
        List<Answer> answers = Query("FROM Answer ORDER BY aId");

        if (answers != null) disableRecursion(answers);
        
        return answers;
    }

    public Answer getAnswerById(int aId) {
        session = Util.getNewSession();       
        answer = (Answer) session.get(Answer.class, aId);
        session.close();

        if (answer != null) disableRecursion(answer);
        
        return answer;
    }

    public List<Answer> getAnswerByQuestionId(int qId) {        
        answers = Query(String.format("FROM Answer WHERE qId=%d ORDER BY aId", qId));
        
        if (answers != null)     disableRecursion(answers);
        
        return answers;
    }

    public Answer createAnswer(Answer ans, int qId) {
        Transaction tr = null;

        QuestionService service = new QuestionService();
        session = Util.getNewSession();
        try {
            tr = session.beginTransaction();
            answer = new Answer(ans.getTitle(), ans.getAnsStudent(), ans.getAnsTeacher());
            
            // set parent to this answer
            Question question = service.getQuestionById(qId);
            // question.getAnswers().add(answer);
            answer.setQuestion(question);

            session.save(answer);
            tr.commit();

        } catch (HibernateException ex) {
            if (tr != null) tr.rollback();
        } finally {
            session.close();
        }

        if (answer != null)    disableRecursion(answer);
        
        // return a new answer that contain the primary key
        return answer;    
    }
    
    public Integer updateAnswer(Answer ans) {
        answers = new ArrayList<>();
        answers.add(ans);
        return updateAnswers(answers);
    }

    public Integer updateAnswers(List<Answer> anss) {
        Integer rowAffected = 0;
        Transaction tr = null;

        session = Util.getNewSession();
        Query query = session.createQuery("UPDATE Answer SET title=:at, ansStudent=:as, ansTeacher=:atc WHERE aId=:Id");

        try {
            tr = session.beginTransaction();
            for (Answer a : anss) {
                query.setParameter("Id", a.getaId());
                query.setParameter("at", a.getTitle());
                query.setParameter("as", a.getAnsStudent());
                query.setParameter("atc", a.getAnsTeacher());
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

    public Integer deleteAnswer(Answer ans) {
        return ExecuteQuery(String.format("DELETE FROM Answer WHERE aId=%d", ans.getaId()));
    }
    
    private void disableRecursion(Answer answer) {
        answer.setQuestion(null);
    }

    private void disableRecursion(List<Answer> answers) {
        for (Answer a : answers) {
            a.setQuestion(null);
        }
    }
}