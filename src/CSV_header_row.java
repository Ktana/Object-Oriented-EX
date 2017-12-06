import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;



 /** describes the head line (the labels) on the input file, 
 * it contains private Strings that matching the labels on the original file, 
 * getters of the values and one constructor that gets the relevant values from the header line.
 * Main use: saving data about the devise that collect the data.
 * @authors Alona + Alex
 */
public class CSV_header_row
{
	private String wifi  = "";
	private String appRelease  = "";
	private String model  = "";
	private String release  = "";
	private String device  = "";
	private String display  = "";
	private String board  = "";
	private String brand  = "";
	
	
	public CSV_header_row()
	{
		this.wifi= "";
		this.appRelease= "";
		this.model= "";
		this.release= "";
		this.device= "";
		this.display= "";
		this.board= "";
		this.brand= "";
	}

	public CSV_header_row(String wifi, String appRelease, String model, String release, String device, String display, String board, String brand)
	{
		this.wifi= wifi;
		this.appRelease= appRelease;
		this.model= model;
		this.release= release;
		this.device= device;
		this.display= display;
		this.board= board;
		this.brand= brand;
	}
	
	public String getwifi() { return wifi; }
	public String getappRelease() { return appRelease; };
	public String getmodel() { return model; }
	public String getrelease() { return release; }
	public String getdevice() { return device; }
	public String getdisplay() { return display; };
	public String getboard() { return board; }
	public String getbrand() { return brand; }
}

