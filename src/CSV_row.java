
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator; 


 /** describes the structure of the rows in input files. 
 * This class implements from interface: Comparable<CSV_row>. 
 * Has been done an @Override to CompareTo function (that not in use right now). 
 * This class contains private String variables and private CSV_header_row variable, 
 * getters of the values and one constructor that gets the values of the first line, and CSV_header_row object.
 * There is a function called getCriterionForGroup that returns the key for filtering the rows 
 * (by: time, model, longitude, altitude, latitude).
 * In addition – was defined five comparators for the filter by: time, model, longitude, altitude, 
 * latitude, SSID. For each comparator object has been done @Override to the Compare function.
 * Main use: creates the data rows on Union CSV file.
 * @authors Alona + Alex
 */ 
public class CSV_row implements Comparable <CSV_row> 
{
	private String MAC  = "";
	private String SSID  = "";
	private String AuthMode  = "";
	private String FirstSeen  = "";
	private String Channel  = "";
	private String RSSI  = "";
	private String CurrentLatitude  = "";
	private String CurrentLongitude  = "";
	private String AltitudeMeters  = "";
	private String AccuracyMeters  = "";
	private String Type  = "";
	private CSV_header_row HeaderRow = null;
	
	
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
	
	@Override
	public int compareTo(CSV_row row)
	{
		if(this.Channel == "" || row.Channel == "") return 0;
			return (int)(Integer.parseInt(this.Channel) - Integer.parseInt(row.Channel));	
	}
	
	public static Comparator<CSV_row> LONComparator = new Comparator< CSV_row >() {
        @Override
        public int compare(CSV_row r1, CSV_row r2) {
        	if(r1.getCurrentLongitude() == "" || r2.getCurrentLongitude() == "") 
        		return 0;
        	return Double.compare(Double.parseDouble (r1.getCurrentLongitude()) , Double.parseDouble (r2.getCurrentLongitude()));
        }
    };
    
    public static Comparator<CSV_row> LATComparator = new Comparator< CSV_row >() {
        @Override
        public int compare(CSV_row r1, CSV_row r2) {
        	if(r1.getCurrentLatitude() == "" || r2.getCurrentLatitude() == "") 
        		return 0;
        	return Double.compare(Double.parseDouble (r1.getCurrentLatitude()) , Double.parseDouble (r2.getCurrentLatitude()));
        }
    };
    
    public static Comparator<CSV_row> CHNLComparator = new Comparator< CSV_row >() {
        @Override
        public int compare(CSV_row r1, CSV_row r2) {
        	if(r1.getChannel() == "" || r2.getChannel() == "") return 0;
			return (int)(Integer.parseInt(r1.getChannel()) - Integer.parseInt(r2.getChannel()));
        }
    };
    
    public static Comparator<CSV_row> TIMEComparator = new Comparator< CSV_row >() {
        @Override
        public int compare(CSV_row r1, CSV_row r2) {
        	if(r1.getFirstSeen() == "" || r2.getFirstSeen() == "") return 0;
        	SimpleDateFormat formator= new SimpleDateFormat("dd/MM/yyyy HH:mm");
        	try
        	{
        	Date d1 = formator.parse(r1.getFirstSeen());
        	Date d2 = formator.parse(r2.getFirstSeen());
        	return d1.compareTo(d2);
        	}
        	
        	catch(ParseException ex)
        	{	return 0; }
        }
    };
    
    public static Comparator<CSV_row> LVLComparator = new Comparator< CSV_row >() {
        @Override
        public int compare(CSV_row r1, CSV_row r2) {
 
        	if(r1.getRSSI() == "" || r2.getRSSI() == "") return 0;
			return (int)(Integer.parseInt(r2.getRSSI()) - Integer.parseInt(r1.getRSSI()));	
        }
    };
}

