package se.newton.testtool.service;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.*;

import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class TestService {

    private Session session;
    private List<Test> tests;
    private Test test;
    
    public List<Test> Query(String hqlQuery) {
        session = Util.getNewSession();
        
        Query query = session.createQuery(hqlQuery);
        tests = query.list();
        
        session.close();
        return tests.isEmpty() ? null : tests;
    }

    public Integer ExecuteQuery(String hqlQuery) {
        session = Util.getNewSession();
        
        Query query = session.createQuery(hqlQuery);
        Integer rowAffected = query.executeUpdate();
        
        session.close();
        return rowAffected;
    }
    
    public List<Test> getAllTests() {
        tests = Query("FROM Test ORDER BY tId");

        if (tests != null)      disableRecursion(tests);
        
        return tests;
    }
    
    public Test getTestById(int tId) {
        session = Util.getNewSession();    
        test = (Test) session.get(Test.class, tId);

        if (test != null) disableRecursion(test);
        
        return test;
    } 
    
    public List<Test> getTestByUserId(int uId) {
        tests = Query(String.format("FROM Test WHERE uId=%d ORDER BY title", uId));
        if (tests != null)     disableRecursion(tests);
        
        return tests;
    } 

    public Test createTest(Test t) {
        Transaction tr = null;
        
        session = Util.getNewSession();
        try {
            tr = session.beginTransaction();
            test = new Test(t.getTitle(), t.getOpenDate(), t.getCloseDate(), t.getTimer(), t.getFeedback(), t.isShowAnswer(), t.isDone(), t.getTotalScore(), t.getGrade());

            // test has no parent
            session.save(test);
            tr.commit();
            
        } catch (HibernateException ex) {
            if (tr != null)     tr.rollback();
        } finally {
            session.close();
        }
        
        if (test != null)    disableRecursion(test);
        
        // return a new test that contain the primary key
        return test;
    }
    
    public Integer updateTest(Test ts) {
        tests = new ArrayList<>();
        tests.add(ts);
        return updateTests(tests);
    }

    public Integer updateTests(List<Test> tss) {
        Integer rowAffected = 0;
        Transaction tr = null;
        
        session = Util.getNewSession();        
        Query query = session.createQuery("UPDATE Test SET title=:tt, openDate=:to, closeDate=:tc, feedback=:tf, timer=:tm, grade=:tg, totalScore=:ts, done=:td, showAnswer=:tsa WHERE tId=:Id");

        try {
            tr = session.beginTransaction();
            for (Test t : tss) {
                query.setParameter("Id", t.gettId());
                query.setParameter("tt", t.getTitle());
                query.setParameter("to", t.getOpenDate());
                query.setParameter("tc", t.getCloseDate());
                query.setParameter("tf", t.getFeedback());
                query.setParameter("tm", t.getTimer());
                query.setParameter("tg", t.getGrade());
                query.setParameter("ts", t.getTotalScore());
                query.setParameter("td", t.isDone());
                query.setParameter("tsa", t.isShowAnswer());
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

    public Integer deleteTest(Test test) {
        return ExecuteQuery(String.format("DELETE FROM Test WHERE tId=:%d", test.gettId()));
    }
    
    private void disableRecursion(Test test) {
        test.setQuestions(null);
    }

    private void disableRecursion(List<Test> ts) {
        for (Test t : ts) {
            for (Question q : t.getQuestions()) {
                q.setTest(null);
                q.setAnswers(null);
            }
        }
    }
}
