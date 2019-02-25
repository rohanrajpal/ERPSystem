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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddRemoveEmp extends AppCompatActivity {
    public static final String UNIT_NAME="Unit-1";
    public static final String UNIT_ID="unitID";
    public  static final String EmpShift = "Day";
    TextView textViewunitname;
    EditText edittextempname;
    Spinner shift;
//    ListView listViewEmps;
    Button baddEmp,bViewEmpDay,bViewEmpNight,markEmpDay;
    DatabaseReference databaseEmp;

    List<Employee> Emps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_emp);

        textViewunitname = (TextView) findViewById(R.id.UnitView);
        edittextempname = (EditText) findViewById(R.id.EmpName);
        bViewEmpDay = findViewById(R.id.ViewAtdDay);
        bViewEmpNight = findViewById(R.id.ViewAtdNight);
        baddEmp = (Button) findViewById(R.id.addEmp);
        markEmpDay = findViewById(R.id.BmarkDay);
        shift = findViewById(R.id.spinnerShift);
//        listViewEmps = (ListView) findViewById(R.id.empList);
        Emps=new ArrayList<>();
        Intent intent = getIntent();
        final String Uid = intent.getStringExtra(Operations.UNIT_ID);
        String name = intent.getStringExtra(Operations.UNIT_NAME);

        textViewunitname.setText(name);

        databaseEmp = FirebaseDatabase.getInstance().getReference("Employee").child(Uid);

        baddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmp();
            }
        });
        markEmpDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    markAtdDay();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        bViewEmpDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotToviewEmp("Day");
            }
        });
        bViewEmpNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotToviewEmp("Night");
            }
        });
    }
    private boolean checktimings(String time, String endtime) throws ParseException {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if(date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }
    private void markAtdDay() throws ParseException {
//        Date currentTime = Calendar.getInstance().getTime();
        String Time = new SimpleDateFormat("HH:mm").format(new Date());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String currentDateandTime = sdf.format(new Date());
//        Toast.makeText(this, String.valueOf(Time), Toast.LENGTH_SHORT).show();
//        if (sdf < )
//        String start = "8/0/0";
//        SimpleDateFormat sdf = new SimpleDateFormat("HH/mm/ss");
//        Date strDate = sdf.parse(start);
//        if (System.currentTimeMillis() > strDate.getTime()) {
//            catalog_outdated = 1;
//        }
        boolean result = (checktimings(Time,"10:00"));
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        boolean result2 = checktimings("08:00",Time);
        
        if (result && result2){
            gotoMarkDayAtd();
        }else{
            Toast.makeText(this, "Cant change attendance now", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoMarkDayAtd() {

    }

    private void gotToviewEmp(String shift) {
        Intent temp =getIntent();
        String id = temp.getStringExtra(UnitSelection.UNIT_ID);
        String name = temp.getStringExtra(UnitSelection.UNIT_NAME);
        Intent intent = new Intent(getApplicationContext(), TakeAttendance.class);
        intent.putExtra(UNIT_ID, id);
        intent.putExtra(UNIT_NAME,name);
        intent.putExtra(EmpShift,shift);

        startActivity(intent);
    }



    private void saveEmp(){
        String EmpName = edittextempname.getText().toString().trim();
        String EmpShift = shift.getSelectedItem().toString();
        if(!TextUtils.isEmpty(EmpName))
        {
            String id = databaseEmp.push().getKey();
            Employee emp = new Employee(id,EmpName,"Absent",EmpShift);

            databaseEmp.child(id).setValue(emp);

            Toast.makeText(this, "Employee added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Employee name should not be empty", Toast.LENGTH_SHORT).show();
        }
    }

}
