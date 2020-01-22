package com.szymon.webscraping;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
/**
 *
 * @author mac
 */

/**
* Car object entity is used for mapping with database columns
* Class represents the car object
*/
@Entity(name="CarEntity")
@Table(name="cars")
public class CarEntity implements Serializable{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;

        @Column(name = "car_title")
        private String title;

        @Column(name = "car_description")
        private String description;

        @Column(name = "car_price")
        private String price;

        @Column(name = "car_images")
        private String images;

        @Column(name = "car_url")
        private String car_url;


        /** Empty constructor */
        public CarEntity(){
        }

        //Getters and setters
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }
        public void setPrice(String price) {
            this.price = price;
        }

        public String getImages(String images){ return images; }
        public void setImages(String images){this.images = images;}

        public String getCarUrl(String car_url){ return car_url; }
        public void setCarUrl(String car_url){this.car_url = car_url;}

}
