package com.example.ecommerceassignment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecommerceassignment.MainActivity;
import com.example.ecommerceassignment.R;
import com.example.ecommerceassignment.model.ProductModel;

import java.util.ArrayList;

public class ProductVariantAdapter extends RecyclerView.Adapter<ProductVariantAdapter.ViewHolder> {
    private ArrayList<ProductModel.VariantModel> listProductVariants;
    private Context context;
    private LayoutInflater inflater;
    private MainActivity act;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public ProductVariantAdapter(Context context, ArrayList<ProductModel.VariantModel> listProductVariants) {
        this.context = context;
        this.listProductVariants = listProductVariants;
        this.act = (MainActivity) context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductVariantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.listitem_product_details, viewGroup, false);
        return new ProductVariantAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVariantAdapter.ViewHolder viewHolder, int i) {
        if (i == 0) {
            viewHolder.ll_ProductDetails_Layout.setBackgroundColor(context.getResources().getColor(R.color.background_gray));
            viewHolder.tvProductColor.setText("Color");
            viewHolder.tvProductSize.setText("Size");
            viewHolder.tvProductPrice.setText("Price");
        } else {
            final ProductModel.VariantModel model = getItem(i);
            viewHolder.ll_ProductDetails_Layout.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.tvProductColor.setText(model.getColor());
            viewHolder.tvProductSize.setText(model.getSize() == null || model.getSize().equalsIgnoreCase("null") ? "-" : model.getSize());
            viewHolder.tvProductPrice.setText(model.getPrice());
        }

    }

    @Override
    public int getItemCount() {
        return listProductVariants.size() + 1;
    }

    private ProductModel.VariantModel getItem(int position) {
        return listProductVariants.get(position - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductColor, tvProductSize, tvProductPrice;
        LinearLayout ll_ProductDetails_Layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductColor = (TextView) itemView.findViewById(R.id.tv_ProductColor);
            tvProductSize = (TextView) itemView.findViewById(R.id.tv_ProductSize);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tv_ProductPrice);
            ll_ProductDetails_Layout = (LinearLayout) itemView.findViewById(R.id.ll_ProductDetails_layout);
        }
    }
}

