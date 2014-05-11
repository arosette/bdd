package bdd.model;

public class Friendship {
    private String mail_sender;
    private String mail_receiver;
    private Boolean status;
    private String date;

    public String getSenderMail() {
	return mail_sender;
    }

    public void setSenderMail(String mail_sender) {
	this.mail_sender = mail_sender;
    }

    public String getReceiverMail() {
	return mail_receiver;
    }

    public void setReceiverMail(String mail_receiver) {
	this.mail_receiver = mail_receiver;
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
}