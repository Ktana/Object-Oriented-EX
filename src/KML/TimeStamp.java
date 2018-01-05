package KML;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

import Global.DateFormat;

@XmlDataType(defaultElementName = "TimeStamp")
/**
 * A class to get the automatic KML generator to create a time stamp tag with all it's fields
 * @author Alex Fishman
 *
 */
public class TimeStamp {
	@XmlElement(pos = 1)
	private String when;

	public String getWhen() {return when;}
	
	/**
	 * Blank constructor to use the interface of Jsefa
	 */
	public TimeStamp(){}


	/**
	 * Constructor from data
	 * @param date
	 */
	public TimeStamp(String date)
	{
		this.when = DateFormat.adjustTime(date
				).toString()+"Z";
	}



}
