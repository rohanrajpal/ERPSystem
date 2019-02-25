package com.example.rohan.workers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Operations extends AppCompatActivity {
    public static final String UNIT_NAME="Unit-1";
    public static final String UNIT_ID="unitID";

    Button changemp,bdelivery,bproductions,brawmaterial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        changemp = findViewById(R.id.empchange);
        bdelivery = findViewById(R.id.bdelivery);
        bproductions = findViewById(R.id.bproduction);
        brawmaterial = findViewById(R.id.bRawMaterial);
        changemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoempchange();
            }
        });
        bproductions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPoduction();
            }
        });
        bdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotodelivery();
            }
        });
        brawmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotorawmaterial();
            }
        });
    }

    private void gotorawmaterial() {
        Intent temp =getIntent();
        String id = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        Intent intent = new Intent(getApplicationContext(), RawMaterialChange.class);
        intent.putExtra(UNIT_ID, id);
        intent.putExtra(UNIT_NAME,name);

        startActivity(intent);
    }

    private void gotodelivery() {
        Intent temp =getIntent();
        String id = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        Intent intent = new Intent(getApplicationContext(), DeliveryAddRemove.class);
        intent.putExtra(UNIT_ID, id);
        intent.putExtra(UNIT_NAME,name);

        startActivity(intent);
    }

    private void gotoPoduction() {
        Intent temp =getIntent();
        String id = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        Intent intent = new Intent(getApplicationContext(), Poduction.class);
        intent.putExtra(UNIT_ID, id);
        intent.putExtra(UNIT_NAME,name);

        startActivity(intent);
    }

    public void gotoempchange()
    {
        Intent temp =getIntent();
        String id = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        Intent intent = new Intent(getApplicationContext(), AddRemoveEmp.class);
        intent.putExtra(UNIT_ID, id);
        intent.putExtra(UNIT_NAME,name);

        startActivity(intent);
    }

}
