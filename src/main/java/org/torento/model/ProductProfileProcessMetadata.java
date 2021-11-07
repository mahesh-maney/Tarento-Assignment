package org.torento.model;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import javax.inject.Singleton;
import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Singleton
public class ProductProfileProcessMetadata implements Serializable {
    private String productID;
    private String productName;
    private String productDescription;
    private String productManufacturer;
    private Float  productWeight;
    private String persistenceLayerSaveId;
    private String productProfileProcessId;
}
