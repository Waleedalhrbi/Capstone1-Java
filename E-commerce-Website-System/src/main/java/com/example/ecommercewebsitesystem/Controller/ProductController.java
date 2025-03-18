package com.example.ecommercewebsitesystem.Controller;

import com.example.ecommercewebsitesystem.Api.ApiResponse;
import com.example.ecommercewebsitesystem.Model.Product;
import com.example.ecommercewebsitesystem.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/get")
    public ResponseEntity getAllProducts() {
        ArrayList<Product> products = productService.getProducts();

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        int response = productService.addProduct(product);

        if (response == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product with this ID already exists!"));
        } else if (response == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product added successfully!"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Category ID does not exist!"));
    }



    @PutMapping("/update/{productID}")
    public ResponseEntity updateProduct(@PathVariable String productID, @RequestBody @Valid Product updatedProduct, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        int response = productService.updateProduct(productID, updatedProduct);

        if (response == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product updated successfully!"));
        } else if (response == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category ID does not exist!"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Product with this ID does not exist!"));
    }


    @DeleteMapping("/delete/{productID}")
    public ResponseEntity deleteProduct(@PathVariable String productID) {
        boolean isDelete = productService.deleteProduct(productID);

        if (isDelete) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product deleted successfully!"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Product with this ID not found!"));
    }



    // endPoint to get price rang from max to min
    @GetMapping("/get-price-rang/{maxPrice}/{minPrice}")
    public ResponseEntity getPriceRang(@PathVariable double maxPrice, @PathVariable double minPrice) {
        ArrayList<Product> products = productService.getPriceRang(maxPrice, minPrice);

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("There is no product with this rang!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }



    @GetMapping("/recommended/{userID}")
    public ResponseEntity getRecommendedProducts(@PathVariable String userID) {
        ArrayList<Product> recommendedProducts = productService.getRecommendedProducts(userID);

        if (recommendedProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ApiResponse("User not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(recommendedProducts);
    }
}
