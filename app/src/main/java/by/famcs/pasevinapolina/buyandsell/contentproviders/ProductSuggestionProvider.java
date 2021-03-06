package by.famcs.pasevinapolina.buyandsell.contentproviders;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.famcs.pasevinapolina.buyandsell.jsonparsers.GsonParser;
import by.famcs.pasevinapolina.buyandsell.models.Product;
import by.famcs.pasevinapolina.buyandsell.models.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user
 */

public class ProductSuggestionProvider extends ContentProvider {

    List<Product> mProducts;

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if(mProducts == null || mProducts.isEmpty()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://buyandsellweb.herokuapp.com/json/product_data.json")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();

                mProducts = Arrays.asList(GsonParser.parseJSONtoProducts(jsonString));

//                JSONArray jsonArray = new JSONArray(jsonString);
//
//                mProducts = new ArrayList<>();
//
//                int length = jsonArray.length();
//                for (int i = 0; i < length; i++) {
//                    String product = jsonArray.getString(i);
//                    mProducts.add(product);
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MatrixCursor matrixCursor = new MatrixCursor(
                new String[]{
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
                }
        );
        if(mProducts != null) {
            String query = uri.getLastPathSegment().toUpperCase();
            int limit = Integer.parseInt(uri.getQueryParameter(
                    SearchManager.SUGGEST_PARAMETER_LIMIT));

            int length = mProducts.size();
            for (int i = 0; i < length && matrixCursor.getCount() < limit; i++) {
                String product = mProducts.get(i).getName();
                if (product.toUpperCase().contains(query)) {
                    matrixCursor.addRow(new Object[]{ i, product, i });
                }
            }
        }
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
