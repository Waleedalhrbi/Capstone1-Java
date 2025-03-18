package com.example.ecommercewebsitesystem.Controller;

import com.example.ecommercewebsitesystem.Api.ApiResponse;
import com.example.ecommercewebsitesystem.Model.MerchantStock;
import com.example.ecommercewebsitesystem.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity getAllMerchantStocks() {
        ArrayList<MerchantStock> merchantStockList = merchantStockService.getMerchantStockList();
        return ResponseEntity.status(HttpStatus.OK).body(merchantStockList);
    }


    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        int response = merchantStockService.addMerchantStock(merchantStock);

        if (response == 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Merchant stock with this ID already exists!"));
        } else if (response == 2) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Merchant stock added successfully!"));
        } else if (response == 3) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found!"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Merchant ID does not exist!"));
    }


    @PutMapping("/update/{stockID}")
    public ResponseEntity updateMerchantStock(@PathVariable String stockID, @RequestBody @Valid MerchantStock updatedMerchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        int response = merchantStockService.updateMerchantStock(stockID, updatedMerchantStock);

        if (response == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Merchant stock updated successfully!"));
        } else if (response == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Merchant stock with this ID does not exist!"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product with this ID does not exist!"));
    }


    @DeleteMapping("/delete/{stockID}")
    public ResponseEntity deleteMerchantStock(@PathVariable String stockID) {
        boolean isDeleted = merchantStockService.deleteMerchantStock(stockID);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Merchant stock deleted successfully!"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Merchant stock with this ID does not exist!"));
    }


    @PutMapping("/add-product-stock/{productID}/{merchantID}/{amount}")
    public ResponseEntity addProductStock( @PathVariable String productID, @PathVariable String merchantID, @PathVariable int amount){
        int response = merchantStockService.addProductStock(productID, merchantID, amount);
        if (response == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product stock added successfully!"));
        } else if (response == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Merchant stock with this ID does not exist!"));
        } else if (response == 3) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product ID does not exist!"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Merchant ID does not exist!"));
    }
}
