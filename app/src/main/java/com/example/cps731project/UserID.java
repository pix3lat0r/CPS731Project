package com.example.cps731project;

/*Class to make userid global*/

public class UserID {
    public static String user_id;
    public static String name;
    public UserID(){ }
    public void setUserID(String email){

        user_id = email;

    }
    public void setName(String name){
        this.name = name;
    }

    public String getUser_id(){
        return user_id;
    }
}
