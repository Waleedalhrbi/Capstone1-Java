package com.example.ecommercewebsitesystem.Service;

import com.example.ecommercewebsitesystem.Model.Category;
import com.example.ecommercewebsitesystem.Model.Merchant;
import com.example.ecommercewebsitesystem.Model.MerchantStock;
import com.example.ecommercewebsitesystem.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class MerchantStockService {

    final private ProductService productService;
      private MerchantService merchantService;

    ArrayList<MerchantStock> merchantStockList = new ArrayList<>();

    public MerchantStockService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }
    public ArrayList<MerchantStock> getMerchantStockList() {
        return merchantStockList;
    }

    public int addMerchantStock(MerchantStock merchantStock) {

        for (Merchant merchant : merchantService.getMerchants()){
            if (merchant.getID().equalsIgnoreCase(merchantStock.getMerchantId())){
                for (Product product : productService.getProducts()) {
                    if (product.getID().equalsIgnoreCase(merchantStock.getProductID())) {

                        for (MerchantStock merchantStock1 : merchantStockList) {
                            if (merchantStock1.getID().equalsIgnoreCase(merchantStock.getID())) {
                                return 1;
                            }
                        }
                        merchantStockList.add(merchantStock);
                        return 2;
                    }
                } //
                return 3;
            }
        }
        return 4;
    }


    public int updateMerchantStock(String stockID, MerchantStock updatedMerchantStock) {
        for (Product product : productService.getProducts()) {
            if (product.getID().equalsIgnoreCase(updatedMerchantStock.getProductID())) {
                for (MerchantStock merchantStockU : merchantStockList) {
                    if (merchantStockU.getID().equalsIgnoreCase(stockID)) {
                        merchantStockList.set(merchantStockList.indexOf(merchantStockU), updatedMerchantStock);
                        return 1;
                    }
                }
                return 2;
            }
        }
        return 3;
    }


    public boolean deleteMerchantStock(String stockID) {
        for (MerchantStock merchantStock : merchantStockList) {
            if (merchantStock.getID().equalsIgnoreCase(stockID)) {
                merchantStockList.remove(merchantStock);
                return true;
            }
        }
        return false;
    }

    public int addProductStock(String productID, String merchantID, int amount) {

        for (Merchant merchantService1 : merchantService.getMerchants()){
                    if (merchantService1.getID().equalsIgnoreCase(merchantID)) {
                        for (Product productService1 : productService.getProducts()) {
                            if (productService1.getID().equalsIgnoreCase(productID)) {
                                for (MerchantStock merchantStock1 : merchantStockList) {
                                    if (merchantStock1.getProductID().equalsIgnoreCase(productService1.getID())) {
                                        merchantStock1.setStock(merchantStock1.getStock() + amount);
                                        return 1;
                                    }
                                }
                                return 2;
                            }
                        }
                        return 3;
                    }
        }
        return 4;
    }


}
