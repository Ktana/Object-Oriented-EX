/**
 * @authors Alona(321894834) + Alex(319451514)
 */ 

import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

import java.util.Comparator; 

@XmlDataType(defaultElementName = "Placemark")
public class CSV_row //implements Comparable <CSV_row> 
{
	@XmlElement(pos = 1)
	String MAC  = "";
	@XmlElement( pos = 2)
	 String SSID  = "";
	@XmlElement( pos = 3)
	 String AuthMode  = "";
	@XmlElement( pos = 4)
	 String FirstSeen  = "";
	@XmlElement( pos = 5)
	 String Channel  = "";
	@XmlElement( pos = 6)
	 String RSSI  = "";
	@XmlElement( pos = 7)
	 String CurrentLatitude  = "";
	@XmlElement( pos = 8)
	 String CurrentLongitude  = "";
	@XmlElement( pos = 9)
	 String AltitudeMeters  = "";
	@XmlElement( pos = 10)
	 String AccuracyMeters  = "";
	@XmlElement(pos = 11)
	 String Type  = "";
	
	private CSV_header_row HeaderRow = null;
	
	public CSV_row(){
		
	}
	
	public CSV_row(String MAC, String SSID, String AuthMode, String FirstSeen, String Channel, String RSSI, String CurrentLatitude, String CurrentLongitude, String AltitudeMeters, String AccuracyMeters, String Type,CSV_header_row header)
	{
		this.MAC  = MAC;
		this.SSID  = SSID;
		this.AuthMode  = AuthMode;
		this.FirstSeen  = FirstSeen;
		this.Channel  = Channel;
		this.RSSI  = RSSI;
		this.CurrentLatitude  = CurrentLatitude;
		this.CurrentLongitude  = CurrentLongitude;
		this.AltitudeMeters  = AltitudeMeters;
		this.AccuracyMeters  = AccuracyMeters;
		this.Type  = Type;
		
		if(header == null)
			this.HeaderRow = new CSV_header_row();
		else
			this.HeaderRow = header;
	}
	
	public String getMAC() { return MAC; }
	public String getSSID() { return SSID; };
	public String getAuthMode() { return AuthMode; }
	public String getFirstSeen() { return FirstSeen; }
	public String getChannel() { return Channel; }
	public String getRSSI() { return RSSI; };
	public String getCurrentLatitude() { return CurrentLatitude; }
	public String getCurrentLongitude() { return CurrentLongitude; }
	public String getAltitudeMeters() { return AltitudeMeters; }
	public String getAccuracyMeters() { return AccuracyMeters; }
	public String getType() { return Type; }
	public CSV_header_row getHeaderRow() { return HeaderRow; }
	
	public String getCriterionForGroup()
	{
		return this.FirstSeen+","+this.HeaderRow.getmodel()+","+this.CurrentLatitude+","+this.CurrentLongitude+","+this.AltitudeMeters;
	}

	@SuppressWarnings("unchecked")
	 public static Comparator<CSV_row> LONComparator = MyComparatorFactory.getComparator(CSV_row.class,"ByLON");
			
	@SuppressWarnings("unchecked")
	 public static Comparator<CSV_row> LATComparator = MyComparatorFactory.getComparator(CSV_row.class,"ByLAT");

	@SuppressWarnings("unchecked")
	 public static Comparator<CSV_row> CHNLComparator = MyComparatorFactory.getComparator(CSV_row.class,"ByCHNL");

	@SuppressWarnings("unchecked")
	 public static Comparator<CSV_row> TIMEComparator = MyComparatorFactory.getComparator(CSV_row.class,"ByTIME");

	@SuppressWarnings("unchecked")
	 public static Comparator<CSV_row> LVLComparator = MyComparatorFactory.getComparator(CSV_row.class,"ByLVL");
}

