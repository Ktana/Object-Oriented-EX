import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/** this class represents a row from Union output file.
 * Contains private String variables: 5 key labels and one more " extends " key that
 * contains the 40 more dynamic variables. Getters and one constructor that gets 2 Strings: 
 * the first is the keys of data (they separated by ',') and the second String is the "extension" 
 * (also separated by ','). Also – the constructor make sure that the extension does not get over 40 
 * (if we lack of data – it will be spaces).
 * The class contains five comparators (ID, time, alt, lon, lat) for each comparator object 
 * has been done @Override to the Compare function.
 * The class also contains CompareByTime function and CompareByPlace function that we will use on the filter.
 * @authors Alona + Alex
 */
public class CSV_Merged_Row {
	
	//Comparator by time
	
	//"Static" fields
	private String Time  = "";
	private String ID  = "";
	private String Lat  = "";
	private String Lon  = "";
	private String Alt  = "";
	private String Extension ="";
	

	public CSV_Merged_Row(String prefix, String suffix){
		String[] prefix_arr = prefix.split(",");
		try{
		this.Time = DateFormat.adjustTime(prefix_arr[0]);
		this.ID = prefix_arr[1];
		this.Lat = prefix_arr[2];
		this.Lon = prefix_arr[3];
		this.Alt = prefix_arr[4];
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return;
		}
		
		String[] suffix_arr = suffix.split(",");
		if(suffix_arr.length > 41)
		{
			this.Extension = arrayToString(Arrays.copyOfRange(suffix_arr, 0, 41));
		}
		else
		{
			String blanks="";
			for(int i=0;i< 40 - suffix_arr.length;i++)
			{
				blanks = blanks +",";
			}
			this.Extension = arrayToString(suffix_arr) + blanks;
		}
		
	}
	
	@SuppressWarnings("unchecked")
    public static Comparator<CSV_Merged_Row> LONComparator = MyComparatorFactory.getComparator(CSV_Merged_Row.class, "ByLON");
//	public static Comparator<CSV_Merged_Row> LONComparator = new Comparator< CSV_Merged_Row >() {
//        @Override
//        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
//        	if(r1.getLon() == "" || r2.getLon() == "") 
//        		return 0;
//        	return Double.compare(Double.parseDouble (r1.getLon()) , Double.parseDouble (r2.getLon()));
//        }
//    };
    @SuppressWarnings("unchecked")
    public static Comparator<CSV_Merged_Row> LATComparator = MyComparatorFactory.getComparator(CSV_Merged_Row.class, "ByLAT");
//    public static Comparator<CSV_Merged_Row> LATComparator = new Comparator< CSV_Merged_Row >() {
//        @Override
//        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
//        	if(r1.getLat() == "" || r2.getLat() == "") 
//        		return 0;
//        	return Double.compare(Double.parseDouble (r1.getLat()) , Double.parseDouble (r2.getLat()));
//        }
//    };
    @SuppressWarnings("unchecked")
    public static Comparator<CSV_Merged_Row> ALTComparator = MyComparatorFactory.getComparator(CSV_Merged_Row.class, "ByALT");
//    public static Comparator<CSV_Merged_Row> ALTComparator = new Comparator< CSV_Merged_Row >() {
//        @Override
//        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
//        	if(r1.getAlt() == "" || r2.getAlt() == "") return 0;
//			return (int)(Integer.parseInt(r1.getAlt()) - Integer.parseInt(r2.getAlt()));
//        }
//    };
    
    @SuppressWarnings("unchecked")
    public static Comparator<CSV_Merged_Row> TIMEComparator = MyComparatorFactory.getComparator(CSV_Merged_Row.class, "ByTIME");
	
//    public static Comparator<CSV_Merged_Row> TIMEComparator = new Comparator< CSV_Merged_Row >() {
//        @Override
//        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
//        	if(r1.getTime() == "" || r2.getTime() == "") return 0;
//        	SimpleDateFormat formator= new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        	try
//        	{
//        	Date d1 = formator.parse(r1.getTime());
//        	Date d2 = formator.parse(r2.getTime());
//        	return d1.compareTo(d2);
//        	}
//        	
//        	catch(ParseException ex)
//        	{	return 0; }
//        }
//    };
    @SuppressWarnings("unchecked")
    public static Comparator<CSV_Merged_Row> IDComparator = MyComparatorFactory.getComparator(CSV_Merged_Row.class, "ByID");
//    public static Comparator<CSV_Merged_Row> IDComparator = new Comparator< CSV_Merged_Row >() {
//        @Override
//        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
//			return (r1.getID().compareTo(r2.getID()));
//        }
//    };
   
    
    
    
    public static String arrayToString(String array[])
    {
        if (array.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i)
        {
            sb.append(array[i]+",");
        }
        return sb.substring(1);
    }
    
