package com.example.direktoratpendidikan.data;

public class MSG {

    private Integer success;
    private String message;
    private String nama_user;
    private Integer status_user;

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

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
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

    public Integer getStatus() {
        return status_user;
    }

    public void setStatus(Integer status_user) {
        this.status_user = status_user;
    }

}