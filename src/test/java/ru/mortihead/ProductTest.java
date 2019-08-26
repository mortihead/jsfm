package ru.mortihead;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.mortihead.model.Product;
import ru.mortihead.service.ProductService;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
public class ProductTest {

    @Autowired
    private ProductService productService;


    @Test
    public void WhetAddProduct_ThenProductIsReturned(){
        Product product = new Product( "Angular", "1.7.8", null, 10);
        productService.add(product);
        List<?> products =  productService.findByNameIs("Angular");

        System.out.println("findByName is 'Angular': " + products);
        assertEquals(products.size(), 1);
        assertEquals(products.get(0), product);
    }

    @Test
    public void WhenSearchByNameContainingIgnoreCaseJS_ThenProductWithJS_in_Name_IsReturned(){
        productService.add(new Product("React", "16.9.0", null, 9));
        productService.add(new Product("Node.js", "12.8.0", null, 8));
        productService.add(new Product("Backbone.js", "1.4.0", null, 8));

        List<Product> products = productService.findByNameContainingIgnoreCase("JS");

        System.out.println("findByNameContainingIgnoreCase 'js':" + products);
        assertEquals(products.size(), 2);
    }

}
