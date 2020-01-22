package com.szymon.webscraping;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author mac
 */
public class Main {
    public static void main(String [] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ScraperManager manager = (ScraperManager) context.getBean("scraperManager");

        manager.startThreads();
        manager.joinThreads();
    }
    
}
