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
 * Class ClassicCarsForSaleScraper extends a thread, dedicated for classiccarsforsale.co.uk website
 */
public class ClassicCarsForSaleScraper extends Thread{
    
    
    // https://www.classiccarsforsale.co.uk/jaguar
    
    
   /**
   * Default delay time for scraper set to 2 seconds per each iteration
   */
    private int crawlDelay = 2000;
    private String listingUrl = "";
    String domain = "https://www.classiccarsforsale.co.uk";
    
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
                for (int pages=1; pages<=10; pages++) { // loop through number of pages of listings 
                    //Download HTML document from website
                    Document doc = Jsoup.connect("https://www.classiccarsforsale.co.uk/jaguar?page="+pages).get();
                    //Get all of the car listings from the page
                    Elements carList = doc.select(".listings");
                    for (int i = 0; i < carList.size(); ++i) {
                        Elements carUrls = carList.get(i).select(".listing-desc-bottom-row > a[href]");
                        for (Element link : carUrls) {
                            listingUrl = "" + domain + link.attr("href");
                            // important
                            try{
                                CarEntity carObj = new CarEntity();
                                Document carListingPage = Jsoup.connect(listingUrl).get();
                                Elements carListing = carListingPage.select("#advertPageItemMain");
                                for(int x=0; i<carListing.size(); ++x){
                                    Elements carTitle = carListing.get(x).select(".table_title");
                                    System.out.println("### Title: " + carTitle.text());

                                    Session mysession = hibernate.getSessionFactory().getCurrentSession();
                                    carObj.setCarUrl(listingUrl);

                                    carObj.setTitle(carTitle.text());

                                    String image = "";
                                    Elements carThumbImages = carListing.get(x).select("li[data-thumb$=.jpg]");
                                    for (Element jpg : carThumbImages) {
                                        image = "" + jpg.attr("data-thumb");
                                    }
                                    carObj.setImages(image);

                                    Elements carDescription = carListing.get(x).select("#itemText");
                                    carObj.setDescription(carDescription.text());

                                    Elements carPrice = carListing.get(x).select("td:containsOwn(Price) + td");
                                    carObj.setPrice(carPrice.text());
                                    
                                // Check if duplicate exists
                                if(!hibernate.duplicatesExists(listingUrl, mysession)){
                                    // Duplicate doesnt exist
                                    carObj.setCarUrl(listingUrl);

                                    // Add laptop, url and product to database (need to commit)
                                    mysession.save(carObj);

                                    //Commit transaction to save it to database
                                    mysession.getTransaction().commit();

                                    //Close the session and release database connection
                                    mysession.close();
                                } else {
                                    // Duplicate exists
                                    mysession.update(carObj);
                                    mysession.close();
                                }
                                    
//                                    mysession.beginTransaction();
//                                    mysession.save(carObj);
//                                    mysession.getTransaction().commit();
//                                    mysession.close();
                                    }// end of for 
                                }  // end of try
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            
                        } // end of for loop through links one by one
                    }// end of for loop through links
                } // end of for loop through pages
            } // end of while loop
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
