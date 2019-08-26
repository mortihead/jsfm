package ru.mortihead.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String version;
    @Column(nullable = true)
    private Date deprecationDate;
    @Column(nullable = true)
    private Integer hypeLevel;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(Date deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    public Integer getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(Integer hypeLevel) {
        this.hypeLevel = hypeLevel;
    }

    public Product(String name, String version, Date deprecationDate, Integer hypeLevel) {
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
                ", version= " + version + ", deprecationDate: " + deprecationDate + ", hypeLevel: " + hypeLevel + " ]";
    }

}
