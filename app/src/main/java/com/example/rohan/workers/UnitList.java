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

public class UnitList extends ArrayAdapter<Units>{
    private Activity context;
    List<Units> unitlist;

    public UnitList(Activity context, List<Units> unitlist)
    {
        super(context,R.layout.activity_list_layout,unitlist);
        this.context = context;
        this.unitlist = unitlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem =inflater.inflate(R.layout.activity_list_layout,null,true);

        TextView textviewname = (TextView) listViewItem.findViewById(R.id.textViewName);

        Units unit =unitlist.get(position);
        textviewname.setText(unit.getUnitname());

        return  listViewItem;
    }
}
