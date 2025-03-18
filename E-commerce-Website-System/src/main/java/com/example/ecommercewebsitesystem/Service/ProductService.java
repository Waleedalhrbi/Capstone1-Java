package com.example.ecommercewebsitesystem.Service;

import com.example.ecommercewebsitesystem.Model.Category;
import com.example.ecommercewebsitesystem.Model.MerchantStock;
import com.example.ecommercewebsitesystem.Model.Product;
import com.example.ecommercewebsitesystem.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class ProductService {

    private final CategoryService categoryService;
    private final ApplicationContext applicationContext;

    ArrayList<Product> products = new ArrayList<Product>();

    public ProductService(CategoryService categoryService, ApplicationContext applicationContext) {
        this.categoryService = categoryService;
        this.applicationContext = applicationContext;
    }

    private UserService getUserService() {
        return applicationContext.getBean(UserService.class);
    }

    private MerchantStockService getMerchantStockService() {
        return applicationContext.getBean(MerchantStockService.class);
    }
    public ArrayList<Product> getProducts() {
        return products;
    }

    public int addProduct(Product product) {

        for (Category category : categoryService.getCategories()) {
            if (category.getID().equalsIgnoreCase(product.getCategoryID())) {

                for (Product p : products) {
                    if (p.getID().equalsIgnoreCase(product.getID())) {
                        return 1;
                    }
                }
                products.add(product);
                return 2;
            }
        }
        return 3;
    }



    public int updateProduct(String productID, Product updatedProduct) {

        for (Product product : products) {
            if (product.getID().equalsIgnoreCase(productID)) {
                for (Category category : categoryService.getCategories()) {
                    if (category.getID().equalsIgnoreCase(updatedProduct.getCategoryID())) {
                        products.set(products.indexOf(product), updatedProduct);
                        return 1;
                    }
                }
                return 2;
            }
        }
        return 3;
    }

    public boolean deleteProduct(String productID) {
        for (Product product : products) {
            if (product.getID().equalsIgnoreCase(productID)) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }



    // endPoint to get the rang of prices from min price to max price
    public ArrayList<Product> getPriceRang(double maxPrice , double minPrice) {
        ArrayList<Product> productsWithPrice = new ArrayList<>();

        for (Product product : products) {
          if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                productsWithPrice.add(product);
            }
        }
        return productsWithPrice;
    }


    public ArrayList<Product> getRecommendedProducts(String userID) {

        MerchantStockService merchantStockService = getMerchantStockService();
        UserService userService = getUserService();

        for (User user : userService.getUsers()) {
            if (user.getID().equalsIgnoreCase(userID)) {

                ArrayList<Product> purchasedProducts = user.getOrderHistory();

                if (purchasedProducts.isEmpty()) {
                    return getPopularProducts();
                }

                ArrayList<String> userCategories = new ArrayList<>();
                for (Product p : purchasedProducts) {
                    if (!userCategories.contains(p.getCategoryID())) {
                        userCategories.add(p.getCategoryID());
                    }
                }

                ArrayList<Product> relatedProducts = new ArrayList<>();
                for (Product p : products) {
                    if (userCategories.contains(p.getCategoryID())) {
                        relatedProducts.add(p);
                    }
                }

                ArrayList<Product> popularProducts = getPopularProducts();
                ArrayList<Product> recommendedProducts = new ArrayList<>(relatedProducts);

                for (Product p : popularProducts) {
                    if (!recommendedProducts.contains(p)) {
                        recommendedProducts.add(p);
                    }
                }

                return recommendedProducts;
            }
        }

        return new ArrayList<>();
    }


    private ArrayList<Product> getPopularProducts() {
        ArrayList<Product> popularProducts = new ArrayList<>();
        MerchantStockService merchantStockService = getMerchantStockService();
        for (MerchantStock merchantStock : merchantStockService.getMerchantStockList()) {
            for (Product p : products) {
                if (p.getID().equalsIgnoreCase(merchantStock.getProductID())) {
                    if (!popularProducts.contains(p)) {
                        popularProducts.add(p);
                    }
                }
            }
        }
        if (popularProducts.size() > 5) {
            ArrayList<Product> topFiveProducts = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                topFiveProducts.add(popularProducts.get(i));
            }
            return topFiveProducts;
        } else {
            return popularProducts;
        }
    }


}
