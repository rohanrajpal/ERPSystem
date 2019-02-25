package com.example.rohan.workers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button adbutton;
    Button usebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adbutton = (Button)findViewById(R.id.badmin);
        usebutton = (Button)findViewById(R.id.unitselect);
        adbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openadminlogin();
            }
        });
        usebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openunitselection();
            }
        });
    }

    public void openadminlogin()
    {
        Intent intent = new Intent(this, AdminLogin.class);
        startActivity(intent);
    }
    public void openunitselection()
    {
        Intent intent = new Intent(this, UnitSelection.class);
        startActivity(intent);
    }
}
