package com.example.ecommercewebsitesystem.Controller;
import com.example.ecommercewebsitesystem.Api.ApiResponse;
import com.example.ecommercewebsitesystem.Model.Merchant;
import com.example.ecommercewebsitesystem.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getAllMerchant() {
        ArrayList<Merchant> merchants = merchantService.getMerchants();

        return ResponseEntity.status(HttpStatus.OK).body(merchants);
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isAdded = merchantService.addMerchant(merchant);
        if (isAdded) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Merchant added successfully"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Merchant already exists"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isUpdated = merchantService.updateMerchant(id, merchant);

        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Merchant updated successfully"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Merchant not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id) {
        boolean isDeleted = merchantService.deleteMerchant(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Merchant deleted successfully"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Merchant not found"));
    }
}
