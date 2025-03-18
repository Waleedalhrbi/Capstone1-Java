package com.example.ecommercewebsitesystem.Controller;

import com.example.ecommercewebsitesystem.Api.ApiResponse;
import com.example.ecommercewebsitesystem.Model.Product;
import com.example.ecommercewebsitesystem.Model.User;
import com.example.ecommercewebsitesystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        ArrayList<User> users = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isAdded = userService.addUser(user);

        if (isAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User added successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("User with this ID already exists!"));
        }
    }


    @PutMapping("/update/{ID}")
    public ResponseEntity updateUser(@PathVariable String ID, @RequestBody @Valid User updatedUser, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isUpdate = userService.updateUser(ID, updatedUser);

        if (isUpdate) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User updated successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User with this ID does not exist!"));
        }
    }


    @DeleteMapping("/delete/{ID}")
    public ResponseEntity deleteUser(@PathVariable String ID) {
        boolean isDelete = userService.deleteUser(ID);

        if (isDelete) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User deleted successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User with this ID does not exist!"));
        }
    }

    @PutMapping("/buy-product/{userID}/{productID}/{merchantID}")
    public ResponseEntity buyProduct(@PathVariable String userID, @PathVariable String productID, @PathVariable String merchantID) {
        int response = userService.buyProduct(userID, productID, merchantID);

        if (response == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product successfully bought"));
        } else if (response == 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("You don't have enough money to buy this product"));
        } else if (response == 3) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The stock is not enough"));
        } else if (response == 4) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found"));
        } else if (response == 5) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Merchant not found"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("User not found"));
    }




    // endPoint to  recharge the balance of user
    @PutMapping("/recharge-balance/{userID}/{amount}")
    public ResponseEntity rechargeBalance(@PathVariable String userID, @PathVariable double amount) {
        int responseNumber = userService.rechargeBalance(userID, amount);
        if (responseNumber == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Recharge successfully!"));
        } else if (responseNumber == 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Amount can not be negative!"));
        } else if (responseNumber == 3) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User can not be found!"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Something went wrong"));
    }


    // endPoint to get order history for the user
    @GetMapping("/get-user-order-history/{userID}")
    public ResponseEntity getUserOrderHistory(@PathVariable String userID) {
        ArrayList<Product> orderHistory = userService.getUserOrderHistory(userID);

        if (orderHistory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("There are no user order history"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderHistory);
    }


    // endPoint to return product that user Purchased
    @PostMapping("/return-product/{userID}/{productID}")
    public ResponseEntity returnProduct(@PathVariable String userID, @PathVariable String productID) {
        int result = userService.returnProduct(userID, productID);

        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product returned successfully!"));
        } else if (result == 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Cannot return product. It was not purchased or has already been returned"));
        } else if (result == 3) {
            return ResponseEntity.badRequest().body(new ApiResponse("User not found."));
        } else {
            return ResponseEntity.internalServerError().body(new ApiResponse("An error occurred while processing the request."));
        }
    }


}