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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewRawMaterial extends AppCompatActivity {
    TextView viewUnitName;
    DatabaseReference databaseRaw;
    List<RawMaterial> RawMats;
    ListView RawMatList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_raw_material);

        viewUnitName = findViewById(R.id.RawUnitName);
        RawMatList =findViewById(R.id.RawMatListDsp);
        Intent temp =getIntent();
        final String Uid = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);

        viewUnitName.setText(name);
        databaseRaw = FirebaseDatabase.getInstance().getReference("Raw_Material").child(Uid);

        RawMats = new ArrayList<>();

        RawMatList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                RawMaterial subraw = RawMats.get(position);
                showUpdateDialog(Uid,subraw.getrID(),subraw.getrName());
                return false;
            }
        });
    }

    private void showUpdateDialog(final String uid, final String rID, String rName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.update_raw_material,null);

        dialogBuilder.setView(dialogView);
        Button update = dialogView.findViewById(R.id.updateRaw);
        final Button remove = dialogView.findViewById(R.id.RemoveRawMat);
        final EditText Name = dialogView.findViewById(R.id.EditRawName);
        final EditText Weight = dialogView.findViewById(R.id.EditRawWeight);
        dialogBuilder.setTitle("Updating: "+rName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newName = Name.getText().toString().trim();
                final String newWt = Weight.getText().toString().trim();

                updateDetailsRaw(uid,rID,newName,newWt);
                alertDialog.dismiss();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removestuff(uid,rID);
                alertDialog.dismiss();

            }
        });
    }

    private void removestuff(String uid, String rID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Raw_Material").child(uid).child(rID);
        databaseReference.removeValue();
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
    }

    private void updateDetailsRaw(String uid, String rID, String newName, String newWt) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Raw_Material").child(uid).child(rID);
        if(!TextUtils.isEmpty(newName)){
            databaseReference.child("rName").setValue(newName);
        }
        if(!TextUtils.isEmpty(newWt)){
            databaseReference.child("rWt").setValue(newWt);
        }

        if (TextUtils.isEmpty(newName) && TextUtils.isEmpty(newWt)){
            Toast.makeText(this, "Atleast enter one field", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseRaw.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RawMats.clear();
                for(DataSnapshot rdsnapshot : dataSnapshot.getChildren()){
//                    Employee emp = empsnapshot.getValue(Employee.class);
//                    Product pd = pdsnapshot.getValue(Product.class);
                    RawMaterial rd =rdsnapshot.getValue(RawMaterial.class);
                    RawMats.add(rd);
                }

//                ProdList EmpListAdapter = new ProdList(ViewProducts.this,Products);
                RawList RawListAdapter = new RawList(ViewRawMaterial.this,RawMats);
//                listviewproducts.setAdapter(EmpListAdapter);
                RawMatList.setAdapter(RawListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
