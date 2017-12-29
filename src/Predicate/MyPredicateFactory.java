package Predicate;
import java.util.ArrayList;
import java.util.function.Predicate;
import Global.CSV_Merged_Row;
import Global.DateFormat;

public class MyPredicateFactory {
	static int i;
	static String TIME_MinVal;
	static String TIME_MaxVal;
	static String ALT_MinVal;
	static String ALT_MaxVal;
	static String LAT_MinVal;
	static String LAT_MaxVal;
	static String LON_MinVal;
	static String LON_MaxVal;
	static String ID_MinVal;
	
	public static Predicate<CSV_Merged_Row> getPredicate(String[] PredicateType, String[] MinVal, String[] MaxVal ,String LogicalOperator)
	{
		if(LogicalOperator == "") return  x->true;
		if(MinVal == null || MinVal.length < 1) return  x->true;
		if(MaxVal == null || MaxVal.length < 1) return  x->true;
		if(PredicateType == null || PredicateType.length < 1) return  x->true;
		
		ArrayList<Predicate<CSV_Merged_Row>> p = new ArrayList<Predicate<CSV_Merged_Row>>() ;
		Predicate<CSV_Merged_Row> result = x->true;		
		Predicate<CSV_Merged_Row> prd_Tmp= x->true;
		
		for( i=0; i< PredicateType.length;i++ )
		{
			if(PredicateType[i] == null)
				continue;
			switch(PredicateType[i].toUpperCase())
			{
			case "TIME":
				if(MinVal[i] != null && MaxVal[i] != null )
				{
					TIME_MinVal = MinVal[i];
					TIME_MaxVal = MaxVal[i];
				}
				prd_Tmp = r -> DateFormat.getDateFromString(r.getTime()).compareTo(DateFormat.getDateFromString(TIME_MinVal)) >=0;
				prd_Tmp.and(r -> DateFormat.getDateFromString(r.getTime()).compareTo(DateFormat.getDateFromString(TIME_MaxVal)) <=0);

				break;
			case "ALT":
				if(MinVal[i] != null && MaxVal[i] != null )
				{
					ALT_MinVal = MinVal[i];
					ALT_MaxVal = MaxVal[i];
				}
				prd_Tmp = r -> Double.parseDouble(r.getAlt()) >= Double.parseDouble(ALT_MinVal) &&
						Double.parseDouble(r.getAlt()) <= Double.parseDouble(ALT_MaxVal);
				break;
			case "LAT":
				if(MinVal[i] != null && MaxVal[i] != null )
				{
					LAT_MinVal = MinVal[i];
					LAT_MaxVal = MaxVal[i];
				}
				prd_Tmp = r -> Double.parseDouble(r.getLat()) >= Double.parseDouble(LAT_MinVal) && 
						Double.parseDouble(r.getLat()) <= Double.parseDouble(LAT_MaxVal);
				break;
			case "LON":
				if(MinVal[i] != null && MaxVal[i] != null )
				{
					LON_MinVal = MinVal[i];
					LON_MaxVal = MaxVal[i];
				}
				prd_Tmp = r -> Double.parseDouble(r.getLon()) >= Double.parseDouble(LON_MinVal) &&
						Double.parseDouble(r.getLon()) <= Double.parseDouble(LON_MaxVal);
				break;
			case "ID_INCLUDE":
				if(MinVal[i] != null && MaxVal[i] != null )
				{
					ID_MinVal = MinVal[i];
				}
				prd_Tmp = r -> r.getID().contains(ID_MinVal);
				break;
			case "ID_EXCLUDE":
				if(MinVal[i] != null && MaxVal[i] != null )
				{
					ID_MinVal = MinVal[i];
				}
				prd_Tmp = r -> r.getID().contains(ID_MinVal);
				prd_Tmp = prd_Tmp.negate();
				break;				
			default:
				break;
			}
			p.add(prd_Tmp);
		}
				
		for(i=0; i< p.size();i++ )
		{
			switch(LogicalOperator.toUpperCase())
			{
			case "AND":
				result = result.and(p.get(i));
				break;
			case "OR":
				result = result.or(p.get(i));
				break;
			case "NOT":
				result = result.and(p.get(i).negate());
				break;
			default:
				result = x->true;	
				break;
			}
		}
		return result;
	}
	
	
}