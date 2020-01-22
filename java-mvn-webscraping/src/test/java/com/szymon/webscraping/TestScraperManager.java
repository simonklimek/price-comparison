package com.szymon.webscraping;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
/**
 *
 * @author mac
 */
public class TestScraperManager {
    
    
    @Test
    @DisplayName("Testing setter for threads in ScraperManager class")
    void scraperManagerListTest(){
        // Instance of the class we want to test
        ScraperManager manager = new ScraperManager();
        
        // Creating 3 new threads
        Thread scraper1 = new Thread();
        Thread scraper2 = new Thread();
        Thread scraper3 = new Thread();
        
        // Empty list to store threads
        List<Thread> testList = new ArrayList();
        
        // Adding threads to empty list
        testList.add(scraper1);
        testList.add(scraper2);
        testList.add(scraper3);
        
        try{
            // Adding list to our ScraperManager
            manager.setScraperList(testList);
        } catch(Exception ex){
            fail("Failed to set the scraperList in ScaperManager class" + ex.getMessage());
        }
        
        // Chech if the testList matches ScraperManagers list
        assertEquals(testList, manager.getScraperList());
    }
    
    
    @Test
    @DisplayName("Test if threads are running")        
    void scrapingThreadsTest(){
        // Instance of threads that we want to test (All of them)
        CarScraper scraper1 = new CarScraper();
        CarScraper scraper2 = new CarScraper();
        CarScraper scraper3 = new CarScraper();
        
        // Empty list to store threads
        List<Thread> threadList = new ArrayList();
        
        // Add threads to the list
        threadList.add(scraper1);
        threadList.add(scraper2);
        threadList.add(scraper3);
        
        try{
            // Start threads
            for(Thread thread : threadList)
                thread.start();
        } catch(Exception ex) {
            fail("Failed to start the threads" + ex.getMessage());
        }
        
        // Test to see if threads are running
        for(Thread test : threadList)
            assertEquals(true,test.isAlive());
    }
    
}
