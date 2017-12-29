package Predicate;
import java.util.ArrayList;
import java.util.function.Predicate;
import Global.CSV_Merged_Row;
import Global.DateFormat;

public class MyPredicateFactory {
	static int i;
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
				prd_Tmp = r -> DateFormat.getDateFromString(r.getTime()).compareTo(DateFormat.getDateFromString(MinVal[i])) >=0;
				prd_Tmp.and(r -> DateFormat.getDateFromString(r.getTime()).compareTo(DateFormat.getDateFromString(MaxVal[i])) <=0);
				break;
			case "ALT":
				prd_Tmp = r -> Double.parseDouble(r.getAlt()) >= Double.parseDouble(MinVal[i]);
				prd_Tmp.and(r -> Double.parseDouble(r.getAlt()) <= Double.parseDouble(MaxVal[i]));
				break;
			case "LAT":
				prd_Tmp = r -> Double.parseDouble(r.getLat()) >= Double.parseDouble(MinVal[i]);
				prd_Tmp.and(r -> Double.parseDouble(r.getLat()) <= Double.parseDouble(MaxVal[i]));
				break;
			case "LON":
				prd_Tmp = r -> Double.parseDouble(r.getLon()) >= Double.parseDouble(MinVal[i]);
				prd_Tmp.and(r -> Double.parseDouble(r.getLon()) <= Double.parseDouble(MaxVal[i]));
				break;
			case "ID_INCLUDE":
				prd_Tmp = r -> r.getID().contains(MinVal[i]);
				break;
			case "ID_EXCLUDE":
				prd_Tmp = r -> r.getID().contains(MinVal[i]);
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
