package com.example.rohan.workers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddRemoveUnit extends AppCompatActivity {
    EditText unitname;
    Button adduser,viewuser;

    DatabaseReference databaseUnits;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_unit);

        databaseUnits = FirebaseDatabase.getInstance().getReference("Units");

        unitname = findViewById(R.id.UnitNumber);
        adduser = findViewById(R.id.unitadd);
        viewuser = findViewById(R.id.BviewUnits);



        viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoviewandremoveUnits();
            }
        });
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUnit();
            }
        });


    }

    private void gotoviewandremoveUnits() {
        Intent intent = new Intent(this, ViewRemoveUnits.class);
        startActivity(intent);
    }


    private void addUnit()
    {
        String unit =unitname.getText().toString().trim();

        if(!TextUtils.isEmpty(unit))
        {
            String id = databaseUnits.push().getKey();
            Units unittoadd = new Units(id,unit);
            databaseUnits.child(id).setValue(unittoadd);
            Toast.makeText(this, "Unit added",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "You should enter a unit",Toast.LENGTH_LONG).show();
        }
    }


}

