package com.saidul.nytimesstoreexample.model;

import com.google.gson.annotations.SerializedName;

public class GithubUser {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("login")
    private String login;

    @SerializedName("company")
    private String company;

    @SerializedName("bio")
    private String bio;

    @SerializedName("location")
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
