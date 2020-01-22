package com.szymon.webscraping;
/**
 *
 * @author mac
 */
import java.io.IOException;
import static java.lang.Thread.sleep;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Represents a scraper thread dedicated for Car and Classic website.
 */

public class CarScraper extends Thread{
    String domain = "https://www.carandclassic.co.uk";
    String make_BMW = "/list/10/";
    String make_AstonMartin = "/list/7/";
    String make_Jaguar = "/list/22/";
    String make_Mercedes = "/list/31/";
    String make_Bentley = "/list/9/";
    int page = 1;
    int totalPages = 10;

    private String listingUrl = "";
    private String carUrl = "https://www.carandclassic.co.uk/classic_cars.php?category=&make="+ "22" +"&region=&country=1&era=&type=1&price=&keyword=&S.x=80&S.y=10";
   
    /**
   * Default delay time for scraper set to 2 seconds per each iteration
   */
    private int crawlDelay = 2000;
    
   /**
   * Sets the delay time between each thread run
   * @param delay_ms This thread new delay.  
   *                Integer type parameter 
   *                represents milliseconds
   */
    public void setScrapeDelay_ms(int delay_ms) {
        this.crawlDelay = delay_ms;
    }

    volatile private boolean runThread = false;

    CarEntity carObj = new CarEntity();

    // Class that generates sessionFactory
    Hibernate hibernate = new Hibernate();

    @Override
    public void run(){
        runThread = true;
        try{
            while(runThread){
                System.out.println("Scraper 4 thread is scraping data.");
                System.out.println("Scraping "+ domain + " Make: " + make_BMW);

                    for (int j=1; j<=100; j++) { // looping through pages
                        Document carPage = Jsoup.connect("https://www.carandclassic.co.uk/classic_cars.php?category=&make=" + "22" + "&region=&country=1&era=&type=1&price=&keyword=&S.x=80&S.y=10&page=" + j).get();
                        Elements carList = carPage.select("#browserPageItemList");
                        for (int i = 0; i < carList.size(); ++i) {
                            Elements carUrls = carList.get(i).select(".titleAndText > a[href]");
                            for (Element link : carUrls) {
                                System.out.println("\nlink : " + domain + link.attr("href"));
                                listingUrl = "" + domain + link.attr("href");
                                try{
                                    CarEntity carObj = new CarEntity();
                                    Document carListingPage = Jsoup.connect(listingUrl).get();
                                    Elements carListing = carListingPage.select("#advertPageItemMain");
                                    for(int x=0; i<carListing.size(); ++x){
                                        Elements carTitle = carListing.get(x).select(".table_title");
                                        System.out.println("### Title: " + carTitle.text());
                                        //                            Document carListingPage = Jsoup.connect(listingUrl).get();
        //                            Elements carListing = carListingPage.select("#advertPageItemMain");
        //                            for(int x=0; i<carListing.size(); ++x){

                                        Session mysession = hibernate.getSessionFactory().getCurrentSession();
                                        carObj.setCarUrl(listingUrl);
        //
        //                                Elements carTitle = carListing.get(x).select(".table_title");
        //                                System.out.println("### Title: " + carTitle.text());
                                        carObj.setTitle(carTitle.text());

//                                        System.out.println("### Thumb size images list: ");
                                        String image = "";
                                        Elements carThumbImages = carListing.get(x).select("li[data-thumb$=.jpg]");
                                        for (Element jpg : carThumbImages) {
//                                            System.out.println("Url: " + jpg.attr("data-thumb"));
                                            image = "" + jpg.attr("data-thumb");
                                        }
                                        carObj.setImages(image);

                                        Elements carDescription = carListing.get(x).select("#itemText");
//                                        System.out.println("### Description: " + carDescription.text());
                                        carObj.setDescription(carDescription.text());

                                        Elements carPrice = carListing.get(x).select("td:containsOwn(Price) + td");
//                                        System.out.println("### Price: " + carPrice.text());
                                        carObj.setPrice(carPrice.text());

  
//                                     // Check if duplicate exists
//                                        if(!hibernate.duplicatesExists(listingUrl, mysession)){
//                                            // Duplicate doesnt exist
//                                            carObj.setCarUrl(listingUrl);
//
//                                            // Add laptop, url and product to database (need to commit)
//                                            mysession.save(carObj);
//
//                                            //Commit transaction to save it to database
//                                            mysession.getTransaction().commit();
//
//                                            //Close the session and release database connection
//                                            mysession.close();
//                                        } else {
//                                            // Duplicate exists
//                                            mysession.update(carObj);
//                                            mysession.close();
//                                        }

                                        mysession.beginTransaction();
                                        mysession.save(carObj);
                                        mysession.getTransaction().commit();
                                        mysession.close();
                                        }// end of for 
                                    }  // end of try
                                    catch(Exception ex){
                                        ex.printStackTrace();
                                    }
                            } // end of for
                        }// end of for
                    } // end of for
            } // end of while  
            sleep(crawlDelay);
        } // end of try
        catch (IOException ex) {
            System.out.println("Error while accessing the carandclassic.co.uk website");
        }   
        catch(InterruptedException ex){
            System.err.println(ex.getMessage());
        }
    } // end of run method  
    
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
} // end of  class 
