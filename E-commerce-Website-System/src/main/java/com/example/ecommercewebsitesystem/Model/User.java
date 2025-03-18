package com.example.ecommercewebsitesystem.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "PLease enter an ID")
    private String ID;

    @NotEmpty(message = "Please enter user name")
    @Size(min = 6, message = "length of user name must be more than 5")
    private String username;

    @NotEmpty(message = "Please enter Password")
    @Size(min = 7, message = "Password must be longer than 6 characters.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain at least one letter and one digit")
    private String password;

    @NotEmpty(message = "Please enter your email")
    @Email
    private String email;

    @NotEmpty(message = "Please enter a role")
    @Pattern(regexp = "Admin|Customer")
    private String role;


    @NotNull(message = "Please enter balance")
    @Positive(message = "Balance must be a positive number")
    private double balance;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArrayList<Product> orderHistory = new ArrayList<>();
}
