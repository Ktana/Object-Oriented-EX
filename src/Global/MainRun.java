package Global;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Algorithms.*;
import GUI.*;


public class MainRun {
	
	private String folderPath;
	private String filePath;
	private String meregedPath;
	private String maxTime;
	private String minTime;
	private String maxLAT;
	private String minLAT;
	private String maxLON;
	private String minLON;
	private String maxALT;
	private String minALT;
	
	public static String[] PredicateType = new String[5];
	public static String[] MinVal= new String[5];
	public static String[] MaxVal= new String[5];
	public static String LogicalOperator = "CLEAR_FILTER";
	

	public void saveFolderPath(String path){
		this.folderPath = path;
		System.out.println(folderPath);
	}
	
	public void saveFilePath(String path){
		this.filePath = path;
		System.out.println(filePath);
	}
	
	public void saveMeregedPath(String path){
		this.meregedPath = path;
		System.out.println(meregedPath);
	}

	public String getFolderPath()
	{
		return this.folderPath;
	}

	public void saveToCSV()
	{
		String input_path = this.folderPath;
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
			toCSVtoKML.readCSV(fileNameArray.get(i));
		}
		toCSVtoKML.mergeData(output_path +"RESULT");
	}
	
	public void saveToKML()
	{
		String input_path = this.folderPath;
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
			toCSVtoKML.readCSV(fileNameArray.get(i));
		}

		toCSVtoKML.createPlacemarkListFromCsvFile();
		StringWriter XML_data = toCSVtoKML.ExportPlacemarkListToXML();
		toCSVtoKML.saveToKMLFile(XML_data,output_path + "RESULT.kml");
	}
	
	public void clearData() {
		String output_path = "C:/ex0/OUT/";
		
		List<CSV_Merged_Row> mergedRowList = new ArrayList<CSV_Merged_Row>();
		toCSVtoKML.CreateCSV_Merged_File(output_path +"RESULT_Merged.csv", mergedRowList);

		StringWriter XML_data = toCSVtoKML.ExportPlacemarkListToXML();
		XML_data.getBuffer().setLength(0);
		toCSVtoKML.saveToKMLFile(XML_data,output_path + "RESULT.kml");
	}

//	public void addCSV()
//	{
//		
//	}
	
	public void submitFilter(){
		
	}
	
	
	
	public Full_Coordinate algorhthm1(String mac) {
//		String output_path = this.meregedPath;
//		String line=null;
//		
//		try {
//			FileReader fr = new FileReader(output_path);
//			BufferedReader bf = new BufferedReader(fr);
//
//			line = bf.readLine();
//
//			while(line != null) {
//				//separate all values between ","
//				String [] strs = line.split(",");
//
//				if(strs.length == 11){
//					String prefix = strs[0]+","+strs[1]+","+strs[2]+","+strs[3]+",";
//					String sufix = strs[4]+","+strs[5]+","+strs[6]+","+strs[7]+","+strs[8]+","+strs[9]+","+strs[10]+","+strs[11]+","+strs[12]+","+strs[13]+","+strs[14]+","+strs[15]+","+strs[16]+","+strs[17]+","+strs[18]+","+strs[19]+","+strs[20]+","+strs[21]+","+strs[22]+","+strs[23]+","+strs[24]+","+strs[25]+","+strs[26]+","+strs[27]+","+strs[28]+","+strs[29]+","+strs[30]+","+strs[31]+","+strs[32]+","+strs[33]+","+strs[34]+","+strs[35]+","+strs[36]+","+strs[37]+","+strs[38]+","+strs[39]+","+strs[40]+","+strs[41]+","+strs[42]+","+strs[43]+","+strs[44];
//					CSV_Merged_Row row= new CSV_Merged_Row( prefix, sufix );
//					toCSVtoKML.rowMergeList.add(row);
//				}
//				else{
//					//Do nothing: row is not valid (there are not enough columns in file )
//				}
//				line = bf.readLine();
//			}
//			fr.close();
//			bf.close();
//		}
//		catch (IOException e){
//			e.printStackTrace();
//		}
		toCSVtoKML.run();
		Full_Coordinate fc = Algo_1.algorithm_1(mac);
		return fc;
	}
	
	public Full_Coordinate algorhthm2(String mac1, int signal1, String mac2, int signal2, String mac3, int signal3) {
//		String output_path = this.meregedPath;
//		String line=null;
//		
//		try {
//			FileReader fr = new FileReader(output_path);
//			BufferedReader bf = new BufferedReader(fr);
//
//			line = bf.readLine();
//
//			while(line != null) {
//				//separate all values between ","
//				String [] strs = line.split(",");
//
//				if(strs.length == 11){
//					String prefix = strs[0]+","+strs[1]+","+strs[2]+","+strs[3]+",";
//					String sufix = strs[4]+","+strs[5]+","+strs[6]+","+strs[7]+","+strs[8]+","+strs[9]+","+strs[10]+","+strs[11]+","+strs[12]+","+strs[13]+","+strs[14]+","+strs[15]+","+strs[16]+","+strs[17]+","+strs[18]+","+strs[19]+","+strs[20]+","+strs[21]+","+strs[22]+","+strs[23]+","+strs[24]+","+strs[25]+","+strs[26]+","+strs[27]+","+strs[28]+","+strs[29]+","+strs[30]+","+strs[31]+","+strs[32]+","+strs[33]+","+strs[34]+","+strs[35]+","+strs[36]+","+strs[37]+","+strs[38]+","+strs[39]+","+strs[40]+","+strs[41]+","+strs[42]+","+strs[43]+","+strs[44];
//					CSV_Merged_Row row= new CSV_Merged_Row( prefix, sufix );
//					toCSVtoKML.rowMergeList.add(row);
//				}
//				else{
//					//Do nothing: row is not valid (there are not enough columns in file )
//				}
//				line = bf.readLine();
//			}
//			fr.close();
//			bf.close();
//		}
//		catch (IOException e){
//			e.printStackTrace();
//		}
		toCSVtoKML.run();
		Full_Coordinate fc = Algo_2.algorithm_2(mac1,signal1,mac2,signal2,mac3,signal3);
		return fc;
	}
	
	public boolean isMerged()
	{
		try {
			FileReader fr = new FileReader(this.filePath);
			BufferedReader bf = new BufferedReader(fr);

			String line = bf.readLine();
			
			fr.close();
			bf.close();
			
			if(line.length() == 324)
				return true;
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static void run(){
		Frame1.GUIrun();
	}
	
	
	public static void main(String[] args) {
		run();
	}
}


