package com.example.ecommerceassignment.model;

import java.util.ArrayList;

public class CategoryModel {
    private String id, name;
    private ArrayList<ProductModel> products;
    private ArrayList<CategoryModel> child_categories;

    public CategoryModel() {
    }

    public CategoryModel(String id, String name, ArrayList<ProductModel> products, ArrayList<CategoryModel> child_categories) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.child_categories = child_categories;
    }

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

    public ArrayList<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }

    public ArrayList<CategoryModel> getChild_categories() {
        return child_categories;
    }

    public void setChild_categories(ArrayList<CategoryModel> child_categories) {
        this.child_categories = child_categories;
    }
}
