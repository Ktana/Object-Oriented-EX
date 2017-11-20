
import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

@XmlDataType(defaultElementName = "TimeStamp")
public class TimeStamp {
	@XmlElement(pos = 1)
	private String when;

	public String getWhen() {return when;}
	
	public TimeStamp(){}

	public TimeStamp(String date)
	{
		this.when = date;	
	}



}
