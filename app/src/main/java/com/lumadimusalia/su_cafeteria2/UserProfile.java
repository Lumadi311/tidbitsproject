package com.lumadimusalia.su_cafeteria2;

public class UserProfile {
    public String userFirstName;
    public String userLastName;
    public String userEmailAddress;


    public UserProfile(String userFirstName, String userLastName, String userEmailAddress){
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmailAddress = userEmailAddress;

    }
    public String getUserFirstName(){
        return userFirstName;
    }
    public String getUserLastName(){
        return userLastName;
    }
    public String getUserEmailAddress(){
        return userEmailAddress;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }
    public void setUserLastName(String userLastName){
        this.userLastName = userLastName;
    }
    public void setUserEmailAddress(String userEmailAddress){
        this.userEmailAddress = userEmailAddress;
    }
}
