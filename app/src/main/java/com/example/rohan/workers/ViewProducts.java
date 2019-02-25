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

public class ViewProducts extends AppCompatActivity {
    TextView displayunitName;
    DatabaseReference databaseProd;
    ListView listviewproducts;
    List<Product> Products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        displayunitName = findViewById(R.id.UnitViewProd);
        listviewproducts = findViewById(R.id.productsList);
        Intent temp =getIntent();
        final String Uid = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        displayunitName.setText(name);
        databaseProd = FirebaseDatabase.getInstance().getReference("Products").child(Uid);
        Products = new ArrayList<>();
        listviewproducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ViewProducts.this, "Clicked", Toast.LENGTH_SHORT).show();
                Product subprod = Products.get(position);
                showUpdateDialog(Uid,subprod.getPid(),subprod.getPname());
                return false;
            }
        });

    }

    private void showUpdateDialog(final String uid, final String pid, String pname) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.update_product,null);

        dialogBuilder.setView(dialogView);

        Button update = dialogView.findViewById(R.id.updateProdDetails);
        Button remove = dialogView.findViewById(R.id.RemoveProd);
        final EditText changedName = dialogView.findViewById(R.id.editProdName);
        final EditText changedWt = dialogView.findViewById(R.id.editProdWt);
        final EditText changedBag = dialogView.findViewById(R.id.editProdBags);
        dialogBuilder.setTitle("Updating: "+pname);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newName = changedName.getText().toString().trim();
                final String newWt = changedWt.getText().toString().trim();
                final String newBags = changedBag.getText().toString().trim();
                nowupdateProductDetails(uid,pid,newName,newWt,newBags);
                alertDialog.dismiss();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removetheProduct(uid,pid);
                alertDialog.dismiss();
            }
        });
    }

    private void removetheProduct(String uid, String pid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(uid).child(pid);
        databaseReference.removeValue();
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
    }

    private void nowupdateProductDetails(String uid,String pid, String newName,String newWt,String newBags){

        if(!TextUtils.isEmpty(newName) || !TextUtils.isEmpty(newWt) ||!TextUtils.isEmpty(newBags)){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(uid).child(pid);
            if (!TextUtils.isEmpty(newName) && !TextUtils.isEmpty(newWt) &&!TextUtils.isEmpty(newBags)){
                databaseReference.child("pname").setValue(newName);
                databaseReference.child("pweight").setValue(newWt);
                databaseReference.child("pbags").setValue(newBags);
            }else if(!TextUtils.isEmpty(newName) && !TextUtils.isEmpty(newBags)){
                databaseReference.child("pname").setValue(newName);
                databaseReference.child("pbags").setValue(newBags);
            }
            else if (!TextUtils.isEmpty(newName) && !TextUtils.isEmpty(newWt)){
                databaseReference.child("pname").setValue(newName);
                databaseReference.child("pweight").setValue(newWt);
            }
            else if (!TextUtils.isEmpty(newBags)&& !TextUtils.isEmpty(newWt)){
                databaseReference.child("pweight").setValue(newWt);
                databaseReference.child("pbags").setValue(newBags);
            }
            else if (!TextUtils.isEmpty(newName))
            {
                databaseReference.child("pname").setValue(newName);

            }
            else if (!TextUtils.isEmpty(newBags)){
                databaseReference.child("pbags").setValue(newBags);

            }
            else{
                databaseReference.child("pweight").setValue(newWt);
            }
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Atleast fill one field", Toast.LENGTH_SHORT).show();
        }
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
                    Product pd = pdsnapshot.getValue(Product.class);
                    Products.add(pd);
                }

                ProdList EmpListAdapter = new ProdList(ViewProducts.this,Products);

                listviewproducts.setAdapter(EmpListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
