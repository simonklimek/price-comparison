package com.szymon.webscraping;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
/**
 *
 * @author mac
 */
public class TestCarEntity {
    
    @Test
    @DisplayName("Test setting classes attributes")
    void settingCarAttibutes(){
        CarEntity carObj = new CarEntity();
        String url = "https://www.carandclassic.co.uk/car/C1177463";
         try{
            
            //Setting class elements to random values
            carObj.setTitle("BMW e46");
            carObj.setDescription("A good car");
            carObj.setPrice("£46 000");
            carObj.setCarUrl(url);
            carObj.setImages("http://pexels.com/bmw.jpg");
            
        } catch(Exception ex) {
            fail("Failed to set the elements. Exception thrown: " + ex.getMessage());
        }
        
        // Check if the value in the class match values that was set in this method
        assertEquals("£46 000", carObj.getPrice());
        assertEquals("BMW e46", carObj.getTitle());
        
    }
    
}
