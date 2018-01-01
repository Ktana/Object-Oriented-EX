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

public class FileChecker {

	private List<FileInfo> fileInfoList;
	private String folder;
	private List<String> fileNameArray = new ArrayList<String>();
	private String errorMsg = "";
		
	public FileChecker(String folderPath){
		this.fileInfoList = new ArrayList<FileInfo>();
		this.folder = folderPath;
		fileNameArray = new ArrayList<String>();
		Path folder = Paths.get(this.folder);
		try {
			Files.newDirectoryStream(folder,"*.csv").forEach(s -> fileNameArray.add(s.toString()));	
		}
		catch(IOException e) {
			e.printStackTrace();	
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
			/////////////////////
//			Frame1 folderPath = new Frame1();
			MainRun main = new MainRun();
			main.saveFolderPath(this.folder);
			main.saveToCSV();
			main.saveToKML();
			////////////////////
		}
	}

	public int existsInFileInfo(String file)
	{
		for (int i = 0; i < fileInfoList.size(); i++) {
			if(fileInfoList.get(i).getFileName().equals(file))
				return i;
		}
		return -1;
	}

}
