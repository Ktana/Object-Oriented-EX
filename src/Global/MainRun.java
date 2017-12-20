package Global;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import GUI.*;
import KML.Placemark;

public class MainRun {
	
	String folderPath;

	public void saveFolderPath(String path){
		this.folderPath = path;
		System.out.println(folderPath);
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

	public static void run(){
		Frame1.GUIrun();
	}
	public static void main(String[] args) {
		run();
	}
}


