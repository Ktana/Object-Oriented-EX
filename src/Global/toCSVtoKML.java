package Global;

import Comparator.*;
import KML.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsefa.xml.XmlIOFactory;
import org.jsefa.xml.XmlSerializer;
import org.jsefa.xml.namespace.QName;

/** in this class we define List<CSV_row> variable that gets data from input file, and List<SCV_Merged_row> 
 * that gets data that will appear on output file.
 * readCSV function – gets file name of input path and read the information from this file into rowList object.
 * mergeData function – collects filtered data from rowList (by the signal) and make another object called 
 * rowMergeList, and now we able to filter by several criteria's (ID, time) by the comparator object that 
 * defined in SCV_Merged_row class.
 * createCSV_Merged_File function – gets output name and the mergedRowList object and creates the output file.
 * toKML function – gets the path of  Union CSV file that was created by us and create KML file into our 'OUT' folder.
 * @authors Alona + Alex 
 */
public class toCSVtoKML {
	private static StringBuilder sb = new StringBuilder();
	public static List<CSV_row> rowList = new ArrayList<CSV_row>();
	public static List<CSV_Merged_Row> rowMergeList = new ArrayList<CSV_Merged_Row>();
	public static List<Placemark> PlacemarkList = new ArrayList<Placemark>();

	public static List<CSV_Merged_Row> getRowMergeList() {
		return rowMergeList;
	}
	
