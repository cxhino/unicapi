package com.uca.unicapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Stephane on 04/25/2018.
 */

public class UniversityModel extends RealmObject{
    @SerializedName("uniname")
    private String uniname;
    @SerializedName("uniaddress")
    private String uniaddress;
    @SerializedName("uniphone")
    private String uniphone;
    @SerializedName("unidescription")
    private String unidescription;
    @SerializedName("unidepartment")
    private String unidepartment;
    @JsonIgnore
    @SerializedName("id")
    private int id_uni;

    public String getUniname() {
        return uniname;
    }

    public void setUniname(String uniname) {
        this.uniname = uniname;
    }

    public String getUniaddress() {
        return uniaddress;
    }

    public void setUniaddress(String uniaddress) {
        this.uniaddress = uniaddress;
    }

    public String getUniphone() {
        return uniphone;
    }

    public void setUniphone(String uniphone) {
        this.uniphone = uniphone;
    }

    public String getUnidescription() {
        return unidescription;
    }

    public void setUnidescription(String unidescription) {
        this.unidescription = unidescription;
    }

    public int getId_uni() {
        return id_uni;
    }

    public void setId_uni(int id_uni) {
        this.id_uni = id_uni;
    }

    public String getUnidepartment() {
        return unidepartment;
    }

    public void setUnidepartment(String unidepartment) {
        this.unidepartment = unidepartment;
    }
}
