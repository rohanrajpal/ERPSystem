package com.example.rohan.workers;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewRemoveUnits extends AppCompatActivity {
    ListView listviewunits;
    DatabaseReference databaseUnits;
    List<Units> unitlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_remove_units);
        databaseUnits = FirebaseDatabase.getInstance().getReference("Units");
        listviewunits =(ListView)findViewById(R.id.listViewunits);
        unitlist = new ArrayList<>();
        listviewunits.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Units unit = unitlist.get(position);
                showremovedialog(unit.getId(),unit.getUnitname());
                return false;
            }
        });
    }

    private void showremovedialog(final String UnitId, String UnitName)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.remove_dialog,null);

        dialogBuilder.setView(dialogView);

        final Button buttonRemove = (Button) dialogView.findViewById(R.id.RemUnit);
        dialogBuilder.setTitle("Removing Unit: "+UnitName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUnit(UnitId);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteUnit(String unitId) {
        DatabaseReference drUnits = FirebaseDatabase.getInstance().getReference("Units").child(unitId);
        DatabaseReference drEmp = FirebaseDatabase.getInstance().getReference("Employee").child(unitId);
        drUnits.removeValue();
        drEmp.removeValue();

        Toast.makeText(this, "Removed the unit", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseUnits.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                unitlist.clear();
                for (DataSnapshot unitsnapshot : dataSnapshot.getChildren()) {
                    Units unit = unitsnapshot.getValue(Units.class);

                    unitlist.add(unit);
                }
                UnitList adapter = new UnitList(ViewRemoveUnits.this,unitlist);
                listviewunits.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
