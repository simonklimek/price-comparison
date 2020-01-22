package com.szymon.webscraping;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
/**
 *
 * @author mac
 */

@Entity(name="DealerEntity")
@Table(name="dealers")
public class DealerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "webisite")
    private String website;


    /** Empty constructor */
    public DealerEntity(){
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }


    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }


}
