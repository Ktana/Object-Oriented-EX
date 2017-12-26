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
		toCSVtoKML.run();
		Full_Coordinate fc = Algo_1.algorithm_1(mac);
		return fc;
	}
	
	public boolean isMerged()
	{
		try {
			FileReader fr = new FileReader(this.filePath);
			BufferedReader bf = new BufferedReader(fr);

			String line = bf.readLine();
			List<String> firstLine = new ArrayList<String>(Arrays.asList(line.split(" , ")));
			
			fr.close();
			bf.close();
			
			if(firstLine.size() == 45)
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


