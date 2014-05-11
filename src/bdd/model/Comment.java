package bdd.model;

public class Comment {

    private String user_mail;
    private String publication_url;
    private String stream_url;
    private String content;
    private String date;

    public String getUserMail() {
	return user_mail;
    }

    public void setUserMail(String user_mail) {
	this.user_mail = user_mail;
    }

    public String getPublicationUrl() {
	return publication_url;
    }

    public void setPublicationUrl(String publication_url) {
	this.publication_url = publication_url;
    }

    public String getStreamUrl() {
	return stream_url;
    }

    public void setStreamUrl(String stream_url) {
	this.stream_url = stream_url;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

}
