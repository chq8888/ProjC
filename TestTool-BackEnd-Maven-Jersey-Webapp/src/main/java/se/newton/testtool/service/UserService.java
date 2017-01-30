package se.newton.testtool.service;

import java.util.List;
import org.hibernate.*;

import se.newton.testtool.*;
import se.newton.testtool.model.*;

public class UserService {

    private Session session;
    private List<User> users;
    private User user;

    public List<User> Query(String hqlQuery) {
        session = Util.getNewSession();
        
        Query query = session.createQuery(hqlQuery);
        users = query.list();
        
        session.close();
        return users.isEmpty() ? null : users;
    }

    public Integer ExecuteQuery(String hqlQuery) {
        session = Util.getNewSession();
        
        Query query = session.createQuery(hqlQuery);
        Integer result = query.executeUpdate();
        
        session.close();
        return result;
    }
    
    public List<User> getAllUsers() {
        
        session = Util.getNewSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM User ORDER BY uId");
        users = query.list();  
        
        session.getTransaction().commit();
        session.close();
        
        return users;
    }

    public User getUserById(int uId) {
        
        session = Util.getNewSession();
        session.beginTransaction();
        
        user = (User) session.get(User.class, uId);
        
        session.getTransaction().commit();
        session.close();

        return user;
    }

    public Integer deleteUserById(int uId) {
        return ExecuteQuery(String.format("DELETE FROM User WHERE uId=:%d", uId));
    }
}
