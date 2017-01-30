package se.newton.testtool;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Util {

    private static final SessionFactory SESSIONFACTORY;

    // static class initializer
    static { 
        try {
            
            // Create the SessionFactory from standard (hibernate.cfg.xml)
            Configuration configuration = new Configuration();
            configuration.configure();
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SESSIONFACTORY = configuration.buildSessionFactory(serviceRegistry);
            
        } catch (Throwable ex) {
            
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getNewSession() {
        // return a new hibernate session
        return SESSIONFACTORY.openSession();
    }

    public static String log(String fromClass, String method, String param, String value) {
        param = param == null ? "" : "with Param: " + param;
        value = (param == null) || (value == null) ? "" : "and Value: " + value;
        String msg = "%s Webservice receive a %s request %s %s";

        msg = String.format(msg, fromClass.toUpperCase(), method.toUpperCase(), param, value);
        System.out.println(msg);

        return msg;
    }
}
