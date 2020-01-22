package com.szymon.webscraping;
import java.util.List;
/**
 *
 * @author mac
 */
public class ScraperManager {
   List<Thread> scraperList;
    /**
     * Empty constructor
     */
    ScraperManager() {}
    
    //Getters and setters
    public List<Thread> getScraperList() {
        return scraperList;
    }
    public void setScraperList(List<Thread> scraperList) {
        this.scraperList = scraperList;
    }

    public void startThreads(){
        for(Thread carScraper : scraperList){
            carScraper.start();
        }
        System.out.println("Scraping terminated");
    }

    public void joinThreads(){
        System.out.println("++++++++++++++++++ joining threads");
        for(Thread carScraper : scraperList){
            try{
                carScraper.join();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

//
//        //Shut down sessionManager - only one of these is shared between the scrapers
//        if(!scraperList.isEmpty())
//            scraperList.get(0).setHibernate().getSessionFactory().close();

}
