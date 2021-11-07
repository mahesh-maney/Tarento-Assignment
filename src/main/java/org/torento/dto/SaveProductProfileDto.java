package org.torento.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import javax.inject.Singleton;
import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@Singleton
public class SaveProductProfileDto implements Serializable {
    private String productID;
    private String productName;
    private String productDescription;
    private String productManufacturer;
    private float productPrice;
    private Float productWeight;
}
