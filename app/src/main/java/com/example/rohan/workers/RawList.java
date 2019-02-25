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

public class RawList extends ArrayAdapter<RawMaterial> {
    private Activity context;
    List<RawMaterial> RawMats;

    public RawList(Activity context, List<RawMaterial> RawMats)
    {
        super(context,R.layout.activity_list_layout,RawMats);
        this.context = context;
        this.RawMats = RawMats;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem =inflater.inflate(R.layout.activity_list_layout,null,true);

        TextView textviewname = listViewItem.findViewById(R.id.textViewName);
        TextView prodwt = listViewItem.findViewById(R.id.textViewEmploye);
//        TextView prodbags = listViewItem.findViewById(R.id.vieBags);
            RawMaterial rd = RawMats.get(position);
//        Product pd = Prods.get(position);
        textviewname.setText(rd.getrName());
        prodwt.setText("Weight: "+rd.getrWt());
//        prodbags.setText("Number of Bags: "+pd.getPbags());

        return  listViewItem;
    }
}
