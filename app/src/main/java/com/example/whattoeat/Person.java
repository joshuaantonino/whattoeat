package com.example.whattoeat;

public class Person {

    public String email, password, cpassword;

    public Person (){

    }

    public Person (String email, String password, String confirmpassword){
        this.email = email;
        this.password = password;
        this.cpassword = confirmpassword;
    }
}

