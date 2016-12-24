package by.famcs.pasevinapolina.buyandsell.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import by.famcs.pasevinapolina.buyandsell.R;
import by.famcs.pasevinapolina.buyandsell.adapters.HttpHandler;
import by.famcs.pasevinapolina.buyandsell.adapters.ProductArrayAdapter;
import by.famcs.pasevinapolina.buyandsell.jsonparsers.GsonParser;
import by.famcs.pasevinapolina.buyandsell.models.Product;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends ListFragment{

    private String TAG = CatalogFragment.class.getSimpleName();

    public CatalogFragment() {
        // Required empty public constructor
    }

    List<Product> products;
    private static String url = "http://buyandsellweb.herokuapp.com/json/product_data.json";
    private ProgressDialog pDialog;
    private LayoutInflater inflater;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        products = new ArrayList<>();
        this.inflater = inflater;

        new android.os.Handler().postDelayed(
                ()-> {
                    new ProductJSONReader(getActivity()).execute();
                }, 3000);

        return super.onCreateView(inflater, container, savedInstanceState);
    }



/**
 * Async task class to get json by making HTTP call
 */
private class ProductJSONReader extends AsyncTask<Void, Void, Void> {

    Context context;

    public ProductJSONReader(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog  = ProgressDialog.show(context, "Loading...",
                getResources().getString(R.string.please_wait), false);

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();

            try {
                String jsonStr = sh.makeGETCall(url);

                Log.e(TAG, "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    int length = jsonArray.length();
                    for (int i = 0; i < length; i++) {

                        JSONObject product = jsonArray.getJSONObject(i);
                        products.add(GsonParser.parseJSONtoProduct(product));

                    }
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Json parsing error: " + e.getMessage(),
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });

            }

//            Toast.makeText(context, getResources().getString(R.string.server_error),Toast.LENGTH_LONG)
//                    .show();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(),
//                            "Couldn't get json from server. Check LogCat for possible errors!",
//                            Toast.LENGTH_LONG)
//                            .show();
//                }
//            });



        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        /**
         * Updating parsed JSON data into ListView
         * */
        ProductArrayAdapter adapter = new ProductArrayAdapter(
                inflater.getContext(), products.toArray(new Product[]{}));
        setListAdapter(adapter);
    }

}

}
