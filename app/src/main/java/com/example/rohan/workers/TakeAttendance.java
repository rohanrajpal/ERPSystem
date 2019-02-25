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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TakeAttendance extends AppCompatActivity {
    TextView unitname;
    DatabaseReference databaseEmp;
    ListView listViewEmps;
    List<Employee> Emps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        unitname = (TextView) findViewById(R.id.UnitName);
        listViewEmps = (ListView) findViewById(R.id.EmpList);
        Intent intent = getIntent();
        final String Uid = intent.getStringExtra(Operations.UNIT_ID);
        String name = intent.getStringExtra(Operations.UNIT_NAME);
        Emps=new ArrayList<>();
        unitname.setText(name);

        databaseEmp = FirebaseDatabase.getInstance().getReference("Employee").child(Uid);

        listViewEmps.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Employee subemp = Emps.get(position);
                showUpdateDialog(Uid,subemp.getEmpId(),subemp.getEmpName());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEmp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Emps.clear();
                Intent intent = getIntent();
                String compareshift = intent.getStringExtra(AddRemoveEmp.EmpShift);
                for(DataSnapshot empsnapshot : dataSnapshot.getChildren()){
                    Employee emp = empsnapshot.getValue(Employee.class);
                    if (emp.getShift().equals(compareshift)) {
                        Emps.add(emp);
                    }
                }

                EmpList EmpListAdapter = new EmpList(TakeAttendance.this,Emps);

                listViewEmps.setAdapter(EmpListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String UnitID,final String EMPID ,final String EmpName)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.update_dialog,null);

        dialogBuilder.setView(dialogView);

        final Button buttonPresent = (Button) dialogView.findViewById(R.id.buttonAttd);
        final Button buttonAbsent = dialogView.findViewById(R.id.buttonAbst);
        Button buttonRemove = dialogView.findViewById(R.id.removeEmployee);
        final Button updateDetails = dialogView.findViewById(R.id.BChangeDetails);
        final EditText changeName = dialogView.findViewById(R.id.EChangeName);
        final Spinner changeShift = dialogView.findViewById(R.id.EchangeShift);

        dialogBuilder.setTitle("Updating: "+EmpName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        buttonPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(TakeAttendance.this, UnitID, Toast.LENGTH_SHORT).show();
                updateAttd(UnitID,EMPID,EmpName,"Present");

                alertDialog.dismiss();
            }
        });
        buttonAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAttd(UnitID,EMPID,EmpName,"Absent");

                alertDialog.dismiss();
            }
        });
        updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newName = changeName.getText().toString().trim();
                final String newShift = changeShift.getSelectedItem().toString();
                updateDetailsEmp(UnitID,EMPID,newName,newShift);

                alertDialog.dismiss();
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removetheEmployee(UnitID,EMPID);
                alertDialog.dismiss();
            }
        });
    }

    private void removetheEmployee(String unitID, String empid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employee").child(unitID).child(empid);
        databaseReference.removeValue();
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
    }

    private void updateDetailsEmp(String id, String EmpId,String newName,String newShift) {

        
        if (!TextUtils.isEmpty(newName) || !TextUtils.isEmpty(newShift)){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employee").child(id).child(EmpId);
            if (!TextUtils.isEmpty(newName)) {

                databaseReference.child("empName").setValue(newName);
                databaseReference.child("shift").setValue(newShift);
            }
            else{
                databaseReference.child("shift").setValue(newShift);
            }
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Enter atleast one detail", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAttd(String id, String EmpId,String Name,String Status)
    {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employee").child(id).child(EmpId);
        databaseReference.child("attendance").setValue(Status);
        Toast.makeText(this, "Marked", Toast.LENGTH_SHORT).show();
    }
}
