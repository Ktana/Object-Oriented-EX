package Global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * The class deals with errors that occur because of date format problems.
 * @author Alex Fishman
 *
 */
public class DateFormat {

/**
 * Generates the sent date param as required in my program
 * @param inDate
 * @return inDate in the format I need it to be
 */
	public static String formatDate(String inDate) {
		String outDate = "1900-01-01";

		inDate = inDate.replace("/", "-");

		String[] splitDate = inDate.split("-");

		if(splitDate.length == 3)
		{
			if(splitDate[0].length() == 2)
			{
				outDate = splitDate[2]+"-"+splitDate[1]+"-"+splitDate[0];
			}
			else
			{
				outDate = inDate;
			}
		}
		return outDate;
	}
	
	/**
	 * Adjusting time to "my requirements".
	 * @param time
	 * @return time as String in my required format.
	 */
	public static String adjustTime(String time)
	{
		do
		{ //replace double blanks
			time = time.replace("  ", " ");
		}while(time.contains("  "));
		
		String [] array = time.split(" ");
		switch(array.length)
		{
		case 1: 
			return formatDate(array[0])+" 00:00:00";
		case 2:
			String [] timeArray = array[1].split(":");
			switch(timeArray.length)
			{
			case 1: return formatDate(array[0])+" "+timeArray[0]+":00:00";
			case 2: return formatDate(array[0])+" "+timeArray[0]+":"+timeArray[1]+":00";
			case 3: return time;
			default:
				return formatDate(array[0])+" 00:00:00";
			}
		default: 
			return "1900-01-01 00:00:00";
		}
	}

	/**
	 * Generates the date in the correct format
	 * @param inDate
	 * @param format
	 * @return the correct date as a String
	 */
	public static String formatDate(String inDate, String format) {
		String outDate = "1900-01-01";

		inDate = inDate.replace("/", "-");

		String[] splitDate = inDate.split("-");

		if(splitDate.length == 3)
		{
			switch(format){
			case "DD-MM-YYYY":
				if(splitDate[0].length() == 2)
				{
					outDate = splitDate[2]+"-"+splitDate[1]+"-"+splitDate[0];
				}
				else
				{
					outDate = inDate;
				}
				break;

			case "DD/MM/YYYY":
				if(splitDate[0].length() == 2)
				{
					outDate = splitDate[2]+"/"+splitDate[1]+"/"+splitDate[0];
				}
				else
				{
					outDate = inDate.replace("-", "/");;
				}
				break;
				
			default: break;
			}
		}

		return outDate;

	}
	
	/**
	 * Determines what is the format of the current gotten date.
	 * created to solve the date format problems.
	 * @param date
	 * @return it's structure
	 */
	public static String getDateFormat(String date) {
		if(date.indexOf("-")>-1)
			return "DD-MM-YYYY";
		
		if(date.indexOf("/")>-1)
			return "DD/MM/YYYY";
		return "";
	}

	/**
	 * Parsing function
	 * @param date
	 * @return the same date in Date format
	 */
	public static Date getDateFromString(String date)
	{
		if(date == "") return null;
		
		Date d1;
		SimpleDateFormat formator;
		date = DateFormat.adjustTime(date);
		if(getDateFormat(date)=="DD/MM/YYYY"){
			 formator= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		}
		else{
			formator= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		
		try
		{
		 d1 = formator.parse(date);
		}
		catch(ParseException ex)
		{	
			System.out.println(ex.getMessage());
			return null; 
		}
		return d1;
	}
}