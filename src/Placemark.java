
import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

@XmlDataType(defaultElementName = "Placemark")

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
	
	public Placemark(){}
	
	public Placemark(String name,String description,String styleUrl,Point point,TimeStamp timeStamp){
		this.name = name;
		this.description = description;
		this.styleUrl = "#green";
		this.Point = point;
		this.TimeStamp = timeStamp;
	}
	
}