	public String getTime() {return Time;}
	public String getID() {return ID;}
	public String getLat() {return Lat;}
	public String getLon() {return Lon;}
	public String getAlt() {return Alt;}
	public String getExtension() {return Extension;}
	
	public String getMAC1() { String [] mac = this.Extension.split(","); return mac[1];}
	public String getMAC2() {String [] mac = this.Extension.split(","); if(mac.length>5)if(!mac[5].isEmpty()) return mac[5];return "";}
	public String getMAC3() {String [] mac = this.Extension.split(","); if(mac.length>9)if(!mac[9].isEmpty()) return mac[9];return "";}
	public String getMAC4() {String [] mac = this.Extension.split(","); if(mac.length>13)if(!mac[13].isEmpty()) return mac[13];return "";}
	public String getMAC5() {String [] mac = this.Extension.split(","); if(mac.length>17)if(!mac[17].isEmpty()) return mac[17];return "";}
	public String getMAC6() {String [] mac = this.Extension.split(","); if(mac.length>21)if(!mac[21].isEmpty()) return mac[21];return "";}
	public String getMAC7() {String [] mac = this.Extension.split(","); if(mac.length>25)if(!mac[25].isEmpty()) return mac[25];return "";}
	public String getMAC8() {String [] mac = this.Extension.split(","); if(mac.length>29)if(!mac[29].isEmpty()) return mac[29];return "";}
	public String getMAC9() {String [] mac = this.Extension.split(","); if(mac.length>33)if(!mac[33].isEmpty()) return mac[33];return "";}
	public String getMAC10(){String [] mac = this.Extension.split(","); if(mac.length>37)if(!mac[37].isEmpty()) return mac[37];return "";}
	
	
	public String getSignal1() { String [] signal = this.Extension.split(","); return signal[3];}
	public String getSignal2() {String [] signal = this.Extension.split(","); if(signal.length>5)if(!signal[7].isEmpty()) return signal[7];return "";}
	public String getSignal3() {String [] signal = this.Extension.split(","); if(signal.length>9)if(!signal[11].isEmpty()) return signal[11];return "";}
	public String getSignal4() {String [] signal = this.Extension.split(","); if(signal.length>13)if(!signal[15].isEmpty()) return signal[15];return "";}
	public String getSignal5() {String [] signal = this.Extension.split(","); if(signal.length>17)if(!signal[19].isEmpty()) return signal[19];return "";}
	public String getSignal6() {String [] signal = this.Extension.split(","); if(signal.length>21)if(!signal[23].isEmpty()) return signal[23];return "";}
	public String getSignal7() {String [] signal = this.Extension.split(","); if(signal.length>25)if(!signal[27].isEmpty()) return signal[27];return "";}
	public String getSignal8() {String [] signal = this.Extension.split(","); if(signal.length>29)if(!signal[31].isEmpty()) return signal[31];return "";}
	public String getSignal9() {String [] signal = this.Extension.split(","); if(signal.length>33)if(!signal[35].isEmpty()) return signal[35];return "";}
	public String getSignal10(){String [] signal = this.Extension.split(","); if(signal.length>39)if(!signal[39].isEmpty()) return signal[39];return "";}
	
	//Filter functions
	public boolean compareByTime(String operator,String time)
	{
		if(time == "") return false;
    	SimpleDateFormat formator= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	
    	try
    	{
	    	Date d1 = formator.parse(this.Time);
	    	Date d2 = formator.parse(time);
	    	switch(operator)
	    	{
		    	case ">":
		    		return d1.compareTo(d2) > 0;
		    	case "<":
		    		return d1.compareTo(d2) < 0;
		    	default:
		    		return d1.compareTo(d2) == 0;
	    	}
    	}
    	
    	catch(ParseException ex)
    	{	return false; }
	};
	
	public boolean compareByPlace(double lat, double lon, double alt)
			//String criterion)
	{
//		if(Lon == "") return false;
//		if(Lat == "") return false;
//		if(Alt == "") return false;
//	    
//		return (this.Lon+this.Lat+this.Alt).compareTo(criterion) == 0;

		double rad = Math.sqrt(Math.pow(Double.parseDouble(this.Lat) - lat, 2)+
				     Math.pow(Double.parseDouble(this.Lon) - lon, 2)+
				     Math.pow(Double.parseDouble(this.Alt) - alt, 2));
		return (rad < 0.005);
	}

