package com.szymon.webscraping;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 *
 * @author mac
 */
public class TestHibernate {
    
    @Test
    @DisplayName("Test sessionFactory method from Hibernate class (DATABASE REQUIRED)")
    void sessionFactoryMethodTest() {
        // Instance of a class that we want to test
        Hibernate hibernate = new Hibernate();
        AppConfig app = new AppConfig();
        
        try{
            // Create a sessionFactory
            hibernate.setSessionFactory(app.sessionFactory());
        } catch(Exception ex) {
            fail("Failed setting sessionFactory (IS THE DATABASE ON?)" + ex.getMessage());
        }
        
        // Check if sessionFactory is not null
        assertNotNull(hibernate.getSessionFactory());
    }
    
    
}


