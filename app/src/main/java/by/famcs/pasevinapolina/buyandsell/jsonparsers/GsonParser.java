package by.famcs.pasevinapolina.buyandsell.jsonparsers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;

import by.famcs.pasevinapolina.buyandsell.models.Product;
import by.famcs.pasevinapolina.buyandsell.models.User;

/**
 * Created by user
 */

public class GsonParser {

    public static User[] parseJSONtoUser(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, User[].class);
    }

    public static Object[] parseJSONtoArray(String response, Class<? extends Object[]> aClass) {
        Gson gson = new Gson();
        return gson.fromJson(response, aClass);
    }

    public static Product[] parseJSONtoProducts(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, Product[].class);
    }

    public static Product parseJSONtoProduct(JSONObject jsonProduct) {
        Product product = new Product();
        try {
            product.setId(jsonProduct.getLong("product_id"));
            product.setName(jsonProduct.getString("name"));
            product.setCategory(jsonProduct.getString("category"));
            product.setBrand(jsonProduct.getString("brand"));
            product.setDescription(jsonProduct.getString("description"));
            product.setPrice(jsonProduct.getDouble("price"));
            product.setOnSalePrice(jsonProduct.getDouble("on_sale_price"));
            product.setPhoto(jsonProduct.getString("photo"));

        } catch (JSONException e) {
            Log.e(GsonParser.class.getSimpleName(), "JSON parse error: " + e.getMessage());
        }
        return product;
    }
}
