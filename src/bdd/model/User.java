package bdd.model;

public class User {

    private String mail;
    private String surname;
    private String password;
    private String avatar;
    private String country;
    private String city;
    private String biography;
    private String date;
    private String personal_stream_url;

    public String getMail() {
	return mail;
    }

    public void setMail(String mail) {
	this.mail = mail;
    }

    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getAvatar() {
	return avatar;
    }

    public void setAvatar(String avatar) {
	this.avatar = avatar;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getBiography() {
	return biography;
    }

    public void setBiography(String biography) {
	this.biography = biography;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public String getPersonalStream() {
	return personal_stream_url;
    }

    public void setPersonalStream(String personal_stream_url) {
	this.personal_stream_url = personal_stream_url;
    }
}