	/**this function gets the path of CSV file and read it.
	 *then, use CSV_row and CSV_header_row objects in order to fill it with the data from the file.
	 *@see CSV_row	CSV_row object class
	 *@see CSV_header_row	CSV_header_row object class
	 *@param fileName	need to be a path on your computer this is a String.
	 *@credit readCSV function (surround) see: https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file 
	 *@return void
	 */
	public static void readCSV(String fileName){	
		CSV_header_row header_row =null;
		String line=null;

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader bf = new BufferedReader(fr);

			line = bf.readLine();

			while(line != null) {
				//separate all values between ","
				String [] strs = line.split(",");

				if(strs.length == 8) //The  First header row, should be inserted into CSV_row in the next iteration
				{
					header_row = new CSV_header_row(strs[0],strs[1],strs[2], strs[3], strs[4], strs[5], strs[6], strs[7]);
					line = bf.readLine();
				}
				else if(strs.length == 11){
					CSV_row row= new CSV_row(strs[0],strs[1],strs[2], strs[3], strs[4], strs[5], strs[6], strs[7], strs[8],strs[9],strs[10],header_row );
					rowList.add(row);
				}
				else{
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

	/**this function collects filtered data from rowList (by the signal) and make another object called rowMergeList, 
	 *and now we able to filter by several criteria's (ID, time) by the comparator object that defined in 
	 *CSV_Merged_row class.
	 *@see CSV_Merged_row		CSV_Merged_row object class
	 *@param fileName	need to be a path on your computer this is a String.
	 *@credit https://metanit.com/java/tutorial/10.7.php
	 *@return void
	 */
	public static void mergeData(String fileName){
		/* from https://metanit.com/java/tutorial/10.7.php
		 * ***************Groupping**********
		 */

		@SuppressWarnings("unchecked")
		Stream<CSV_row> CSV_Stream = rowList.stream().sorted(MyComparatorFactory.getComparator(CSV_row.class, "ByLVL"));

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
				//System.out.println("\nsuffix="+suffix);
			}

			rowMergeList.add(new CSV_Merged_Row(prefix,suffix));
		} 

		/**
		 * Filter 
		 * @author: http://qaru.site/questions/289/what-is-the-best-way-to-filter-a-java-collection
		 * */
		

		//By Alt
//				    rowMergeList = rowMergeList.stream()
//				    	    .filter(r -> (r.getAlt().compareTo("19") >= 0) && (r.getAlt().compareTo("24") <= 0)).collect(Collectors.toList());

		//By Lat
//	    rowMergeList = rowMergeList.stream()
//	    	    .filter(r -> (r.getLat().compareTo("32.16") >= 0) && (r.getLat().compareTo("32.17") <= 0)).collect(Collectors.toList());

		//By Lon
//	    rowMergeList = rowMergeList.stream()
//	    	    .filter(r -> (r.getLon().compareTo("34.81") >= 0) && (r.getLon().compareTo("34.83") <= 0)).collect(Collectors.toList());
		
		//By ID
//				    rowMergeList = rowMergeList.stream()
//				    	    .filter(r -> r.getID().contains("Lenovo")).collect(Collectors.toList());

		//By Time
//				    rowMergeList = rowMergeList.stream()
//				    	    .filter(r -> (r.compareByTime(">","2017-10-27 16:14:37")) && (r.compareByTime("<","2017-10-27 16:30:37"))).collect(Collectors.toList());

		//By Place: Lat,Lon,Alt 34.813	32.1685	54 ---> "34.87115922"+"32.09115523"+"54"
//			rowMergeList = rowMergeList.stream()
//					.filter(r -> r.compareByPlace(32.168,34.813,38)).collect(Collectors.toList());
		
		//By MAC
		List<CSV_Merged_Row> rowMergeMACList = new ArrayList<CSV_Merged_Row>();
		String mac="3c:1e:04:03:7f:17";//"ec:8c:a2:48:f1:98";////"3c:1e:04:03:7f:17" 14:cc:20:c8:83:9c;
		rowMergeMACList = rowMergeList.stream()
				.filter(r ->  r.compareByMAC(mac)).collect(Collectors.toList());
		//System.out.println(rowMergeMACList.toString());
		
		for (int i = 0; i < rowMergeMACList.size(); i++) {
				String alt = rowMergeMACList.get(i).getAlt();
				String lat = rowMergeMACList.get(i).getLat();
				String lon = rowMergeMACList.get(i).getLon();
				String sig = rowMergeMACList.get(i).getMacSignal(mac);
				
				//System.out.println("("+lat+","+lon+","+alt+"),"+" Signal="+sig+"\n");
		}
		
		

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

	 /**this function gets output name and the mergedRowList object and then creates the output file.
	 *@param String fileName		need to be a path on your computer this is a String.
	 *@param List<CSV_Merged_Row> mergedRowList
	 *@see CSV_Merged_Row 	CSV_Merged_Row
	 *@return void
	 */
	public static void CreateCSV_Merged_File(String fileName,List<CSV_Merged_Row> mergedRowList){
		//Write in file
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(fileName));

			sb = new StringBuilder();

			sb.append("Time,");
			sb.append("ID,");
			sb.append("Lon,");
			sb.append("Lat,");
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

	/**
	 *@param fileName	a string  that represents a file path
	 *@return name of the file without the file format.
	 */
	private static String getFName(String fileName) {
		int lastPos= fileName.lastIndexOf(".");
		if(lastPos < 0) return fileName;
		return fileName.substring(0, lastPos);
	}

	/**this function gets the path of CSV file and List of CSV_row and writes a file.
	 *@see CSV_row	CSV_row object class
	 *@param fileName	need to be a path on your computer this is a String.
	 *@param rowList	an object that was created for that function.
	 *@return void
	 */
	public static void writeToCsv(String fileName,List<CSV_row> rowList){
		//Write in file
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(fileName));
			sb = new StringBuilder();
			for (int i = 0; i < rowList.size(); i++) {
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
	 * This function creates the format of KML and assembles it as a full KML string
	 * */

	public static StringWriter ExportPlacemarkListToXML() {
		XmlSerializer serializer = XmlIOFactory.createFactory(Placemark.class).createSerializer();
		StringWriter writer = new StringWriter();
		serializer.open(writer);
		serializer.getLowLevelSerializer().writeXmlDeclaration("1.0", "UTF-8");	
		serializer.getLowLevelSerializer().writeStartElement(QName.create("kml"));
		serializer.getLowLevelSerializer().writeStartElement(QName.create("Document"));

		for (Placemark object : PlacemarkList) serializer.write(object);
		serializer.getLowLevelSerializer().writeEndElement();
		serializer.getLowLevelSerializer().writeEndElement();

		serializer.close(true);
		//System.out.println(writer);
		return writer;
	}

	/**
	 * This function initializes the KML Placemark tags as a list with data collected from CSV 
	 * original file we got
	 **/

	public static void createPlacemarkListFromCsvFile(){
		final String cdata = "<![CDATA[{0}]]>"; 
		for (int i = 0; i < rowList.size(); i++) {
			String name = cdata.replace("{0}", rowList.get(i).getSSID());
			String description = "BSSID: <b>"+rowList.get(i).getMAC()+
					"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+rowList.get(i).getChannel()+
					"</b><br/>Timestamp: <b></b>"//<br/>Date: <b>"
					+rowList.get(i).getFirstSeen();
					//"</b>";
			description = cdata.replace("{0}", description);
			String CurrentLongitude = rowList.get(i).getCurrentLongitude();
			String CurrentLattitude = rowList.get(i).getCurrentLatitude();
			String date = rowList.get(i).getFirstSeen();
			Placemark tmp = new Placemark(name,description,"",new Point(CurrentLongitude,CurrentLattitude),new TimeStamp(date));
			PlacemarkList.add(tmp);
		}
	}

/**
 * @param StringWriter XML_data, String fileName
 * this function writes into KML file of the name it gets the data it got
 **/
	public static void saveToKMLFile(StringWriter XML_data, String fileName){
		//Write in file
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(fileName));	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		pw.write(XML_data.toString().replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<kml>", "<kml xmlns=\"http://earth.google.com/kml/2.0\">   "));
		pw.close();
	}

	public static void run() {
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

		createPlacemarkListFromCsvFile();
		StringWriter XML_data = ExportPlacemarkListToXML();
		saveToKMLFile(XML_data,output_path + "RESULT.kml");
	}
		
	
	/////////////////////////////////MAIN://///////////////////////////////////
	
	public static void main(String[] args){
		run();
	}
	
}
