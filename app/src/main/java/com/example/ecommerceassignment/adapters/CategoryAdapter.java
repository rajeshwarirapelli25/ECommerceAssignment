package com.example.ecommerceassignment.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecommerceassignment.MainActivity;
import com.example.ecommerceassignment.R;
import com.example.ecommerceassignment.fragments.ProductsFragment;
import com.example.ecommerceassignment.model.CategoryModel;
import com.example.ecommerceassignment.utils.LevelBeamView;

import java.util.List;

import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListAdapter;

public class CategoryAdapter extends MultiLevelListAdapter {

    private Context context;
    private LayoutInflater inflater;
    private MainActivity act;

    public CategoryAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        act = (MainActivity) context;
    }

    @Override
    public List<?> getSubObjects(Object object) {
        return ((CategoryModel) object).getChild_categories();
    }

    @Override
    public boolean isExpandable(Object object) {
        return ((CategoryModel) object).getChild_categories().size() > 0;
    }

    @Override
    public View getViewForObject(final Object object, View convertView, final ItemInfo itemInfo) {
        final CategoryModel model = (CategoryModel) object;
        convertView = inflater.inflate(R.layout.listitem_categories, null);

        TextView infoView = (TextView) convertView.findViewById(R.id.tv_CategoryAdapter_CategoryName);
        ImageView arrowView = (ImageView) convertView.findViewById(R.id.iv_CategoryAdapter_arrow);

        LevelBeamView levelBeamView = (LevelBeamView) convertView.findViewById(R.id.dataItemLevelBeam);
        LinearLayout llListItem = (LinearLayout) convertView.findViewById(R.id.ll_CategoryAdapter_Layout);
        LinearLayout llRowItem = (LinearLayout) convertView.findViewById(R.id.ll_RowLayout);

        infoView.setText(((CategoryModel) object).getName());
        if (itemInfo.isExpandable()) {
            arrowView.setVisibility(View.VISIBLE);
            arrowView.setImageResource(itemInfo.isExpanded() ?
                    R.drawable.arrow_up : R.drawable.arrow_down);
        } else {
            arrowView.setVisibility(View.GONE);
        }
        if (!itemInfo.isExpandable()) {
            llListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductsFragment fragment = new ProductsFragment();
                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("productList", model.getProducts());
                    bundle.putSerializable("category_id", model.getId());

                    fragment.setArguments(bundle);
                    act.addFragment(fragment);
                }
            });
        }
        levelBeamView.setLevel(itemInfo.getLevel());
        return convertView;
    }

}
