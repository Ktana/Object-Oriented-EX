package Algorithms;
import java.util.Scanner;

/**this class represents coordinate that include altitude,
 *longitude and latitude and also MAC's signal of this coordinate.
 * @author Alona+Alex
 */
public class Full_Coordinate {
	
	private double lat;
	private double lon;
	private double alt;
	private double signal;
	
	/**empty constractor
	 * @author Alona+Alex
	 */
	public Full_Coordinate() {
		this.lat = 1.0;
		this.lon = 1.0;
		this.alt = 1.0;
		this.signal = -120;
	}
	
	/**constractor that gets a coordinated as doubles
	 * @author Alona+Alex
	 */
	public Full_Coordinate(double lat, double lon, double alt, double signal) {
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.signal = signal;
	}
	
	/**constractor that gets a class
	 * @author Alona+Alex
	 */
	public Full_Coordinate(Full_Coordinate fc) {
		this.lat = fc.lat;
		this.lon = fc.lon;
		this.alt = fc.alt;
		this.signal = fc.signal;
	}
	
	/**constractor that parses String to doubles, need to use a specific format
	 * @param	String	"lat,lon,alt"
	 * @author Alona+Alex
	 */
	public Full_Coordinate(String fc) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(fc).useDelimiter(",");
		this.lat = sc.nextDouble();
		this.lon = sc.nextDouble();
		this.alt = sc.nextDouble();
		this.signal = sc.nextDouble();
	}
	
	public String toString() {return "[lat: "+lat+", lon: "+lon+", alt: "+alt+", signalW: "+signal+"]";}
	public String toStringGUI() {return alt+", "+lon+", "+alt;}

	public String LATtoString() {return String.valueOf(this.lat);}
	public String LONtoString() {return String.valueOf(this.lon);}
	public String ALTtoString() {return String.valueOf(this.alt);}
	public String SignaltoString() {return String.valueOf(this.signal);}
		
	public double getLat() {return lat;}
	public double getLon() {return lon;}
	public double getAlt() {return alt;}
	public double getSignal() {return signal;}
	public void setLat(double lat) {this.lat = lat;}
	public void setLon(double lon) {this.lon = lon;}
	public void setAlt(double alt) {this.alt = alt;}
	public void setSignal(double signal) {this.signal = signal;}

	public void main(String[] args){
		String f_c = "34.80985384,32.16233648,24,-90";
		Full_Coordinate fc = new Full_Coordinate(f_c);
		System.out.println(fc);
		Full_Coordinate fc2 = new Full_Coordinate(fc);
		System.out.println(fc2);
		Full_Coordinate fc3 = new Full_Coordinate();
		System.out.println(fc3);	
		String test = fc.SignaltoString();
		System.out.println(test);
		fc3.setSignal(-4);
		System.out.println(fc3);
	}

}
