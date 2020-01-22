package com.szymon.webscraping;
import java.io.IOException;
import static java.lang.Thread.sleep;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *
 * @author mac
 */

/**
 * Class ERClassicsScraper extends a thread, dedicated for erclassics.com website
 */
public class ERClassicsScraper extends Thread{
    
    
    // https://www.erclassics.com/classic-cars-for-sale/jaguar/
    
   /**
   * Default delay time for scraper set to 2 seconds per each iteration
   */
    private int crawlDelay = 3000;
    
   /**
   * Sets the delay time between each thread run
   * @param delay_ms This thread new delay.  
   *                Integer type parameter 
   *                represents milliseconds
   */
    public void setScrapeDelay_ms(int delay_ms) {
        this.crawlDelay = delay_ms;
    }
    
    // store boolean value represents status of the thread
    volatile private boolean runThread = false;
    
    // Create database entieties objects to store data from website
    CarEntity carObj = new CarEntity();

    // Class that generates sessionFactory
    Hibernate hibernate = new Hibernate();
    
    /**
    * Method run start the thread and scraping 
    */
    @Override
    public void run(){
        runThread = true;
        try{
            while(runThread){
                for (int pages=1; pages<=3; pages++) { // loop through number of pages of listings 
                    //Download HTML document from website
                    Document doc = Jsoup.connect("https://www.erclassics.com/classic-cars-for-sale/jaguar/?p="+pages).get();
                    //Get all of the car listings from the page
                    Elements listings = doc.select(".item");
                }
            }
            sleep(1000 * crawlDelay);
        }// end of try   
        catch (IOException ex) {
                System.out.println("Error while accessing the CLASSIC-TRADER.COM website");
        }   
        catch(InterruptedException ex){
                System.err.println(ex.getMessage());
        }
    }
    
    /**
    * Other classes can use this method to terminate the thread,
    * but they doesn't
    */
    public void stopScraping(){
        runThread = false;
    }

    /**
    * Set hibernate class to get sessionFactory
    * @param hibernate sets hibernate
    */
    public void setHibernate(Hibernate hibernate){
        this.hibernate = hibernate;
    }
}
