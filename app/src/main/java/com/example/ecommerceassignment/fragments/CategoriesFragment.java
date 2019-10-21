package com.example.ecommerceassignment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceassignment.MainActivity;
import com.example.ecommerceassignment.R;
import com.example.ecommerceassignment.adapters.CategoryAdapter;
import com.example.ecommerceassignment.model.CategoryModel;
import com.example.ecommerceassignment.utils.EcommerceDB;

import java.util.ArrayList;

import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.NestType;


public class CategoriesFragment extends Fragment {
    private View rootView;
    private MainActivity act;
    private MultiLevelListView lvCategories;
    private EcommerceDB ecommerceDB;
    private ArrayList<CategoryModel> listCategories;
    private CategoryAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvCategories = (MultiLevelListView) rootView.findViewById(R.id.lv_CategoryFragment_CategoryList);
        ecommerceDB = EcommerceDB.getInstance(act);
        listCategories = ecommerceDB.getCategories("0");
        adapter = new CategoryAdapter(act);

        lvCategories.setAdapter(adapter);
        lvCategories.setNestType(NestType.SINGLE);
        adapter.setDataItems(listCategories);
        adapter.notifyDataSetChanged();
    }
}


