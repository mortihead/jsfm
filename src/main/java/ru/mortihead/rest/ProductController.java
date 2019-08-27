package ru.mortihead.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mortihead.exception.ProductNotFoundException;
import ru.mortihead.exception.WrongSearchParamException;
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
    ProductRepository productRepository;

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
     * legacy format :-)
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
        productRepository.save(new Product(name, version, deprecationDate, hypeLevel));
        return Constants.SUCCESS;
    }

    /**
     * Returm full list of js products
     * Usage example:
     * http://127.0.0.1:8080/products
     * TODO: make paging!
     * @return
     */
    @RequestMapping("/products")
    public List<Product> list() {
        logger.info("list call");
        return productRepository.findAll();
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
                                @RequestParam(value = "partial_match", defaultValue = "0") int partial_match) throws RuntimeException, WrongSearchParamException {
        logger.info("search call");
        if (partial_match == 0) {
            return productRepository.findByNameIs(name);
        } else if (partial_match == 1) {
            return productRepository.findByNameContainingIgnoreCase(name.toUpperCase());
        } else throw new WrongSearchParamException(partial_match);
    }

    @GetMapping("/products/{id}")
    Product products(@PathVariable Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Update product by id
     * HTTP: PUT
     * request header: application/json
     * request body: product in json format {"id":1,"name":"BestJS2222---","version":null,"deprecationDate":null,"hypeLevel":0}
     * @param newProduct
     * @param id
     * @return ResponseEntity<Product>
     * @throws ProductNotFoundException
     */
    @PutMapping("/products/{id}")
    ResponseEntity<Product> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(newProduct.getName());
        product.setDeprecationDate(newProduct.getDeprecationDate());
        product.setVersion(newProduct.getVersion());
        product.setHypeLevel(newProduct.getHypeLevel());

        Product updated = productRepository.save(product);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/products/{id}")
    public void deleteStudent(@PathVariable long id) throws ProductNotFoundException {
        try {
            productRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

}
