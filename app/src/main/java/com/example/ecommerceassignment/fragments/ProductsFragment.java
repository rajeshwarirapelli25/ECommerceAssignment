package com.example.ecommerceassignment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceassignment.MainActivity;
import com.example.ecommerceassignment.R;
import com.example.ecommerceassignment.adapters.ProductAdapter;
import com.example.ecommerceassignment.model.ProductModel;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {
    private View rootView;
    private MainActivity act;
    private ProductAdapter productAdapter;
    private RecyclerView rvProductList;
    private ArrayList<ProductModel> listProducts;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (MainActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_products, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvProductList = (RecyclerView) rootView.findViewById(R.id.rv_ProductsFragment_ProductsList);
        if (getArguments() != null) {
            listProducts = (ArrayList<ProductModel>) getArguments().getSerializable("productList");
        }
        productAdapter = new ProductAdapter(act, listProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false);
        rvProductList.setLayoutManager(linearLayoutManager);
        rvProductList.addItemDecoration(new DividerItemDecoration(act, DividerItemDecoration.VERTICAL));
        rvProductList.setAdapter(productAdapter);
    }


}



