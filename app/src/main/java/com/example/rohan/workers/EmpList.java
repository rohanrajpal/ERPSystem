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

public class EmpList extends ArrayAdapter<Employee>{
    private Activity context;
    List<Employee> Emps;

    public EmpList(Activity context, List<Employee> emps)
    {
        super(context,R.layout.activity_emp_layout,emps);
        this.context = context;
        this.Emps = emps;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem =inflater.inflate(R.layout.activity_emp_layout,null,true);

        TextView textviewname = (TextView) listViewItem.findViewById(R.id.textViewEmp);
        TextView attendance = (TextView)  listViewItem.findViewById(R.id.textViewAttd);
//        Units unit =unitlist.get(position);
        Employee emp = Emps.get(position);
        textviewname.setText(emp.getEmpName());
        attendance.setText(emp.getAttendance());

        return  listViewItem;
    }
}
