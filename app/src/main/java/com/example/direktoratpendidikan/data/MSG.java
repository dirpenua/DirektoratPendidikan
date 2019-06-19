package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;

public class MSG {

    private String id;
    private Integer success;
    private String message;
    private String jamterkirim;
    private String nama_user;
    private String foto_user;
    private Integer status_user;
    private Integer badge;

    /**
     * No args constructor for use in serialization
     */
    public MSG() {
    }

    /**
     * @param message
     * @param success
     */
    public MSG(Integer success, String message) {
        super();
        this.success = success;
        this.message = message;
    }

    public String getId() { return id; }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public Integer getBadge() {
        return badge;
    }

    public String getJamterkirim() {
        return jamterkirim;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNamaUser() {
        return nama_user;
    }

    public void setNamaUser(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getFotoUser() { return foto_user; }

    public Integer getStatus() {
        return status_user;
    }

    public void setStatus(Integer status_user) {
        this.status_user = status_user;
    }

}