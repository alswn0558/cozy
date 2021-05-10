package com.example.teamproject.Login;

/**
 * Created by 김상윤 on 2020/11/10
 */

public class Memberinfo {

    private String name;
    private String phonenumber;
    private String birthday;
    private String gender;
    private String address;
    private String school;
    private String major;


    public Memberinfo(String name, String phonenumber, String birthday, String gender, String address, String school, String major) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.school = school;
        this.major = major;

    }

    public String getName() {
        return  this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhonenumber() {
        return  this.phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getBirthday() {
        return  this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getGender() {
        return  this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAddress() {
        return  this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSchool() {
        return  this.school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getMajor() {
        return  this.major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
}
