package bdd.model;

import java.util.Observable;

public class Friendship extends Observable {
    private String mailUser1;
    private String mailUser2;
    private Boolean status;
    private String date;
    private String asker;

    public String getMailUser1() {
	return mailUser1;
    }

    public void setMailUser1(String mailUser1) {
	this.mailUser1 = mailUser1;
    }

    public String getMailUser2() {
	return mailUser2;
    }

    public void setMailUser2(String mailUser2) {
	this.mailUser2 = mailUser2;
    }

    public Boolean getStatus() {
	return status;
    }

    public void setStatus(Boolean status) {
	this.status = status;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public String getAsker() {
	return asker;
    }

    public void setAsker(String asker) {
	this.asker = asker;
    }
}
