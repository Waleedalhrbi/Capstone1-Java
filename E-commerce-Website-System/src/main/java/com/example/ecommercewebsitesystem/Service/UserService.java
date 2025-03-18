package com.example.ecommercewebsitesystem.Service;


import com.example.ecommercewebsitesystem.Model.Merchant;
import com.example.ecommercewebsitesystem.Model.MerchantStock;
import com.example.ecommercewebsitesystem.Model.Product;
import com.example.ecommercewebsitesystem.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class UserService {

    private   ProductService productService;
    private final MerchantService merchantService;
    private  MerchantStockService merchantStockService;
    ArrayList<User> users = new ArrayList<User>();
    public UserService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setMerchantStockService(MerchantStockService merchantStockService) {
        this.merchantStockService = merchantStockService;
    }

    public ArrayList<User> getUsers() {
        return users;
    }


    public boolean addUser(User user) {
        for (User user1 : users) {
            if (user1.getID().equalsIgnoreCase(user.getID())) {
                return false;
            }
        }

        users.add(user);
        return true;
    }


    public boolean updateUser(String ID, User user) {
        for (User userU : users) {
            if (userU.getID().equalsIgnoreCase(ID)) {
                users.set(users.indexOf(userU), user);
                return true;
            }
        }
        return false;
    }


    public boolean deleteUser(String ID) {
        for (User userD : users) {
            if (userD.getID().equalsIgnoreCase(ID)) {
                users.remove(userD);
                return true;
            }
        }
        return false;
    }


    public int buyProduct(String userID, String productID, String merchantID) {
        for (User userB : users) {
            if (userB.getID().equalsIgnoreCase(userID)) {
                for (Merchant merchant : merchantService.getMerchants()){
                    if (merchant.getID().equalsIgnoreCase(merchantID)) {
                        for (Product product : productService.getProducts()){
                            for (MerchantStock merchantStock : merchantStockService.getMerchantStockList()){
                                if (merchantStock.getProductID().equalsIgnoreCase(product.getID())){
                                        if (product.getID().equalsIgnoreCase(productID)){
                                            if (merchantStock.getStock() > 0) {
                                                if (userB.getBalance() >= product.getPrice()) {
                                                    userB.setBalance(userB.getBalance() - product.getPrice());
                                                    merchantStock.setStock(merchantStock.getStock() - 1);
                                                    addToOrderHistory(userB, product);
                                                    product.setStatus("Purchased");
                                                    return 1;
                                                }
                                                return 2;
                                            }
                                            return 3;
                                        }
                                    return 4;
                                }
                            }
                        }
                    }
                }
                return 5;
            }
        }
        return 6;
    }


    public int rechargeBalance(String userID, double amount) {
        for (User userB : users) {
            if (userB.getID().equalsIgnoreCase(userID)) {
                if (amount > 0){
                    userB.setBalance(userB.getBalance() + amount);
                    return 1;
                }
                return 2;
            }
        }
        return 3;
    }


    private void addToOrderHistory(User user, Product product) {
        if (user.getOrderHistory() == null) {
            user.setOrderHistory(new ArrayList<>());
        }
        user.getOrderHistory().add(product);
    }

    public ArrayList<Product> getUserOrderHistory(String userID) {
        for (User user : users) {
            if (user.getID().equalsIgnoreCase(userID)) {
                return user.getOrderHistory();
            }
        }
        return new ArrayList<>();
    }




    public int returnProduct(String userID, String productID) {
        for (User user : users) {
            if (user.getID().equalsIgnoreCase(userID)) {
                for (Product product : user.getOrderHistory()) {
                    if (product.getID().equalsIgnoreCase(productID) && "Purchased".equals(product.getStatus())) {
                        user.setBalance(user.getBalance() + product.getPrice());
                        product.setStatus("Returned");
                        for (MerchantStock merchantStock : merchantStockService.getMerchantStockList()) {
                            if (merchantStock.getProductID().equalsIgnoreCase(productID)) {
                                merchantStock.setStock(merchantStock.getStock() + 1);
                                return 1;
                            }
                        }
                    }
                }
                return 2;
            }
        }
        return 3;
    }


}
