package com.example.ecommercewebsitesystem.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "Please enter an ID")
    private String ID;

    @NotEmpty(message = "Please enter a name")
    @Size(min = 4, message = "The length of name must be more than 3")
    private String name;

}
