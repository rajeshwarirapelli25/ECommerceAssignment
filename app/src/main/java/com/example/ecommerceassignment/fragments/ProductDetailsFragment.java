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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerceassignment.MainActivity;
import com.example.ecommerceassignment.R;
import com.example.ecommerceassignment.adapters.ProductVariantAdapter;
import com.example.ecommerceassignment.model.ProductModel;

public class ProductDetailsFragment extends Fragment {
    private View rootView;
    private MainActivity act;
    private TextView tvProductName, tvTaxName, tvTaxValue;
    private RecyclerView rvProductVariantList;
    private ProductModel productModel;
    private ProductVariantAdapter productVariantAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (MainActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_details, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        tvProductName = (TextView) rootView.findViewById(R.id.tv_ProductDetailsFragment_ProductName);
        tvTaxName = (TextView) rootView.findViewById(R.id.tv_ProductDetailsFragment_TaxName);
        tvTaxValue = (TextView) rootView.findViewById(R.id.tv_ProductDetailsFragment_TaxValue);
        rvProductVariantList = (RecyclerView) rootView.findViewById(R.id.rv_ProductsDetailsFragment_ProductVariants);
        if (getArguments() != null) {
            productModel = (ProductModel) getArguments().getSerializable("productModel");
        }
        if (productModel != null) {
            tvProductName.setText(productModel.getName());
            tvTaxName.setText(productModel.getTax_name());
            tvTaxValue.setText(productModel.getTax_value());
            productVariantAdapter = new ProductVariantAdapter(act, productModel.getVariants());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false);
            rvProductVariantList.setLayoutManager(linearLayoutManager);
            rvProductVariantList.addItemDecoration(new DividerItemDecoration(act, DividerItemDecoration.VERTICAL));

            rvProductVariantList.setAdapter(productVariantAdapter);

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_sort).setVisible(false);
    }
}



