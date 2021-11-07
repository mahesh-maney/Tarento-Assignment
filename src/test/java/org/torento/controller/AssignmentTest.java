package org.torento.controller;

import io.micronaut.core.annotation.Order;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.torento.dto.SaveProductProfileDto;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@MicronautTest
class AssignmentTest {

    @Inject
    EmbeddedApplication application;

    @Inject
    EmbeddedServer server;

    private HttpClient client;

    @BeforeEach
    void setUp() throws MalformedURLException {
        client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
    }

    @Test
    @Order(1)
    void testApplicationWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    @Order(2)
    public void testAddProductProfile() throws MalformedURLException {
        List<SaveProductProfileDto> listSaveProductProfile = new ArrayList<>();
        final SaveProductProfileDto product = SaveProductProfileDto.builder()
                .productID("A101")
                .productName("Asus Laptop")
                .productDescription("Asus Zenbook laptop")
                .productManufacturer("Asus")
                .productPrice(61990)
                .productWeight(1450f)
                .build();
        listSaveProductProfile.add(product);
        final HttpResponse<ArrayList> exchange = client.toBlocking().exchange(HttpRequest.POST("/torento/assignment/product/profile", listSaveProductProfile), Argument.of(ArrayList.class));
        Assertions.assertNotNull(exchange);
        Assertions.assertEquals(HttpStatus.OK,exchange.status());
        Assertions.assertNotNull(exchange.reason());
        Assertions.assertEquals(HttpStatus.OK.getCode(), exchange.code());
    }

    @Test
    @Order(3)
    public void testAddEmptyProductProfile() throws MalformedURLException {
        List<SaveProductProfileDto> listSaveProductProfile = new ArrayList<>();
        final HttpResponse<ArrayList> exchange = client.toBlocking().exchange(HttpRequest.POST("/torento/assignment/product/profile", listSaveProductProfile), Argument.of(ArrayList.class));
        Assertions.assertNotNull(exchange);
        Assertions.assertEquals(HttpStatus.NO_CONTENT,exchange.status());
        Assertions.assertNotNull(exchange.reason());
        Assertions.assertEquals("No Profile found",exchange.reason());
        Assertions.assertEquals(HttpStatus.NO_CONTENT.getCode(), exchange.code());
    }

    @AfterEach
    void tearDown() {
        client = null;
    }

}
