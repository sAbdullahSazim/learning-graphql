package com.sazim.io.onboarding.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sazim.io.onboarding.models.Product;
import lombok.Builder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.HashMap;
import java.util.Objects;

@GraphQlTest(ProductControllerTest.class)
class ProductControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private ObjectMapper om = new ObjectMapper();

    @Builder
    record ProductCreateReq(
            String name,
            String description,
            String imageUrl
    ) {
    }

    ;

    @Test
    void products() {
    }

    @Test
    void createProduct() throws JsonProcessingException {
        GraphQlTester tester = this.graphQlTester.mutate().build();
        ProductCreateReq product = new ProductCreateReq("Test-Product-name-01", "Test-Product-description-01", "imageUrl-01");

        GraphQlTester.Response execute = tester.documentName("createProduct")
                .variable("productCreateReq", om.readValue(ow.writeValueAsString(product), HashMap.class))
                .execute();

        execute.path("name")
                .entity(String.class)
                .satisfies(name -> name.equals(product.name))
                .path("description")
                .entity(String.class)
                .satisfies(description -> description.equals(product.description))
                .path("id")
                .entity(Long.class)
                .satisfies(Objects::nonNull);
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}