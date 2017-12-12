package KML;

import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

@XmlDataType(defaultElementName = "Point")
public class Point {
	@XmlElement(pos = 1)
	private String coordinates;
	
	public String getcoordinates(){return this.coordinates;}
	
	public Point(){}
		
	public Point(String CurrentLongitude,String CurrentLatitude)
	{
			this.coordinates = CurrentLongitude + " ,"+ CurrentLatitude;
	}

}
