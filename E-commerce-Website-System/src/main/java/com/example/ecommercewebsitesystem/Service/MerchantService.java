package com.example.ecommercewebsitesystem.Service;

import com.example.ecommercewebsitesystem.Model.Category;
import com.example.ecommercewebsitesystem.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<Merchant>();

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public boolean addMerchant(Merchant merchant) {
        for (Merchant merchant1 : merchants) {
            if(merchant1.getID().equalsIgnoreCase(merchant.getID())) {
                return false;
            }
        }

        merchants.add(merchant);
        return true;
    }

    public boolean updateMerchant(String ID ,Merchant merchant) {

        for (Merchant merchantU : merchants) {
            if(merchantU.getID().equalsIgnoreCase(ID)) {
                merchants.set(merchants.indexOf(merchantU), merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String ID) {
        for (Merchant merchantD : merchants) {
            if(merchantD.getID().equalsIgnoreCase(ID)) {
                merchants.remove(merchantD);
                return true;
            }
        }
        return false;
    }
}
