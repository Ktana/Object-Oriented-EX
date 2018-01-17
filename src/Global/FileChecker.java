package Global;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import GUI.Frame1;

/**
 * The class behaves like a watcher after the file/s of my "database".
 * It detect's the changes that were made and updates accordingly. 
 * @author Alex Fishman
 *
 */
public class FileChecker {
	

	private List<FileInfo> fileInfoList;
	private String folder;
	private List<String> fileNameArray = new ArrayList<String>();
	private String errorMsg = "";
	
	/**
	 * The function get's the folder path and start's the watch after each of it's files.
	 * If there are no CSV's to watch after in the chosen folder - it will print a console msg.
	 * @param folderPath
	 */
	public FileChecker(String folderPath){
		this.fileInfoList = new ArrayList<FileInfo>();
		this.folder = folderPath;
		fileNameArray = new ArrayList<String>();
		Path folder = Paths.get(this.folder);
		try {
			Files.newDirectoryStream(folder,"*.csv").forEach(s -> fileNameArray.add(s.toString()));	
		}
		catch(IOException e) {
			//e.printStackTrace();	
			System.out.println("No CSVs here!!!!");
		}


		try {
			for (int i = 0; i < fileNameArray.size(); i++) {
				File file = new File(fileNameArray.get(i));
				Path filePath = file.toPath();
				BasicFileAttributes attr;
				attr = Files.readAttributes(filePath, BasicFileAttributes.class);
				FileInfo newFile = new FileInfo(file.length(), file.toString(), attr.lastModifiedTime());
				fileInfoList.add(newFile);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
/**
 * The flag here is a detection that we want to watch over a single given CSV file.
 * @param folderPath
 * @param fileFlag
 */
	public FileChecker(String folderPath, String fileFlag){
		
		this.fileInfoList = new ArrayList<FileInfo>();
		this.folder = folderPath;
		
		fileNameArray = new ArrayList<String>();
		if (!fileFlag.equals("Y"))
		{			
			Path folder = Paths.get(this.folder);
			try {
				Files.newDirectoryStream(folder,"*.csv").forEach(s -> fileNameArray.add(s.toString()));	
			}
			catch(IOException e) {
				//e.printStackTrace();	
				System.out.println("No CSVs here!!!!");
			}
		}
		else
		{
			fileNameArray.add(folderPath);
		}
		
		try {
			for (int i = 0; i < fileNameArray.size(); i++) {
				File file = new File(fileNameArray.get(i));
				Path filePath = file.toPath();
				BasicFileAttributes attr;
				attr = Files.readAttributes(filePath, BasicFileAttributes.class);
				FileInfo newFile = new FileInfo(file.length(), file.toString(), attr.lastModifiedTime());
				fileInfoList.add(newFile);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * The worker is the one that when change is detected updates the file's created from the database
	 * that changed accordingly.
	 */
	public void workerProsses(){
		
		fileNameArray = new ArrayList<String>();
		Path folder = Paths.get(this.folder);
		try {
			Files.newDirectoryStream(folder,"*.csv").forEach(s -> fileNameArray.add(s.toString()));	
		}
		catch(IOException e1) {
			//e.printStackTrace();
			System.out.println(e1.getMessage());
		}
		
		File file = null;
		Path filePath;
		BasicFileAttributes attr = null;
		for (int i = 0; i < fileNameArray.size(); i++) {
			int index = existsInFileInfo(fileNameArray.get(i));
			try {
				file = new File(fileNameArray.get(i));
				filePath = file.toPath();
				attr = Files.readAttributes(filePath, BasicFileAttributes.class);
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("File : "+e.getMessage()+" does not exist!");
				fileNameArray = new ArrayList<String>();
				folder = Paths.get(this.folder);
				try {
					Files.newDirectoryStream(folder,"*.csv").forEach(s -> fileNameArray.add(s.toString()));	
				}
				catch(IOException e1) {
					//e.printStackTrace();
					System.out.println(e1.getMessage());
				}
				break;
			}
			if(index < 0){
				FileInfo newFile = new FileInfo(file.length(), file.toString(), attr.lastModifiedTime());
				fileInfoList.add(newFile);
				errorMsg += " File added do stuff.....";
			}
			else{
				if(file != null){		
					FileInfo existFile = fileInfoList.get(index);
					if(!existFile.getFileName().equals(file.toString())){
						errorMsg += " File name changed Do stuff.......";
						existFile.setFileName(file.toString());
					}
					if(existFile.getFileSize() != file.length()){
						errorMsg += " File size changed Do stuff.......";
						existFile.setFileSize(file.length());
					}
					if(attr != null && existFile.getTime().compareTo(attr.lastModifiedTime()) != 0){
						errorMsg += " File time changed Do stuff.......";
						existFile.setTime(attr.lastModifiedTime());
					}
				}
			}
		}

		int k=-1;
		int [] indxArrayToRemove = new int [fileInfoList.size()];
		for (int i = 0; i < fileInfoList.size(); i++) {
			boolean fileFound = false;
			for (int j = 0; j < fileNameArray.size(); j++) {
				if(fileInfoList.get(i).getFileName().equals(fileNameArray.get(j))){
					fileFound = true;
				} 
			}
			if(!fileFound){
				indxArrayToRemove[++k]=i;
			}
		}
		for (int i = k; i > -1; i--) {
			if(i == k){
				errorMsg += " File deleted Do stuff.....";
			}
			fileInfoList.remove(indxArrayToRemove[i]);
		}

		if(!errorMsg.equals("")){
			System.out.println(errorMsg);
			errorMsg ="";
			MainRun main = new MainRun();
			main.saveFolderPath(this.folder);
			main.saveToCSV();
			main.saveToKML();
		}
	}

	public void workerFileProsses(){
		File file = null;
		Path filePath;
		BasicFileAttributes attr = null;
		int index = existsInFileInfo(folder);
			try {
				file = new File(folder);
				filePath = file.toPath();
				attr = Files.readAttributes(filePath, BasicFileAttributes.class);
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("File : "+e.getMessage()+" does not exist!");
				return;
			}
			
			if(index < 0){
				FileInfo newFile = new FileInfo(file.length(), file.toString(), attr.lastModifiedTime());
				fileInfoList.add(newFile);
				errorMsg += " File added do stuff.....";
			}
			else{
				if(file != null){		
					FileInfo existFile = fileInfoList.get(index);
					if(!existFile.getFileName().equals(file.toString())){
						errorMsg += " File name changed Do stuff.......";
						existFile.setFileName(file.toString());
					}
					if(existFile.getFileSize() != file.length()){
						errorMsg += " File size changed Do stuff.......";
						existFile.setFileSize(file.length());
					}
					if(attr != null && existFile.getTime().compareTo(attr.lastModifiedTime()) != 0){
						errorMsg += " File time changed Do stuff.......";
						existFile.setTime(attr.lastModifiedTime());
					}
				}
			}
		
		int k=-1;
		int [] indxArrayToRemove = new int [1];
		for (int i = 0; i < fileInfoList.size(); i++) {
			boolean fileFound = false;
			for (int j = 0; j < fileNameArray.size(); j++) {
				if(fileInfoList.get(i).getFileName().equals(fileNameArray.get(j))){
					fileFound = true;
				} 
			}
			if(!fileFound){
				indxArrayToRemove[++k]=i;
			}
		}
		for (int i = k; i > -1; i--) {
			if(i == k){
				errorMsg += " File deleted Do stuff.....";
			}
			fileInfoList.remove(indxArrayToRemove[i]);
		}

		if(!errorMsg.equals("")){
			System.out.println(errorMsg);
			errorMsg ="";
			MainRun main = new MainRun();
			main.saveMeregedPath(filePath.toString());
			main.algorhthm1();
		}
	}

	/**
	 * Check if the file we got is already being watched or if it's a new file.
	 * @param file
	 * @return if the file exist's it's index in the array list will be returned, else -1.
	 */
	public int existsInFileInfo(String file)
	{
		for (int i = 0; i < fileInfoList.size(); i++) {
			if(fileInfoList.get(i).getFileName().equals(file))
				return i;
		}
		return -1;
	}
}
