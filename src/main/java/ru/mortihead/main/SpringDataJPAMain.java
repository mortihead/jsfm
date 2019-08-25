package ru.mortihead.main;


import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.mortihead.model.Product;
import ru.mortihead.service.ProductService;

/**
 * Simple tester for Spring-Data-JPA.
 **/
@Deprecated
public class SpringDataJPAMain {
    static final private Logger logger = Logger.getLogger(SpringDataJPAMain.class);
    public static void main(String[] args) {

        //Create Spring application context
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");

        //Get service from context.
        ProductService productService = ctx.getBean(ProductService.class);

        //Add some items
        productService.add(new Product(1, "Angular", "1.7.8", null, 10));
        productService.add(new Product(2, "React", "16.9.0", null, 9));
        productService.add(new Product(3, "Node.js", "12.8.0", null, 8));
        productService.add(new Product(3, "Backbone.js", "1.4.0", null, 8));

        //Test entity listing
        logger.info("findAll=" + productService.findAll());

        //Test specified find methods
        logger.info("findByName is 'React': " + productService.findByNameIs("React"));
        logger.info("findByNameContainingIgnoreCase 'JS': " + productService.findByNameContainingIgnoreCase("JS"));

        ctx.close();
    }
}