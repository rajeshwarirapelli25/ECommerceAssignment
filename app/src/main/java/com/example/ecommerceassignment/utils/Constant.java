package com.example.ecommerceassignment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Constant {

    public enum DrawerMenu {
        CATEGORIES, SUBCATEGORIES, PRODUCTS, PRODUCTDETAILS
    }

    public static final String baseUrl = "https://stark-spire-93433.herokuapp.com/";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
