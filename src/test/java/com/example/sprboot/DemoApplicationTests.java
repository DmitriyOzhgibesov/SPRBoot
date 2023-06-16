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
        ResponseEntity<String> forEntityDev = restTemplate.getForEntity("http://localhost:" + myDevApp.getMappedPort(8080)  + "/profile", String.class);
        System.out.println(forEntityDev.getBody());
        Assertions.assertEquals(forEntityDev.getBody(), "Current profile is dev");
    }

    @Test
    void contextLoadsProd() {
        ResponseEntity<String> forEntityProd = restTemplate.getForEntity("http://localhost:" + myProdApp.getMappedPort(8081) + "/profile", String.class);
        System.out.println(forEntityProd.getBody());
        Assertions.assertEquals(forEntityProd.getBody(), "Current profile is production");
    }
}