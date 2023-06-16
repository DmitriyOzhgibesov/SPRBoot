package com.example.sprboot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    private static GenericContainer<?> myDevApp = new GenericContainer<>("devapp").withExposedPorts(8080);
    private static GenericContainer<?> myProdApp = new GenericContainer<>("prodapp").withExposedPorts(8081);
    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    public static void setUp() {
        myDevApp.start();
        myProdApp.start();
    }

    @Test
    void contextLoadsDev() {
        Integer mappedPortDev = myDevApp.getMappedPort(8080);

        ResponseEntity<String> forEntityDev = restTemplate.getForEntity("http://localhost:" + myDevApp.getMappedPort(8080), String.class);

        System.out.println(forEntityDev.getBody());
        System.out.println(mappedPortDev);

        Assertions.assertEquals(mappedPortDev, 8080);
    }

    @Test
    void contextLoadsProd() {
        Integer mappedPortProd = myProdApp.getMappedPort(8081);

        ResponseEntity<String> forEntityProd = restTemplate.getForEntity("http://localhost:" + myProdApp.getMappedPort(8081), String.class);

        System.out.println(forEntityProd.getBody());
        System.out.println(mappedPortProd);

        Assertions.assertEquals(mappedPortProd, 8081);
    }
}