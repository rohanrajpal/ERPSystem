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

public class ViewDeliveries extends AppCompatActivity {
    TextView DspUnitName;
    ListView listviewproducts;
    DatabaseReference databaseProd;
    List<DeliveryProduct> Products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_deliveries);

        Intent intent = getIntent();
        final String Uid = intent.getStringExtra(Operations.UNIT_ID);
        String name = intent.getStringExtra(Operations.UNIT_NAME);

        DspUnitName = findViewById(R.id.textViewDeliveryProduct);
        listviewproducts = findViewById(R.id.listViewDeliveries);

        Products = new ArrayList<>();
        databaseProd = FirebaseDatabase.getInstance().getReference("DeliveryProds").child(Uid);
        DspUnitName.setText(name);

        listviewproducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeliveryProduct subdelivery = Products.get(position);
                showUpdateDialog(Uid,subdelivery.getdId(),subdelivery.getDname());
                return false;
            }
        });
    }

    private void showUpdateDialog(final String uid, final String DiD, String dname) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.update_delivery,null);
        dialogBuilder.setView(dialogView);
        Button update = dialogView.findViewById(R.id.BupdateDelivery);
        Button remove = dialogView.findViewById(R.id.BRemoveDelivery);
        final EditText changeBags = dialogView.findViewById(R.id.editTextDeliveryBags);
        final EditText changeWt = dialogView.findViewById(R.id.editTextDeliveryWeight);
        dialogBuilder.setTitle("Updating: "+dname);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removetheDelivery(uid,DiD);
                alertDialog.dismiss();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bags = changeBags.getText().toString().trim();
                String Wt = changeWt.getText().toString().trim();

                updateDelivery(uid,DiD,bags,Wt);
                alertDialog.dismiss();
            }
        });

    }

    private void updateDelivery(String uid, String diD, String bags, String wt) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryProds").child(uid).child(diD);

        if (!TextUtils.isEmpty(bags)){
            databaseReference.child("dBags").setValue(bags);
        }
        if (!TextUtils.isEmpty(wt)){
            databaseReference.child("dWeight").setValue(wt);
        }
        
        if (TextUtils.isEmpty(bags) && TextUtils.isEmpty(wt)){
            Toast.makeText(this, "Enter Atleast one field", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    private void removetheDelivery(String uid, String diD) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryProds").child(uid).child(diD);
        databaseReference.removeValue();
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseProd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Products.clear();
                for(DataSnapshot pdsnapshot : dataSnapshot.getChildren()){
//                    Employee emp = empsnapshot.getValue(Employee.class);
                    DeliveryProduct pd = pdsnapshot.getValue(DeliveryProduct.class);
                    Products.add(pd);
                }

                DeliveryProdList EmpListAdapter = new DeliveryProdList(ViewDeliveries.this,Products);

                listviewproducts.setAdapter(EmpListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
