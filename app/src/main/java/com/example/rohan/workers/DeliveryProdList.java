package com.example.rohan.workers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DeliveryProdList extends ArrayAdapter<DeliveryProduct> {
    private Activity context;
    List<DeliveryProduct> Prods;

    public DeliveryProdList(Activity context, List<DeliveryProduct> Prods)
    {
        super(context,R.layout.product_layout,Prods);
        this.context = context;
        this.Prods = Prods;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem =inflater.inflate(R.layout.delivery_product_layout,null,true);

        TextView deliverto = listViewItem.findViewById(R.id.textViewDeliverTo);
        TextView textviewname = (TextView) listViewItem.findViewById(R.id.textViewProdName);
        TextView prodwt = (TextView)  listViewItem.findViewById(R.id.textViewProdWt);
        TextView prodbags = (TextView) listViewItem.findViewById(R.id.textViewProdBags);

        DeliveryProduct pd = Prods.get(position);
        deliverto.setText(pd.getdDeliverTo());
        textviewname.setText("Product Name: "+pd.getDname());
        prodwt.setText("Weight: "+pd.getdWeight());
        prodbags.setText("Number of Bags: "+pd.getdBags());

        return  listViewItem;
    }
}
