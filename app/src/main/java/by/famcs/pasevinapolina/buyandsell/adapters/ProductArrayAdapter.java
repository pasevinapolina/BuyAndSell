package by.famcs.pasevinapolina.buyandsell.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import by.famcs.pasevinapolina.buyandsell.R;
import by.famcs.pasevinapolina.buyandsell.models.Product;

/**
 * Created by user
 */

public class ProductArrayAdapter extends ArrayAdapter<Product> {

    private final Context context;
    private final Product[] products;


    public ProductArrayAdapter(Context context, Product[] products) {
        super(context, R.layout.list_row_catalog, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Product product = products[position];
        View rowView = inflater.inflate(R.layout.list_row_catalog, parent, false);

        TextView productTitle = (TextView) rowView.findViewById(R.id.product_title);
        TextView seller = (TextView) rowView.findViewById(R.id.seller);
        TextView brand = (TextView) rowView.findViewById(R.id.brand);
        TextView price = (TextView) rowView.findViewById(R.id.price);
        TextView onSalePrice = (TextView) rowView.findViewById(R.id.on_sale_price);
        ImageView productImage = (ImageView) rowView.findViewById(R.id.product_list_image);

        productTitle.setText(product.getName());
        brand.setText(product.getBrand());
        String sellerText = seller.getText().toString();
        seller.setText(sellerText + " " + product.getBrand());
        Picasso.with(context)
                .load(product.getPhoto())
                .into(productImage);
        //seller.setText(sellerText + product.getOwner().getName() + " " + product.getOwner().getLastName());

        price.setText(formatCurrency(product.getPrice()));
        price.setTextSize(18);
        if(product.getOnSalePrice() != 0) {
            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            onSalePrice.setText(formatCurrency(product.getOnSalePrice()));
        }
        return rowView;
    }

    private String formatCurrency(double price) {
        return String.format(Locale.getDefault(), "%,.2f BYN", price);
    }
}
