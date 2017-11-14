/**
 * @authors Alona(321894834) + Alex(319451514)
 * This function read KML file and write it to CSV table 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class toCSVtoKML {
	private static StringBuilder sb = new StringBuilder();
	public static List<CSV_row> rowList = new ArrayList<CSV_row>();
	public static List<CSV_Merged_Row> rowMergeList = new ArrayList<CSV_Merged_Row>();

	/**
	 * @credit readCSV function (surround) see:
	 * https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file 
	 */


	public static void readCSV(String fileName){	
		CSV_header_row header_row =null;
		String line=null;

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader bf = new BufferedReader(fr);

			line = bf.readLine();

			while(line != null) 
			{
				//separate all values between ","
				String [] strs = line.split(",");

				if(strs.length == 8) //The  First header row, should be inserted into CSV_row in the next iteration
				{
					header_row = new CSV_header_row(strs[0],strs[1],strs[2], strs[3], strs[4], strs[5], strs[6], strs[7]);
					line = bf.readLine();
				}
				else if(strs.length == 11)
				{
					CSV_row row= new CSV_row(strs[0],strs[1],strs[2], strs[3], strs[4], strs[5], strs[6], strs[7], strs[8],strs[9],strs[10],header_row );
					rowList.add(row);
				}
				else
				{
					//Do nothing: row is not valid (there are not enough columns in file )
				}

				line = bf.readLine();
			}

			fr.close();
			bf.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void mergeData(String fileName)
	{

		/* from https://metanit.com/java/tutorial/10.7.php
		 * ***************Groupping**********
		 */

		Stream<CSV_row> CSV_Stream = rowList.stream().sorted(CSV_row.LVLComparator);

		Map<String, List< CSV_row >> CSV_GrouppedBy = CSV_Stream.collect(
				Collectors.groupingBy(CSV_row::getCriterionForGroup));

		for(Map.Entry<String, List< CSV_row>> item : CSV_GrouppedBy.entrySet()){

			String prefix= item.getKey();
			String suffix="";
			for(CSV_row csv_row : item.getValue()){     
				String SSID = csv_row.getSSID();
				String MAC =  csv_row.getMAC();
				String CHANEL = csv_row.getChannel();
				String SIGNAL = csv_row.getRSSI();
				suffix = suffix + ","+SSID+","+MAC+","+CHANEL+","+SIGNAL;
			}

			rowMergeList.add(new CSV_Merged_Row(prefix,suffix));
		} 

		//Filter from http://qaru.site/questions/289/what-is-the-best-way-to-filter-a-java-collection

		//By Alt
		//		    rowMergeList = rowMergeList.stream()
		//		    	    .filter(r -> r.getAlt().compareTo("52") == 0).collect(Collectors.toList());

		//By ID
		//		    rowMergeList = rowMergeList.stream()
		//		    	    .filter(r -> r.getID().contains("IPHONE")).collect(Collectors.toList());

		//By Time
		//		    rowMergeList = rowMergeList.stream()
		//		    	    .filter(r -> r.compareByTime(">","28/10/2017  20:10:00")).collect(Collectors.toList());

		//By Place Lat,Lon.Alt 34.87115922	32.09115523	54
	//	rowMergeList = rowMergeList.stream()
	//			.filter(r -> r.compareByPlace("34.87115922"+"32.09115523"+"54")).collect(Collectors.toList());

		//Comparator from https://javadevblog.com/primer-sortirovki-s-pomoshh-yu-java-comparable-i-comparator.html
		//Sort by TIME
		Collections.sort(rowMergeList, CSV_Merged_Row.TIMEComparator);
		//Sort by ID
		Collections.sort(rowMergeList, CSV_Merged_Row.IDComparator);

		CreateCSV_Merged_File(getFName(fileName) + "_Merged.csv",rowMergeList);
		/*
		 ******************************************
		 */


		//Sort by CHNL
		//Collections.sort(rowList, CSV_row.CHNLComparator );

		//Sort by LON
		//Collections.sort(rowList, CSV_row.LONComparator );

		//Sort by LAT
		//Collections.sort(rowList, CSV_row.LATComparator);

		//Sort by TIME
		//Collections.sort(rowList, CSV_row.TIMEComparator);

		//Sort by LVL
		//Collections.sort(rowList, CSV_row.LVLComparator);

		System.out.println("Done Sort/filter --> Location: C:/ex0/OUT/");



	}


	public static void CreateCSV_Merged_File(String fileName,List<CSV_Merged_Row> mergedRowList){
		//Write in file
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(fileName));

			sb = new StringBuilder();

			sb.append("Time,");
			sb.append("ID,");
			sb.append("Lat,");
			sb.append("Lon,");
			sb.append("Alt,");
			sb.append("SSID1,");
			sb.append("MAC1,");
			sb.append("Frequency1,");
			sb.append("Signal1,");
			sb.append("SSID2,");
			sb.append("MAC2,");
			sb.append("Frequency2,");
			sb.append("Signal2,");
			sb.append("SSID3,");
			sb.append("MAC3,");
			sb.append("Frequency3,");
			sb.append("Signal3,");
			sb.append("SSID4,");
			sb.append("MAC4,");
			sb.append("Frequency4,");
			sb.append("Signal4,");
			sb.append("SSID5,");
			sb.append("MAC5,");
			sb.append("Frequency5,");
			sb.append("Signal5,");
			sb.append("SSID6,");
			sb.append("MAC6,");
			sb.append("Frequency6,");
			sb.append("Signal6,");
			sb.append("SSID7,");
			sb.append("MAC7,");
			sb.append("Frequency7,");
			sb.append("Signal7,");
			sb.append("SSID8,");
			sb.append("MAC8,");
			sb.append("Frequency8,");
			sb.append("Signal8,");
			sb.append("SSID9,");
			sb.append("MAC9,");
			sb.append("Frequency9,");
			sb.append("Signal9,");
			sb.append("SSID10,");
			sb.append("MAC10,");
			sb.append("Frequency10,");
			sb.append("Signal10,");
			sb.append('\n');

			for (int i = 0; i < mergedRowList.size(); i++) 
			{
				sb.append(mergedRowList.get(i).getTime());
				sb.append(',');
				sb.append(mergedRowList.get(i).getID());
				sb.append(',');
				sb.append(mergedRowList.get(i).getLon());
				sb.append(',');
				sb.append(mergedRowList.get(i).getLat());
				sb.append(',');
				sb.append(mergedRowList.get(i).getAlt());
				sb.append(',');
				sb.append(mergedRowList.get(i).getExtension());
				sb.append('\n');
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		pw.write(sb.toString());
		pw.close();

	}


	public static String getFName(String fileName) {
		int lastPos= fileName.lastIndexOf(".");
		if(lastPos < 0) return fileName;

		return fileName.substring(0, lastPos);
	}

	public static void writeToCsv(String fileName,List<CSV_row> rowList)
	{
		//Write in file
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(fileName));

			sb = new StringBuilder();

			for (int i = 0; i < rowList.size(); i++) 
			{
				sb.append(rowList.get(i).getMAC());
				sb.append(',');
				sb.append(rowList.get(i).getSSID());
				sb.append(',');
				sb.append(rowList.get(i).getAuthMode());
				sb.append(',');
				sb.append(rowList.get(i).getFirstSeen());
				sb.append(',');
				sb.append(rowList.get(i).getChannel());
				sb.append(',');
				sb.append(rowList.get(i).getRSSI());
				sb.append(',');
				sb.append(rowList.get(i).getCurrentLatitude());
				sb.append(',');
				sb.append(rowList.get(i).getCurrentLongitude());
				sb.append(',');
				sb.append(rowList.get(i).getAltitudeMeters());
				sb.append(',');
				sb.append(rowList.get(i).getAccuracyMeters());
				sb.append(',');
				sb.append(rowList.get(i).getType());
				sb.append('\n');
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		pw.write(sb.toString());
		pw.close();
	}

	/**
	*this function gets the path of CSV file and write a KML file.
	*@param fileName need to be a path on your computer this is a String.
	*@return KML file to chosen path.
	*/
	public static void toKML(String fileName) {
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader bf = new BufferedReader(fr);
			String line = bf.readLine();
			line = bf.readLine();
			PrintWriter pw = new PrintWriter(new File(fileName+".kml"));


			pw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>   ");
			pw.write("\n");
			pw.write("<kml xmlns=\"http://earth.google.com/kml/2.0\">   ");
			pw.write("\n");
			pw.write("<Document>   ");
			pw.write("\n");

			while(line!=null) {
				String [] allData = line.split(",");
				//1
				if(allData.length>5) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[5]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[6]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[7]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}
				//2
				if(allData.length>9) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[9]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[10]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[11]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}
				//3
				if(allData.length>13) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[13]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[14]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[15]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}
				//4
				if(allData.length>17) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[17]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[18]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[19]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}
				//5
				if(allData.length>21) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[21]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[22]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[23]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}
				//6
				if(allData.length>25) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[25]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[26]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[27]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}
				//7
				if(allData.length>29) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[29]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[30]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[31]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}
				//8
				if(allData.length>33) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[33]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[34]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[35]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}
				//9
				if(allData.length>37) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[37]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[38]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[39]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}
				//10
				if(allData.length>43) {
				pw.write("<Placemark>");
				pw.write("<name><![CDATA["+allData[41]+"]]></name>\n");
				pw.write("<description><![CDATA[BSSID: <b>"+allData[42]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+allData[43]+"</b><br/>Timestamp: <b></b><br/>Date: <b>"+allData[0]+"</b>]]></description><styleUrl>#green</styleUrl>\n");
				pw.write("<Point><coordinates>"+allData[2]+" ,"+allData[3]+"</coordinates></Point>\n");
				pw.write("</Placemark>\n");
				pw.write("\n");
				}

				line = bf.readLine();
			}

			pw.write("</Document>");
			pw.write("\n");
			pw.write("</kml>");
			pw.close();

			System.out.println("Done write kml file --> Location: C:/ex0/OUT/");
			fr.close();
			bf.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		String input_path = "C:/ex0";
		String output_path = "C:/ex0/OUT/";

		List<String> fileNameArray = new ArrayList<String>();
		Path folder = Paths.get(input_path);
		try {
			Files.newDirectoryStream(folder,"*.csv").forEach(s -> fileNameArray.add(s.toString()));	
		}
		catch(IOException e) {
			e.printStackTrace();	
		}

		for (int i = 0; i < fileNameArray.size(); i++) {
			readCSV(fileNameArray.get(i));
		}
		mergeData(output_path +"RESULT");
		
		toKML("C:\\ex0\\OUT\\RESULT_Merged.csv");
	}



}
