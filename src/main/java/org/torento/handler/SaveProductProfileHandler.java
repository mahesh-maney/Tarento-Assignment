package org.torento.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torento.dto.SaveProductProfileDto;
import org.torento.kafka.SendToPersistenceLayer;
import org.torento.model.ProductProfileProcessMetadata;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class SaveProductProfileHandler {

    @Inject
    private SendToPersistenceLayer sendToPersistenceLayer;

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveProductProfileHandler.class);

    public String addProductProfile(final List<SaveProductProfileDto> listSaveProductProfileDto) {
        String productProcessId = UUID.randomUUID().toString();
        LOGGER.info("Saving product profile {}", listSaveProductProfileDto);
        final List<ProductProfileProcessMetadata> listProductProfileProcessMetadata = listSaveProductProfileDto.stream().filter(product -> product.getProductID() != null).map(product -> {
            final ProductProfileProcessMetadata productProfile = ProductProfileProcessMetadata.builder()
                    .productID(product.getProductID())
                    .productName(product.getProductName())
                    .productDescription(product.getProductDescription())
                    .productManufacturer(product.getProductManufacturer())
                    .productWeight(product.getProductWeight())
                    .productProfileProcessId(productProcessId)
                    .build();
            sendToPersistenceLayer.sendMessage(product.getProductID(),productProfile);
            return productProfile;
        }).collect(Collectors.toList());
        return listProductProfileProcessMetadata.size() > 0 ? productProcessId : "No Profile found";
    }

}
