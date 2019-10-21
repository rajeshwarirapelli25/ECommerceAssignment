package com.example.ecommerceassignment.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ecommerceassignment.model.CategoryModel;
import com.example.ecommerceassignment.model.ProductModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EcommerceDB extends SQLiteOpenHelper {
    public static EcommerceDB ecommercedb;
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ecommercedb.db";
    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_PRODUCTS = "products";
    public static final String TABLE_PRODUCT_VARIANT = "product_variant";
    public static final String TABLE_PRODUCT_TAX = "product_tax";
    public static final String TABLE_RANKING = "ranking";

    private SQLiteDatabase db;

    private String categories = "create table if not exists " + TABLE_CATEGORIES + " (id integer primary key, name text,parent_id int default 0 , child_categories text);";
    private String products = "create table if not exists " + TABLE_PRODUCTS + " (id integer primary key,category_id text, name text,date_added text,tax_name text,tax_value text);";
    private String product_variants = "create table if not exists " + TABLE_PRODUCT_VARIANT + " (id integer primary key,product_id text, color text, size text,price text);";

//    private String ranking = "create table if not exists " + TABLE_USERDETAILS + " (userData text);";

    public static synchronized EcommerceDB getInstance(Context context) {
        if (ecommercedb == null)
            ecommercedb = new EcommerceDB(context);
        return ecommercedb;
    }

    private EcommerceDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
        db.enableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(categories);
        db.execSQL(products);
        db.execSQL(product_variants);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertInitialData(JSONObject respObj) {
        JSONArray categoriesArr = respObj.optJSONArray("categories");
        db.beginTransaction();
        for (int i = 0; i < categoriesArr.length(); i++) {
            try {
                JSONObject categoryObj = categoriesArr.optJSONObject(i);
                ContentValues values = new ContentValues();
                values.put("id", categoryObj.optString("id"));
                values.put("name", categoryObj.optString("name"));
//                values.put("child_categories", (categoryObj.optJSONArray("child_categories").length() > 0) ? (categoryObj.optJSONArray("child_categories").join(",")) : "");
                if (checkIfRecordExists(categoryObj.optString("id"))) {
                    db.update(TABLE_CATEGORIES, values, "id = ?", new String[]{categoryObj.optString("id")});
                } else {
                    db.insert(TABLE_CATEGORIES, null, values);
                }
                JSONArray childCategoriesArr = categoryObj.optJSONArray("child_categories");
                for (int l = 0; l < childCategoriesArr.length(); l++) {
                    int value = childCategoriesArr.optInt(l);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", value);
                    contentValues.put("parent_id", categoryObj.optString("id"));
                    if (checkIfRecordExists(String.valueOf(value))) {
                        db.update(TABLE_CATEGORIES, contentValues, "id = ?", new String[]{String.valueOf(value)});
                    } else {
                        db.insert(TABLE_CATEGORIES, null, contentValues);
                    }
                }

                JSONArray productsArr = categoryObj.optJSONArray("products");
                for (int j = 0; j < productsArr.length(); j++) {

                    JSONObject productObj = productsArr.optJSONObject(j);
                    ContentValues prodValues = new ContentValues();
                    prodValues.put("id", productObj.optString("id"));
                    prodValues.put("name", productObj.optString("name"));
                    prodValues.put("date_added", productObj.optString("date_added"));
                    prodValues.put("tax_name", productObj.optJSONObject("tax").optString("name"));
                    prodValues.put("tax_value", productObj.optJSONObject("tax").optString("value"));
                    prodValues.put("category_id", categoryObj.optString("id"));

                    long productId = db.replace(TABLE_PRODUCTS, null, prodValues);

                    JSONArray variantArr = productObj.optJSONArray("variants");
                    for (int k = 0; k < variantArr.length(); k++) {
                        JSONObject variantObj = variantArr.optJSONObject(k);

                        ContentValues variantValues = new ContentValues();
                        variantValues.put("id", variantObj.optString("id"));
                        variantValues.put("color", variantObj.optString("color"));
                        variantValues.put("size", variantObj.optString("size"));
                        variantValues.put("price", variantObj.optString("price"));
                        variantValues.put("product_id", productId);
                        long variantId = db.replace(TABLE_PRODUCT_VARIANT, null, variantValues);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public ArrayList<CategoryModel> getCategories(String id) {
        ArrayList<CategoryModel> categoriesList = new ArrayList<>();
        String query = "select * from " + TABLE_CATEGORIES + " where parent_id = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        try {
            if (cursor != null && cursor.moveToNext()) {
                do {
                    CategoryModel model = new CategoryModel();
                    model.setId(cursor.getString(0));
                    model.setName(cursor.getString(1));
                    model.setChild_categories(getCategories(cursor.getString(0)));
                    model.setProducts(getProducts(cursor.getString(0)));
                    categoriesList.add(model);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return categoriesList;
    }

    public ArrayList<ProductModel> getProducts(String id) {
        ArrayList<ProductModel> productList = new ArrayList<>();
        String query = "select * from " + TABLE_PRODUCTS + " where category_id = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        try {
            if (cursor != null && cursor.moveToNext()) {
                do {
                    ProductModel model = new ProductModel();
                    model.setId(cursor.getString(0));
                    model.setName(cursor.getString(2));
                    model.setDate_added(cursor.getString(3));
                    model.setTax_name(cursor.getString(4));
                    model.setTax_value(cursor.getString(5));
                    productList.add(model);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return productList;
    }

    public boolean checkIfRecordExists(String id) {
        boolean value = false;
        Cursor cursor = null;
        String sql = "SELECT id FROM " + TABLE_CATEGORIES + " WHERE id=" + id;
        cursor = db.rawQuery(sql, null);
        value = cursor.getCount() > 0;
        cursor.close();
        return value;
    }
}
