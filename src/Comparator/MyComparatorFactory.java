package Comparator;

import Global.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class MyComparatorFactory extends MyComparator {

	public static Comparator getComparator(Class usedClass, String compareBy)
	{
		String className = usedClass.getName();
		className = className.substring(7, className.length());
		switch(className)
		{
		case "CSV_row":
			switch(compareBy)
			{
				case"ByLON":
					return new Comparator< CSV_row >() {
						@Override
						public int compare(CSV_row r1, CSV_row r2) {
							if(r1.getCurrentLongitude() == "" || r2.getCurrentLongitude() == "") 
								return 0;
							return Double.compare(Double.parseDouble (r1.getCurrentLongitude()) , Double.parseDouble (r2.getCurrentLongitude()));
						}
					};
	
				case"ByLAT":
					return new Comparator< CSV_row >() {
						@Override
						public int compare(CSV_row r1, CSV_row r2) {
							if(r1.getCurrentLatitude() == "" || r2.getCurrentLatitude() == "") 
								return 0;
							return Double.compare(Double.parseDouble (r1.getCurrentLatitude()) , Double.parseDouble (r2.getCurrentLatitude()));
						}
					};
	
				case"ByCHNL":
					return new Comparator< CSV_row >() {
						@Override
						public int compare(CSV_row r1, CSV_row r2) {
							if(r1.getChannel() == "" || r2.getChannel() == "") return 0;
							return (int)(Integer.parseInt(r1.getChannel()) - Integer.parseInt(r2.getChannel()));
						}
					};
	
				case"ByTIME":
					return new Comparator< CSV_row >() {
						@Override
						public int compare(CSV_row r1, CSV_row r2) {
							if(r1.getFirstSeen() == "" || r2.getFirstSeen() == "") return 0;
							SimpleDateFormat formator= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
	
				case"ByLVL":
					return new Comparator< CSV_row >() {
						@Override
						public int compare(CSV_row r1, CSV_row r2) {
	
							if(r1.getRSSI() == "" || r2.getRSSI() == "") return 0;
							return (int)(Integer.parseInt(r2.getRSSI()) - Integer.parseInt(r1.getRSSI()));	
						}
					};
			}
		 case "CSV_Merged_Row":
			switch(compareBy)
			{
			case"ByTIME":
				return new Comparator< CSV_Merged_Row >() {
			        @Override
			        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
			        	if(r1.getTime() == "" || r2.getTime() == "") return 0;
			        	SimpleDateFormat formator= new SimpleDateFormat("dd/MM/yyyy HH:mm");
			        	try
			        	{
			        	Date d1 = formator.parse(r1.getTime());
			        	Date d2 = formator.parse(r2.getTime());
			        	return d1.compareTo(d2);
			        	}
			        	
			        	catch(ParseException ex)
			        	{	return 0; }
			        }
			    };
			    
			case"ByLON":
				return new Comparator< CSV_Merged_Row >() {
			        @Override
			        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
			        	if(r1.getLon() == "" || r2.getLon() == "") 
			        		return 0;
			        	return Double.compare(Double.parseDouble (r1.getLon()) , Double.parseDouble (r2.getLon()));
			        }
			    };
			    
			case"ByLAT":
				return new Comparator< CSV_Merged_Row >() {
			        @Override
			        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
			        	if(r1.getLat() == "" || r2.getLat() == "") 
			        		return 0;
			        	return Double.compare(Double.parseDouble (r1.getLat()) , Double.parseDouble (r2.getLat()));
			        }
			    };
			    
			case"ByALT":
				return new Comparator< CSV_Merged_Row >() {
			        @Override
			        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
			        	if(r1.getAlt() == "" || r2.getAlt() == "") return 0;
						return (int)(Integer.parseInt(r1.getAlt()) - Integer.parseInt(r2.getAlt()));
			        }
			    };
			    
			case"ByID":
				return new Comparator< CSV_Merged_Row >() {
			        @Override
			        public int compare(CSV_Merged_Row r1, CSV_Merged_Row r2) {
						return (r1.getID().compareTo(r2.getID()));
			        }
			    };
			}
		default:
			break;
		}
		return null;
	}

}


