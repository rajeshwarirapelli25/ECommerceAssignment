package com.example.ecommerceassignment.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductModel implements Serializable {
    private String id, name, date_added, tax_name, tax_value;
    private ArrayList<VariantModel> variants;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public ArrayList<VariantModel> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<VariantModel> variants) {
        this.variants = variants;
    }

    public String getTax_name() {
        return tax_name;
    }

    public void setTax_name(String tax_name) {
        this.tax_name = tax_name;
    }

    public String getTax_value() {
        return tax_value;
    }

    public void setTax_value(String tax_value) {
        this.tax_value = tax_value;
    }

    public class VariantModel {
        private String id, color, size, price;

        public VariantModel() {
        }

        public VariantModel(String id, String color, String size, String price) {
            this.id = id;
            this.color = color;
            this.size = size;
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

}
