package com.example.rohan.workers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Poduction extends AppCompatActivity {
    public static final String UNIT_NAME="Unit-1";
    public static final String UNIT_ID="unitID";
    EditText pname,pwt,pbg;
    Button addprod,viewprod;
    DatabaseReference databaseProd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poduction);

        pname = (EditText) findViewById(R.id.Epname);
        pwt = (EditText) findViewById(R.id.Epweight);
        pbg = (EditText) findViewById(R.id.EpBags);
        addprod = (Button) findViewById(R.id.Baddproduct);
        viewprod = (Button) findViewById(R.id.BviewProduct);
        Intent intent = getIntent();
        final String Uid = intent.getStringExtra(Operations.UNIT_ID);
        String name = intent.getStringExtra(Operations.UNIT_NAME);
        databaseProd = FirebaseDatabase.getInstance().getReference("Products").child(Uid);
        addprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
        viewprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openprodview();
            }
        });
    }

    private void openprodview() {
        Intent temp =getIntent();
        String id = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        Intent intent = new Intent(getApplicationContext(), ViewProducts.class);
        intent.putExtra(UNIT_ID, id);
        intent.putExtra(UNIT_NAME,name);

        startActivity(intent);
    }

    private void saveProduct() {
        String ProductName = pname.getText().toString().trim();
        String ProductWt = pwt.getText().toString().trim();
        String ProductBg = pbg.getText().toString().trim();

        if(!TextUtils.isEmpty(ProductName) && !TextUtils.isEmpty(ProductWt) && !TextUtils.isEmpty(ProductBg))
        {
            String uniqueid = databaseProd.push().getKey();
            Product ptemp = new Product(uniqueid,ProductName,ProductBg,ProductWt);
            databaseProd.child(uniqueid).setValue(ptemp);

            Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
