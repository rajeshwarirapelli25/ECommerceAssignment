package com.example.ecommerceassignment.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("json")
    Call<JsonObject> getCategories();
}
