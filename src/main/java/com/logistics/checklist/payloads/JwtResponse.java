package com.logistics.checklist.payloads;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;
    private String fullname;
    private String username;
    private String email;
    private String phone;
    private String address;


    public JwtResponse(String jwttoken, String fullname, String username, String email, String phone, String address) {

        this.jwttoken = jwttoken;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;

    }

    public String getToken() {

        return this.jwttoken;

    }

    public String getFullname(){
        return this.fullname;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPhone(){
        return this.phone;
    }

    public String getAddress(){
        return this.address;
    }

}

