package KML;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

import Global.DateFormat;

@XmlDataType(defaultElementName = "TimeStamp")
public class TimeStamp {
	@XmlElement(pos = 1)
	private String when;

	public String getWhen() {return when;}
	
	public TimeStamp(){}

	public TimeStamp(String date)
	{
//		this.when = LocalDateTime.parse(date , DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" )
//			).toString()+"Z";
		this.when = DateFormat.adjustTime(date
				).toString()+"Z";
	}



}
