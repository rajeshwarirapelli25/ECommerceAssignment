package com.example.ecommerceassignment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
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
    private ProgressBar pbLoader;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkCall = APIClient.getClient().create(APIInterface.class);
        fl_MainActivity_Container = (FrameLayout) findViewById(R.id.fl_MainActivity_Container);
        btnRetry = (Button) findViewById(R.id.btn_retry);
        pbLoader = (ProgressBar) findViewById(R.id.pb_MainActivity_Loader);
        ecommerceDB = EcommerceDB.getInstance(this);
        listBackStack = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeApiCallToGetData();
            }
        });
        makeApiCallToGetData();

    }

    private void makeApiCallToGetData() {
        if (Constant.isNetworkAvailable(this)) {
            btnRetry.setVisibility(View.GONE);
            getProductsData();
        } else {
            btnRetry.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "No internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getProductsData() {
        pbLoader.setVisibility(View.VISIBLE);
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
                        pbLoader.setVisibility(View.GONE);
                        btnRetry.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    pbLoader.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "There was some error while fetching data, please try again later.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                pbLoader.setVisibility(View.GONE);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        menu.findItem(R.id.action_sort).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}
