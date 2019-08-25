package ru.mortihead;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mortihead.model.Product;
import ru.mortihead.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTests {
    private static final ObjectMapper om = new ObjectMapper();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ProductRepository productRepository;



    @Before
    public void init() {
        Optional<Product> product = Optional.of(new Product(1, "AngularJS", "1.7.8", null, 10));
        when(productRepository.findById(1L)).thenReturn(product);
    }

    //http://127.0.0.1:8080/addproduct?name=BestJS&version=1.0.2&deprecation_date=01.01.2020&hype_level=10
    @Test
    public void testAddProduct() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        String url = "/addproduct?name=BestJS&version=1.0.2&deprecation_date=01.01.2020&hype_level=10";
        System.out.println(url);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("0", response.getBody());
    }

    @Test
    public void testListProduct() throws Exception {
        List<Product> js_frameworks = Arrays.asList(
                new Product(2, "React", "16.9.0", null, 9),
                new Product(3, "Node.js", "12.8.0", null, 8));
        when(productRepository.findAll()).thenReturn(js_frameworks);

        String expected = om.writeValueAsString(js_frameworks);

        ResponseEntity<String> response = restTemplate.getForEntity("/list", String.class);
        System.out.println(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


    @Test
    public void testSearchByName() throws Exception {
        List<Product> js_frameworks = Arrays.asList(
                new Product(2, "React", "16.9.0", null, 9));
        when(productRepository.findByNameIs("React")).thenReturn(js_frameworks);

        String expected = om.writeValueAsString(js_frameworks);

        ResponseEntity<String> response = restTemplate.getForEntity("/search?name=React", String.class);
        System.out.println(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testSearchByPartialName() throws Exception {
        List<Product> js_frameworks = Arrays.asList(
                new Product(3, "Node.js", "12.8.0", null, 8),
                new Product(4, "Backbone.js", "1.4.0", null, 8));
        when(productRepository.findByNameContainingIgnoreCase("js")).thenReturn(js_frameworks);

        String expected = om.writeValueAsString(js_frameworks);

        ResponseEntity<String> response = restTemplate.getForEntity("/search?name=js&partial_match=1", String.class);
        System.out.println(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
}