package com.example.rohan.workers;

public class Employee {
    private String empId;
    private String empName;
    private String attendance;
    private String shift;

    public Employee(){

    }

    public Employee(String empId, String empName, String attendance,String shift) {
        this.empId = empId;
        this.empName = empName;
        this.attendance = attendance;
        this.shift = shift;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getAttendance() {
        return attendance;
    }

    public String getShift() {
        return shift;
    }
}
