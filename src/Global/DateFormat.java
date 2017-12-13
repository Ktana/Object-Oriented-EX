package Global;

public class DateFormat {

	
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
	
	public static String adjustTime(String time)
	{
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
}
