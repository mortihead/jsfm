package ru.mortihead.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mortihead.model.Product;
import ru.mortihead.repository.ProductRepository;
import ru.mortihead.service.ProductService;
import ru.mortihead.shared.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Resy web service for JS FRAMEWORKS PRODUCTS
 */
@RestController
public class ProductController {

    static final private Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    /**
     * simple test rest method
     *
     * @param name
     * @return
     */
    @RequestMapping("/sayhello")
    public String sayHello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    /**
     * Add product
     * Usage example:
     * http://127.0.0.1:8080/addproduct?name=BestJS&version=1.0.2&deprecation_date=01.01.2020&hype_level=10
     *
     * @param name
     * @param version
     * @param deprecationDateString
     * @param hypeLevel
     * @return
     * @throws ParseException
     */
    @RequestMapping("/addproduct")
    public int addProduct(@RequestParam(value = "name") String name,
                          @RequestParam(value = "version") String version,
                          @RequestParam(value = "deprecation_date") String deprecationDateString,
                          @RequestParam(value = "hype_level") int hypeLevel) throws ParseException {
        logger.info(String.format("addproduct call: %s, %s", name, version));

        Date deprecationDate = null;
        //TODO: fix date pattern!
        if (deprecationDateString != null && !deprecationDateString.isEmpty()) {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy"); // Russian date format
            deprecationDate = format.parse(deprecationDateString);
        }
        productService.add(new Product(null, name, version, deprecationDate, hypeLevel));
        return Constants.SUCCESS;
    }

    /**
     * Returm full list of js products
     * TODO: make paging!
     * @return
     */
    @RequestMapping("/list")
    public List<Product> list() {
        logger.info("list call");
        return productService.findAll();
    }

    /**
     * Search product by name
     * @param name of javascript framework product
     * @param partial_match - integer flag of partial match name in searching
     * @return list of found frameworks
     * @throws RuntimeException
     */
    @RequestMapping("/search")
    public List<Product> search(@RequestParam(value = "name") String name,
                                @RequestParam(value = "partial_match", defaultValue = "0") int partial_match) throws RuntimeException {
        logger.info("search call");
        if (partial_match == 0) {
            return productService.findByNameIs(name);
        } else if (partial_match == 1) {
            return productService.findByNameContainingIgnoreCase(name);
        } else throw new RuntimeException("Wrong partial_match param!");
    }

}
