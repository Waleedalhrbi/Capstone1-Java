package com.example.ecommercewebsitesystem.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "You must Enter ID")
    private String ID;

    @NotEmpty(message = "Please enter a name")
    @Size(min = 4, message = "The length of name must be more than 3")
    private String name;

    @NotNull(message = "Please enter a price")
    @Positive(message = "Price must be positive number")
    private double price;

    @NotEmpty(message = "Please enter a category ID")
    private String categoryID;



    private String status = "Available";
}
