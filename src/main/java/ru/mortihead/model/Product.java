package ru.mortihead.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Product {
    @Id
    public Integer id;
    public String name;
    public String version;
    public Date deprecationDate;
    public int hypeLevel;

    public Product(Integer id, String name, String version, Date deprecationDate, int hypeLevel) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.deprecationDate = deprecationDate;
        this.hypeLevel = hypeLevel;
    }

    public Product() {
        //Default constructor needed for JPA.
    }

    @Override
    public String toString() {
        return "Product [id: " + id + ", name: " + name +
                ", version= "+version+", deprecationDate: "+deprecationDate+", hypeLevel: "+hypeLevel+" ]";
    }

}
