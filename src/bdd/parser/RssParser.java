package bdd.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bdd.model.Publication;

public class RssParser {
    private Element channel = null;

    public RssParser(String url) {
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	}
	try {
	    Document doc = dBuilder.parse(url);

	    NodeList channels = doc.getDocumentElement().getElementsByTagName(
		    "channel");
	    channel = (Element) channels.item(0);

	} catch (SAXException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public String getTitle() {
	return ((Element) channel.getElementsByTagName("title").item(0))
		.getTextContent();
    }

    public String getDescription() {
	return ((Element) channel.getElementsByTagName("description").item(0))
		.getTextContent();
    }

    public String getLink() {
	return ((Element) channel.getElementsByTagName("link").item(0))
		.getTextContent();
    }

    public List<Publication> getPulications() {
	NodeList items = channel.getElementsByTagName("item");
	ArrayList<Publication> publications = new ArrayList<Publication>();
	for (int i = 0; i < items.getLength(); ++i) {
	    Publication pub = new Publication();
	    Element item = (Element) items.item(i);
	    pub.setTitle(((Element) item.getElementsByTagName("title").item(0))
		    .getTextContent());
	    pub.setDescription(((Element) item.getElementsByTagName(
		    "description").item(0)).getTextContent());
	    pub.setUrl(((Element) item.getElementsByTagName("link").item(0))
		    .getTextContent());
	    pub.setRead(false);
	    String dirtyDate = ((Element) item.getElementsByTagName("pubDate")
		    .item(0)).getTextContent();
	    String[] splittedDate;
	    splittedDate = dirtyDate.split("\\s+");
	    String dirtyMonth = splittedDate[2];
	    String month;
	    switch (dirtyMonth) {
	    case "January":
		month = "01";
		break;
	    case "February":
		month = "02";
		break;
	    case "March":
		month = "03";
		break;
	    case "April":
		month = "04";
		break;
	    case "May":
		month = "05";
		break;
	    case "June":
		month = "06";
		break;
	    case "July":
		month = "07";
		break;
	    case "August":
		month = "08";
		break;
	    case "September":
		month = "09";
		break;
	    case "October":
		month = "10";
		break;
	    case "November":
		month = "11";
		break;
	    case "December":
		month = "12";
		break;
	    default:
		month = "01";
	    }
	    
	    String date = splittedDate[3] + "-" + month + "-" + splittedDate[1];
	    pub.setDate(date);
	    publications.add(pub);
	}
	return publications;
    }

}
