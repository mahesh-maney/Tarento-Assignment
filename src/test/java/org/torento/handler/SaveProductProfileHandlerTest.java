package org.torento.handler;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.torento.dto.SaveProductProfileDto;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@MicronautTest
class SaveProductProfileHandlerTest {

    @Inject
    SaveProductProfileHandler saveProductProfileHandler;

    @Test
    void addProductProfile() {
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
        final String response = saveProductProfileHandler.addProductProfile(listSaveProductProfile);
        Assertions.assertNotNull(response);
    }

    @Test
    void addEmptyProductProfile() {
        List<SaveProductProfileDto> listSaveProductProfile = new ArrayList<>();
        final String response = saveProductProfileHandler.addProductProfile(listSaveProductProfile);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("No Profile found",response);
    }

}