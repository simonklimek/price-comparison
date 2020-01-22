package com.szymon.webscraping;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/**
 *
 * @author mac
 */
public class TestAppConfig {
    @Test
    @DisplayName("Test scraperManager bean from AppConfig class")
    void scaperManagerTest() {
        //Instruct Spring to create and wire beans using XML file
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        //Get scraperManager bean which contains all scrapers
        ScraperManager scraperManager = (ScraperManager) context.getBean("scraperManager");
        
        assertNotNull(scraperManager.getScraperList());
    }
}
