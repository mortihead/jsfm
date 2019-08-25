package ru.mortihead.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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
    public boolean equals(Object obj) {
        return (((Product) obj).id == this.id &&
                ((Product) obj).name.equalsIgnoreCase(this.name) &&
                ((Product) obj).version.equals(this.version) &&
                ((Product) obj).deprecationDate == this.deprecationDate &&
                ((Product) obj).hypeLevel == this.hypeLevel);
    }

    @Override
    public String toString() {
        return "Product [id: " + id + ", name: " + name +
                ", version= "+version+", deprecationDate: "+deprecationDate+", hypeLevel: "+hypeLevel+" ]";
    }

}
