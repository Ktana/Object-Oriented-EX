package KML;

import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

@XmlDataType(defaultElementName = "Placemark")
/**
 * A class to get the automatic KML generator to create a place mark tag with all it's fields
 * @author Alex Fishman
 *
 */
public class Placemark {
	@XmlElement(pos = 1)
	String name;
	
	@XmlElement(pos = 2)
	String description;
	
	@XmlElement(pos = 3)
	String styleUrl ="#green";
	
	@XmlElement(pos = 4)
	Point Point;
	
	@XmlElement(pos=5)
	TimeStamp TimeStamp;
	
	public String getname(){return this.name;}
	public String getdescription(){return this.description;}
	public String getstyleUrl(){return this.styleUrl;}
	public Point getPoint(){return this.Point;}
	public TimeStamp getDate(){return this.TimeStamp;}
	
	/**
	 * Blank constructor to use the interface of Jsefa
	 */
	public Placemark(){}
	
	/**
	 * Constructor from data 
	 * @param name
	 * @param description
	 * @param styleUrl
	 * @param point
	 * @param timeStamp
	 */
	public Placemark(String name,String description,String styleUrl,Point point,TimeStamp timeStamp){
		this.name = name;
		this.description = description;
		this.styleUrl = "#green";
		this.Point = point;
		this.TimeStamp = timeStamp;
	}
	
}
