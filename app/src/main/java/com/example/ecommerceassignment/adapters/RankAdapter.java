package com.example.ecommerceassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ecommerceassignment.R;
import com.example.ecommerceassignment.model.RankModel;

import java.util.ArrayList;

public class RankAdapter extends BaseAdapter {
    private ArrayList<RankModel> listRanks;
    private Context context;

    public RankAdapter(Context context, ArrayList<RankModel> listRanks) {
        this.listRanks = listRanks;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listRanks.size();
    }

    @Override
    public Object getItem(int position) {
        return listRanks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_sort_options, null);
        RankModel model = listRanks.get(position);
        TextView tvRankName = (TextView) view.findViewById(R.id.tv_SortRankName);
        tvRankName.setText(model.getRank());
        tvRankName.setBackgroundColor(context.getResources().getColor(model.isSelected() ? R.color.background_gray : R.color.white));
        return view;
    }
}