	public boolean compareByMAC(String mac) {
		// TODO Auto-generated method stub
		if(this.getMAC1() == "") return false;
		if(this.getMAC2() == "") return false;
		if(this.getMAC3() == "") return false;
		if(this.getMAC4() == "") return false;
		if(this.getMAC5() == "") return false;
		if(this.getMAC6() == "") return false;
		if(this.getMAC7() == "") return false;
		if(this.getMAC8() == "") return false;
		if(this.getMAC9() == "") return false;
		if(this.getMAC10() == "") return false;
		
		if(this.getMAC1().compareTo(mac) == 0)
			return true;
		if(this.getMAC2().compareTo(mac) == 0)
			return true;
		if(this.getMAC3().compareTo(mac) == 0)
			return true;
		if(this.getMAC4().compareTo(mac) == 0)
			return true;
		if(this.getMAC5().compareTo(mac) == 0)
			return true;
		if(this.getMAC6().compareTo(mac) == 0)
			return true;
		if(this.getMAC7().compareTo(mac) == 0)
			return true;
		if(this.getMAC8().compareTo(mac) == 0)
			return true;
		if(this.getMAC9().compareTo(mac) == 0)
			return true;
		if(this.getMAC10().compareTo(mac) == 0)
			return true;
		
		return false;
	}
	
	public String getMacSignal(String mac) {
		// TODO Auto-generated method stub
		
		if(this.getMAC1().compareTo(mac) == 0)
			return this.getSignal1();
		if(this.getMAC2().compareTo(mac) == 0)
			return this.getSignal2();
		if(this.getMAC3().compareTo(mac) == 0)
			return this.getSignal3();
		if(this.getMAC4().compareTo(mac) == 0)
			return this.getSignal4();
		if(this.getMAC5().compareTo(mac) == 0)
			return this.getSignal5();
		if(this.getMAC6().compareTo(mac) == 0)
			return this.getSignal6();
		if(this.getMAC7().compareTo(mac) == 0)
			return this.getSignal7();
		if(this.getMAC8().compareTo(mac) == 0)
			return this.getSignal8();
		if(this.getMAC9().compareTo(mac) == 0)
			return this.getSignal9();
		if(this.getMAC10().compareTo(mac) == 0)
			return this.getSignal10();
		
		return "";
	}

	
	
    
    
}



//optional fields
/*
private String SSID1  = "";
private String MAC1  = "";
private String Channel1  = "";
private String RSSI1  = "";

private String SSID2  = "";
private String MAC2 = "";
private String Channel2  = "";
private String RSSI2  = "";

private String SSID3  = "";
private String MAC3  = "";
private String Channel3  = "";
private String RSSI3  = "";

private String SSID4  = "";
private String MAC4  = "";
private String Channel4  = "";
private String RSSI4  = "";

private String SSID5  = "";
private String MAC5  = "";
private String Channel5  = "";
private String RSSI5  = "";

private String SSID6  = "";
private String MAC6  = "";
private String Channel6  = "";
private String RSSI6  = "";

private String SSID7  = "";
private String MAC7  = "";
private String Channel7  = "";
private String RSSI7  = "";

private String SSID8  = "";
private String MAC8  = "";
private String Channel8  = "";
private String RSSI8  = "";

private String SSID9  = "";
private String MAC9  = "";
private String Channel9  = "";
private String RSSI9  = "";

private String SSID10  = "";
private String MAC10  = "";
private String Channel10  = "";
private String RSSI10  = "";

public String getSSID1() {return SSID1;}
public String getMAC1() {return MAC1;}
public String getChannel1() {return Channel1;}
public String getRSSI1() {return RSSI1;}

public String getSSID2() {return SSID2;}
public String getMAC2() {return MAC2;}
public String getChannel2() {return Channel2;}
public String getRSSI2() {return RSSI2;}

public String getSSID3() {return SSID3;}
public String getMAC3() {return MAC3;}
public String getChannel3() {return Channel3;}
public String getRSSI3() {return RSSI3;}

public String getSSID4() {return SSID4;}
public String getMAC4() {return MAC4;}
public String getChannel4() {return Channel4;}
public String getRSSI4() {return RSSI4;}

public String getSSID5() {return SSID5;}
public String getMAC5() {return MAC5;}
public String getChannel5() {return Channel5;}
public String getRSSI5() {return RSSI5;}

public String getSSID6() {return SSID6;}
public String getMAC6() {return MAC6;}
public String getChannel6() {return Channel6;}
public String getRSSI6() {return RSSI6;}

public String getSSID7() {return SSID7;}
public String getMAC7() {return MAC7;}
public String getChannel7() {return Channel7;}
public String getRSSI7() {return RSSI7;}

public String getSSID8() {return SSID8;}
public String getMAC8() {return MAC8;}
public String getChannel8() {return Channel8;}
public String getRSSI8() {return RSSI8;}

public String getSSID9() {return SSID9;}
public String getMAC9() {return MAC9;}
public String getChannel9() {return Channel9;}
public String getRSSI9() {return RSSI9;}

public String getSSID10() {return SSID10;}
public String getMAC10() {return MAC10;}
public String getChannel10() {return Channel10;}
public String getRSSI10() {return RSSI10;}
*/


