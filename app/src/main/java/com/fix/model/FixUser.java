package com.fix.model;


import java.util.List;

public class FixUser {
    String user_id;
    String first_name;
    String last_name;
    List<Phone> phones;

    public String getName() {
        return first_name + " " + last_name;
    }
}