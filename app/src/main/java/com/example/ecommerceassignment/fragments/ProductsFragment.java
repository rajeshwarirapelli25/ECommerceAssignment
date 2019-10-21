package com.example.ecommerceassignment.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ecommerceassignment.MainActivity;
import com.example.ecommerceassignment.R;
import com.example.ecommerceassignment.adapters.ProductAdapter;
import com.example.ecommerceassignment.adapters.RankAdapter;
import com.example.ecommerceassignment.model.ProductModel;
import com.example.ecommerceassignment.model.RankModel;
import com.example.ecommerceassignment.utils.EcommerceDB;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {
    private View rootView;
    private MainActivity act;
    private ProductAdapter productAdapter;
    private RecyclerView rvProductList;
    private ArrayList<ProductModel> listProducts;
    private AlertDialog dialog;
    private ArrayList<RankModel> listSortOptions;
    private String category_id;
    private EcommerceDB ecommerceDB;

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
        setHasOptionsMenu(true);
        ecommerceDB = EcommerceDB.getInstance(act);
        listProducts = new ArrayList<>();
        rvProductList = (RecyclerView) rootView.findViewById(R.id.rv_ProductsFragment_ProductsList);
        if (getArguments() != null) {
//            listProducts = (ArrayList<ProductModel>) getArguments().getSerializable("productList");
            category_id = getArguments().getString("category_id");
        }
        listProducts.addAll(ecommerceDB.getProducts(category_id, ""));
        listSortOptions = ecommerceDB.getSortOptions();
        productAdapter = new ProductAdapter(act, listProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false);
        rvProductList.setLayoutManager(linearLayoutManager);
        rvProductList.addItemDecoration(new DividerItemDecoration(act, DividerItemDecoration.VERTICAL));
        rvProductList.setAdapter(productAdapter);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_sort).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setCancelable(false);
            View view = LayoutInflater.from(act).inflate(R.layout.dialog_sort_options, null);
            builder.setView(view);
            builder.setTitle("Sort By");
            ListView lvSortList = (ListView) view.findViewById(R.id.lv_SortOptionsList);
            final RankAdapter rankAdapter = new RankAdapter(act, listSortOptions);
//            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(act, R.layout.listitem_sort_options, R.id.tv_SortRankName, listSortOptions);
            lvSortList.setAdapter(rankAdapter);
            lvSortList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RankModel model = listSortOptions.get(position);
                    resetSorting(position);
                    rankAdapter.notifyDataSetChanged();
                    listProducts.clear();
                    listProducts.addAll(ecommerceDB.getProducts(category_id, model.getRank()));
                    productAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Clear Sorting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listProducts.clear();
                    listProducts.addAll(ecommerceDB.getProducts(category_id, ""));
                    listSortOptions.clear();
                    listSortOptions.addAll(ecommerceDB.getSortOptions());
                    rankAdapter.notifyDataSetChanged();
                    productAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            dialog = builder.create();
            dialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void resetSorting(int position) {
        for (RankModel model : listSortOptions) {
            if (model.getRank().equalsIgnoreCase(listSortOptions.get(position).getRank())) {
                model.setSelected(true);
            } else {
                model.setSelected(false);
            }
        }
    }
}



