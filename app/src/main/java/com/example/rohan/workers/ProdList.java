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

public class ProdList extends ArrayAdapter<Product>{
    private Activity context;
    List<Product> Prods;

    public ProdList(Activity context, List<Product> Prods)
    {
        super(context,R.layout.product_layout,Prods);
        this.context = context;
        this.Prods = Prods;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem =inflater.inflate(R.layout.product_layout,null,true);

        TextView textviewname = (TextView) listViewItem.findViewById(R.id.viewProdName);
        TextView prodwt = (TextView)  listViewItem.findViewById(R.id.viewWt);
        TextView prodbags = (TextView) listViewItem.findViewById(R.id.vieBags);

        Product pd = Prods.get(position);
        textviewname.setText(pd.getPname());
        prodwt.setText("Weight: "+pd.getPweight());
        prodbags.setText("Number of Bags: "+pd.getPbags());

        return  listViewItem;
    }
}
