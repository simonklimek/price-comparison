package com.szymon.webscraping;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author mac
 */

/**
* The Spring beans definitions and Spring configuration file
* application uses annotations only
*/
@Configuration
public class AppConfig {
    SessionFactory sessionFactory = null;
    /**
    * Scraper manager Bean
    * @return an instance of Scraper Manager with a list of Web scrapers
    */
    @Bean
    public ScraperManager scraperManager(){
        ScraperManager scraperManager = new ScraperManager();

        //Create list of web scrapers and add to scraper manager
        List<Thread> scraperList = new ArrayList();

//        scraperList.add(scraper2());
        scraperList.add(scraper4());

        scraperManager.setScraperList(scraperList);
        
        // Return Scraper Manager object
        return scraperManager;
    }


    /**
    * Car and Classic Scraper Bean
    * @return an instance of scraper object with set hibernate 
    */
    @Bean
    public CarScraper scraper4(){
        CarScraper scraper4 = new CarScraper();
        scraper4.setScrapeDelay_ms(3000);
        scraper4.setHibernate(hibernate());
        return scraper4;
    }

    /**
    * Session Factory Bean
    * @return an instance of Session Factory
    */
    @Bean
    public Hibernate hibernate(){
        Hibernate hibernate = new Hibernate();
        hibernate.setSessionFactory(sessionFactory());
        return hibernate;
    }

   /**
   * Session factory bean
   * establish and builds database connection objects
   * @return sesstionFactory returns instance of hibernate object if there isn't any existing
   */
    @Bean
    public SessionFactory sessionFactory(){
        if(sessionFactory == null){
            try {
                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
                standardServiceRegistryBuilder.configure("hibernate.cfg.xml");
                //Create the registry that will be used to build the session factory
                StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
                try {
                    //Create the session factory - this is the goal of the init method.
                    sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
                }
                catch (Exception e) {
                        /* The registry would be destroyed by the SessionFactory,
                            but we had trouble building the SessionFactory, so destroy it manually */
                    System.err.println("Session Factory build failed.");
                    e.printStackTrace();
                    StandardServiceRegistryBuilder.destroy( registry );
                }
                //Ouput result
                System.out.println("Session factory built.");
            }
            catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("SessionFactory creation failed." + ex);
                ex.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
