package com.example.rohan.workers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RawMaterialChange extends AppCompatActivity {
    public static final String UNIT_NAME="Unit-1";
    public static final String UNIT_ID="unitID";
    EditText rawname,raweight;
    Button addmaterial,viewmaterial;
    DatabaseReference databaseRaw;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_material_change);

        rawname = findViewById(R.id.ErawName);
        raweight = findViewById(R.id.ErawWeight);
        addmaterial = findViewById(R.id.BaddMat);
        viewmaterial = findViewById(R.id.bViewRawmat);

        Intent intent = getIntent();
        final String Uid = intent.getStringExtra(Operations.UNIT_ID);
        databaseRaw = FirebaseDatabase.getInstance().getReference("Raw_Material").child(Uid);
        
        addmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMaterial();
            }
        });
        viewmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoviewmaterial();
            }
        });
    }

    private void gotoviewmaterial() {
        Intent temp =getIntent();
        String id = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        Intent intent = new Intent(getApplicationContext(), ViewRawMaterial.class);
        intent.putExtra(UNIT_ID, id);
        intent.putExtra(UNIT_NAME,name);

        startActivity(intent);
    }

    private void saveMaterial() {
        String MatName = rawname.getText().toString().trim();
        String MatWt = raweight.getText().toString().trim();
        
        if(!TextUtils.isEmpty(MatName) && !TextUtils.isEmpty(MatWt))
        {
            String uniqueID = databaseRaw.push().getKey();
            RawMaterial raw = new RawMaterial(uniqueID,MatName,MatWt);
            databaseRaw.child(uniqueID).setValue(raw);

            Toast.makeText(this, "Raw material added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please enter in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
