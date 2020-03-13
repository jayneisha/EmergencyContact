package com.jay.emergencycontact;

public class Contact {
    private long rowId;
    private String name;
    private String phone;

    public Contact(long rowId,String name,String phone) {
        this.rowId = rowId;
        this.name = name;
        this.phone = phone;
    }
    public void setRowId(long rowId) {
        this.rowId = rowId;
    }
    public long getRowId() {
        return rowId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
}
