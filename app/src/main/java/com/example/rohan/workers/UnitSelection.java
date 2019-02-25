package com.example.rohan.workers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UnitSelection extends AppCompatActivity {

    public static final String UNIT_NAME="Unit-1";
    public static final String UNIT_ID="unitID";
    DatabaseReference databaseUnits;

    ListView listviewunits;

    List<Units> unitlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_selection);
        databaseUnits = FirebaseDatabase.getInstance().getReference("Units");

        listviewunits =(ListView)findViewById(R.id.selecttheunit);

        unitlist = new ArrayList<>();

        listviewunits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Units unit = unitlist.get(position);
                Intent intent = new Intent(getApplicationContext(), Operations.class);
                intent.putExtra(UNIT_ID, unit.getId());
                intent.putExtra(UNIT_NAME,unit.getUnitname());

                startActivity(intent);
            }
        });
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
                UnitList adapter = new UnitList(UnitSelection.this,unitlist);
                listviewunits.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
