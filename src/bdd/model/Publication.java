package bdd.model;

import java.util.List;

public class Publication {

    private String url;
    private String title;
    private String date;
    private String description;
    private String stream;
    private List<Comment> comments;
    private boolean read;

    public List<Comment> getComments() {
	return comments;
    }

    public void setComments(List<Comment> comments) {
	this.comments = comments;
    }

    public String getStream() {
	return stream;
    }

    public void setStream(String stream) {
	this.stream = stream;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setRead(boolean read) {
	this.read = read;
    }

    public boolean isRead() {
	return read;
    }
}
