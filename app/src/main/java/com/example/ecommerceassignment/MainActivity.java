package com.example.ecommerceassignment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ecommerceassignment.fragments.CategoriesFragment;
import com.example.ecommerceassignment.utils.APIClient;
import com.example.ecommerceassignment.utils.APIInterface;
import com.example.ecommerceassignment.utils.Constant;
import com.example.ecommerceassignment.utils.EcommerceDB;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private APIInterface networkCall;
    private EcommerceDB ecommerceDB;
    public FragmentManager fragmentManager;
    private Fragment currentFragment;
    public ArrayList<Fragment> listBackStack;
    private FrameLayout fl_MainActivity_Container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkCall = APIClient.getClient().create(APIInterface.class);
        fl_MainActivity_Container = (FrameLayout) findViewById(R.id.fl_MainActivity_Container);
        ecommerceDB = EcommerceDB.getInstance(this);
        listBackStack = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        getProductsData();
    }

    private void getProductsData() {
        Call<JsonObject> productCall = networkCall.getCategories();
        productCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("RESPONSE MSG+++++++", response.message());
                Log.e("RESPONSE+++++++", response.toString());
                if (response.code() == 200) {
                    try {
                        JSONObject respObj = new JSONObject(response.body().toString());
                        ecommerceDB.insertInitialData(respObj);
                        displayView(Constant.DrawerMenu.CATEGORIES);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "There was some error while fetching data, please try again later.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "There was some error while fetching data, please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displayView(Constant.DrawerMenu position) {
        Fragment fragment = null;
        switch (position) {
            case CATEGORIES:
                fragment = new CategoriesFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            if (currentFragment != null)
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
            listBackStack.clear();
            fragmentManager.beginTransaction().replace(R.id.fl_MainActivity_Container, fragment).commitAllowingStateLoss();
            listBackStack.add(fragment);
            currentFragment = fragment;
        } else {
            return;
        }
    }

    public void addFragment(Fragment fragment) {
        try {
            if (currentFragment == null) {
                currentFragment = fragment;
                fragmentManager.beginTransaction().add(R.id.fl_MainActivity_Container, fragment).addToBackStack(null).commitAllowingStateLoss();
                listBackStack.add(fragment);
            } else if (currentFragment.getClass() != fragment.getClass()) {
                currentFragment = fragment;
                fragmentManager.beginTransaction().add(R.id.fl_MainActivity_Container, fragment).addToBackStack(null).commitAllowingStateLoss();
                listBackStack.add(fragment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            listBackStack.remove(listBackStack.size() - 1);
            currentFragment = listBackStack.get(listBackStack.size() > 0 ? (listBackStack.size() - 1) : 0);
        } else {
            super.onBackPressed();
        }
    }
}
