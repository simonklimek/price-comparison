package com.szymon.webscraping;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import javax.transaction.Transactional;
/**
 *
 * @author mac
 */

//
//

 /**
 * Hibernate class type for objects created in session factory
 */
@Transactional
public class Hibernate {
    private SessionFactory sessionFactory;

    Hibernate() {
    }
    
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        
    }
    
    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public Boolean duplicatesExists(String carUrl, Session session) {
        //Find matching products in the database
        List<CarEntity> urlList = session.createQuery("FROM cars WHERE car_url='" + carUrl + "'")
                .getResultList();

        //If there is one or more products, the duplicate exists and there is no need to create a new product
        if (urlList.size() >= 1) {
            return true;
            //Else we return false - no duplicates
        } else {
            return false;
        }
    }
    
    public void shutDown(){
        sessionFactory.close();
    }
}
