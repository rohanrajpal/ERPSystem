package com.example.rohan.workers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeliveryAddRemove extends AppCompatActivity {
    public static final String UNIT_NAME="Unit-1";
    public static final String UNIT_ID="unitID";
    public  static final String DeliveringTo = "Day";
    EditText deliverto;
    Button addtoDelivery,viewdelivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_add_remove);

        addtoDelivery = findViewById(R.id.addDelivery);
        deliverto = findViewById(R.id.editDeliverTo);
        viewdelivery =findViewById(R.id.BviewDelivery);
        viewdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoViewDelivery();
            }
        });
        addtoDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ToBeDeliveredTo = deliverto.getText().toString().trim();
                if(TextUtils.isEmpty(ToBeDeliveredTo))
                {
                    Toast.makeText(DeliveryAddRemove.this, "Enter Something", Toast.LENGTH_SHORT).show();
                }
                else{
                    gotoprodlist(ToBeDeliveredTo);
                }

            }
        });


    }

    private void gotoViewDelivery() {
        Intent temp =getIntent();
        String id = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        Intent intent = new Intent(getApplicationContext(), ViewDeliveries.class);
        intent.putExtra(UNIT_ID, id);
        intent.putExtra(UNIT_NAME,name);


        startActivity(intent);
    }

    private void gotoprodlist(String toBeDeliveredTo) {
        Intent temp =getIntent();
        String id = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        Intent intent = new Intent(getApplicationContext(), SelectFromProductList.class);
        intent.putExtra(UNIT_ID, id);
        intent.putExtra(UNIT_NAME,name);
        intent.putExtra(DeliveringTo,toBeDeliveredTo);

        startActivity(intent);
    }
}
