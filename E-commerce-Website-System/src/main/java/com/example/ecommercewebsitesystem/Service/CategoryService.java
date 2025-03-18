package com.example.ecommercewebsitesystem.Service;

import com.example.ecommercewebsitesystem.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<Category>();

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public boolean addCategory(Category category) {
        for (Category c : categories) {
            if(c.getID().equalsIgnoreCase(category.getID())) {
                return false;
            }
        }

        categories.add(category);
        return true;
    }

    public boolean updateCategory(String ID ,Category category) {

        for (Category c : categories) {
            if(c.getID().equalsIgnoreCase(ID)) {
                categories.set(categories.indexOf(c), category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String ID) {
        for (Category c : categories) {
            if(c.getID().equalsIgnoreCase(ID)) {
                categories.remove(c);
                return true;
            }
        }
        return false;
    }
}
