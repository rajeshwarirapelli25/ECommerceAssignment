package com.example.ecommerceassignment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerceassignment.R;
import com.example.ecommerceassignment.model.ProductModel;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<ProductModel> listProducts;
    private Context context;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, ArrayList<ProductModel> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.listitem_products, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProductModel model = listProducts.get(i);
        viewHolder.tvProductName.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_ProductName);
        }
    }
}
