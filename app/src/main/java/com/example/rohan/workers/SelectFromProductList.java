package com.example.rohan.workers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SelectFromProductList extends AppCompatActivity {
    public static final String UNIT_NAME="Unit-1";
    public static final String UNIT_ID="unitID";
    public  static final String DeliveringTo = "Day";
    ListView listviewproducts;
    DatabaseReference databaseProd;
    List<Product> Products;
    TextView UnitNumberSelection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_product_list);


        UnitNumberSelection = findViewById(R.id.SelectingForUnit);
        Intent intent = getIntent();
        final String Uid = intent.getStringExtra(Operations.UNIT_ID);
        String name = intent.getStringExtra(Operations.UNIT_NAME);
        UnitNumberSelection.setText(name);
        databaseProd = FirebaseDatabase.getInstance().getReference("Products").child(Uid);
        listviewproducts = findViewById(R.id.ListOfProd);
        Products = new ArrayList<>();

        listviewproducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product subprod = Products.get(position);
                showUpdateDialog(Uid,subprod.getPid(),subprod.getPname(),subprod.getPbags(),subprod.getPweight());
            }
        });
    }

    private void showUpdateDialog(final String uid, final String pid, final String pname, final String pbags, final String pweight) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.update_delivery_product,null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Updating: "+pname);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        final EditText bags = dialogView.findViewById(R.id.DeliverBags);
        final EditText wt = dialogView.findViewById(R.id.DeliverWt);
        Button confirm = dialogView.findViewById(R.id.AddToDeliverList);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Dbags = Integer.parseInt(bags.getText().toString().trim());
                int Dwt = Integer.parseInt(wt.getText().toString().trim());
                int Pbags = Integer.parseInt(pbags);
                int PWeight = Integer.parseInt(pweight);
                if (Dbags <= Pbags || PWeight >= Dwt)
                {
                    int newPWt = PWeight - Dwt;
                    int newPBags = Pbags - Dbags;
                    saveDelivery(uid,pid,Dbags,Dwt,pname,newPWt,newPBags);
                }
                else {
                    Toast.makeText(SelectFromProductList.this, "Enter lesser values", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void saveDelivery(String uid,String pid, int dbags, int dwt, String pname,int newPwt,int newPBags) {
        Intent intent = getIntent();
        final String DeliverTo = intent.getStringExtra(DeliveryAddRemove.DeliveringTo);
        DeliveryProduct dtemp = new DeliveryProduct(pid,DeliverTo,pname,String.valueOf(dbags),String.valueOf(dwt));
        DatabaseReference databaseDelivery = FirebaseDatabase.getInstance().getReference("DeliveryProds").child(uid).child(pid);
        databaseDelivery.setValue(dtemp);

        databaseProd.child(pid).child("pbags").setValue(String.valueOf(newPBags));
        databaseProd.child(pid).child("pweight").setValue(String.valueOf(newPwt));

        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseProd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Products.clear();
                for(DataSnapshot pdsnapshot : dataSnapshot.getChildren()){
                    Product pd = pdsnapshot.getValue(Product.class);
                    Products.add(pd);
                }

                ProdList EmpListAdapter = new ProdList(SelectFromProductList.this,Products);

                listviewproducts.setAdapter(EmpListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
