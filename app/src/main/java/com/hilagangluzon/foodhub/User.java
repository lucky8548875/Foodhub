package com.hilagangluzon.foodhub;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User extends Object
{
    //private String id;
    private String username;
    private String password;
    private String first_name;
    private String middle_name;
    private String last_name;
    /*private String address;
    private String city;
    private String province;
    private String zip;*/
    private String email;
    /*private String tel_no;
    private String cel_no;*/

    public static final String COLLECTION_NAME = "users";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_FNAME = "first_name";
    public static final String FIELD_MNAME = "middle_name";
    public static final String FIELD_LNAME = "last_name";
    /*public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_CITY = "city";
    public static final String FIELD_PROVINCE = "province";
    public static final String FIELD_ZIP = "zip";*/
    public static final String FIELD_EMAIL = "email";
    /*public static final String FIELD_TEL_NO = "tel_no";
    public static final String FIELD_CEL_NO = "cel_no";*/
    
    public static final String[] FIELDS = new String[]
            {
                    FIELD_USERNAME, FIELD_PASSWORD, FIELD_FNAME, FIELD_MNAME, FIELD_LNAME, /*FIELD_ADDRESS,
                    FIELD_CITY, FIELD_PROVINCE, FIELD_ZIP,*/ FIELD_EMAIL/*, FIELD_TEL_NO, FIELD_CEL_NO*/
            };

    public User()
    {

    }

    @Override
    public String toString() {
        return this.username + "\n" + this.email;
    }

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /*public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    }

    public String getCel_no() {
        return cel_no;
    }

    public void setCel_no(String cel_no) {
        this.cel_no = cel_no;
    }*/
}
