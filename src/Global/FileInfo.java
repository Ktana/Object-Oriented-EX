package Global;

import java.nio.file.attribute.FileTime;
import java.util.Date;
/**
 * A class that holds a file's size, name and time modified.
 * @author Alex Fishman
 *
 */
public class FileInfo {

	private double fileSize;
	private String fileName;
	private FileTime time;
	
	public FileInfo(){
		this.fileSize = 0;
		this.fileName = "";
		this.time = null; 
	}
	
	/**
	 * Constructor 
	 * @param fileSize
	 * @param fileName
	 * @param fileTime
	 */
	public FileInfo(double fileSize, String fileName, FileTime fileTime){
		this.fileSize = fileSize;
		this.fileName = fileName;
		this.time = fileTime;
	}
	
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public FileTime getTime() {
		return time;
	}
	public void setTime(FileTime time) {
		this.time = time;
	}
	
	
	
}
