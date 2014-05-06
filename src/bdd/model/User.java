package bdd.model;

public class User {

    private String mail;
    private String surname;
    private String password;
    private String avatar;
    private String country;
    private String city;
    private String biography;
    private String registrationDate;

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
	this.avatar = country;
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

    public String getRegistrationDate() {
	return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
	this.registrationDate = registrationDate;
    }
}