package KML;

import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

@XmlDataType(defaultElementName = "Point")
/**
 * A class to get the automatic KML generator to create a place mark tag with all it's fields
 * @author Alex Fishman
 *
 */
public class Point {
	@XmlElement(pos = 1)
	private String coordinates;
	
	public String getcoordinates(){return this.coordinates;}
	
	/**
	 * Blank constructor to use the interface of Jsefa
	 */
	public Point(){}
	
	/**
	 * Constructor from data
	 * @param CurrentLongitude
	 * @param CurrentLatitude
	 */
	public Point(String CurrentLongitude,String CurrentLatitude)
	{
			this.coordinates = CurrentLongitude + " ,"+ CurrentLatitude;
	}

}
