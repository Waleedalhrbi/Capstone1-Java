package com.example.ecommercewebsitesystem.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "Please enter an ID")
    private String ID;

    @NotEmpty(message = "Please enter a product id")
    private String productID;

    @NotEmpty(message = "Please enter merchant ID")
    private String merchantId;
    @NotNull(message = "Please enter stock")
    @Min(value = 11, message = "Stock must be more than 10")
    private int stock;
}
