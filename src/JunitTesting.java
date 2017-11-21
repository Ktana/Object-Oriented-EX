import junit.framework.TestCase;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class JunitTesting extends TestCase {
	
	@Test
	public void test_readCSV()
	{
		//JunitTesting test = new JunitTesting();
		String input_path = "C:/ex0";
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
		assertTrue("Row list is empy!", toCSVtoKML.rowList.size()>0);	
	}
	
	@Test
	public void test_readCSV_RequiredFields()
	{
		//JunitTesting test = new JunitTesting();
		String input_path = "C:/ex0";
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
		for (int i = 0; i < toCSVtoKML.rowList.size(); i++) {
			assertTrue("Time is empty in row "+i, !(toCSVtoKML.rowList.get(i).getFirstSeen().isEmpty()));
			assertTrue("Latitude is empty in row "+i, !(toCSVtoKML.rowList.get(i).getCurrentLatitude().isEmpty()));
			assertTrue("Altitude is empty in row "+i, !(toCSVtoKML.rowList.get(i).getAltitudeMeters().isEmpty()));
			assertTrue("Longtitude is empty in row "+i, !(toCSVtoKML.rowList.get(i).getCurrentLongitude().isEmpty()));
			assertTrue("Header Row: Model field is empty in row "+i, !(toCSVtoKML.rowList.get(i).getHeaderRow().getmodel().isEmpty()));
			assertTrue("Header Row: Brand field is empty in row "+i, !(toCSVtoKML.rowList.get(i).getHeaderRow().getbrand().isEmpty()));
			assertTrue("Header Row: Board field is empty in row "+i, !(toCSVtoKML.rowList.get(i).getHeaderRow().getboard().isEmpty()));
			assertTrue("Header Row: Device field is empty in row "+i, !(toCSVtoKML.rowList.get(i).getHeaderRow().getdevice().isEmpty()));
			assertTrue("Header Row: WIFI field is empty in row "+i, !(toCSVtoKML.rowList.get(i).getHeaderRow().getwifi().isEmpty()));
			assertTrue("Header Row: AppRelease field is empty in row "+i, !(toCSVtoKML.rowList.get(i).getHeaderRow().getappRelease().isEmpty()));
			assertTrue("Header Row: Release field is empty in row "+i, !(toCSVtoKML.rowList.get(i).getHeaderRow().getrelease().isEmpty()));
			assertTrue("Header Row: Display field is empty in row "+i, !(toCSVtoKML.rowList.get(i).getHeaderRow().getdisplay().isEmpty()));
			
		}

	}
	
	@Test
	public void test_Mereged_RequiredFields()
	{
		//JunitTesting test = new JunitTesting();
		String input_path = "C:/ex0";
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
		for (int i = 0; i < toCSVtoKML.rowMergeList.size(); i++) {
			assertTrue("Merged Table: Time is empty in row "+i, !(toCSVtoKML.rowMergeList.get(i).getTime().isEmpty()));
			assertTrue("Merged Table: ID is empty in row "+i, !(toCSVtoKML.rowMergeList.get(i).getID().isEmpty()));
			assertTrue("Merged Table: Latitude is empty in row "+i, !(toCSVtoKML.rowMergeList.get(i).getLat().isEmpty()));
			assertTrue("Merged Table: Longtitude is empty in row "+i, !(toCSVtoKML.rowMergeList.get(i).getLon().isEmpty()));
			assertTrue("Merged Table: Latitude is empty in row "+i, !(toCSVtoKML.rowMergeList.get(i).getLat().isEmpty()));
		}
	}

	
	@Test
	public void test_writeToCSV()
	{
		String input_path = "C:/ex0";
		String output_path = "C:/ex0/OUT/";
		String original_file_path = "C:/ex0/Test/RESULT_Merged_Correct.csv";
		String new_file_path = "C:\\ex0\\OUT\\RESULT_Merged.csv"; 
				//toCSVtoKML.getFName(output_path +"RESULT") + "_Merged.csv";

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
		toCSVtoKML.mergeData(new_file_path);
		
		byte[] fileORIGBytes, fileNEWBytes;
		try {
			fileORIGBytes = Files.readAllBytes(Paths.get(original_file_path));
			fileNEWBytes = Files.readAllBytes(Paths.get(new_file_path));
			
			String file1 = new String(fileORIGBytes, StandardCharsets.UTF_8);
			String file2 = new String(fileNEWBytes, StandardCharsets.UTF_8);
			assertEquals("The content in the strings should match", file1, file2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void test_writeToCSV_byRows()
	{
		String input_path = "C:/ex0";
		String output_path = "C:/ex0/OUT/";
		String original_file_path = "C:/ex0/Test/RESULT_Merged_Correct.csv";
		String new_file_path = "C:\\ex0\\OUT\\RESULT_Merged.csv";
				//toCSVtoKML.getFName(output_path +"RESULT") + "_Merged.csv";

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
		toCSVtoKML.mergeData(new_file_path);
		
		byte[] fileORIGBytes, fileNEWBytes;
		try {
			List<String> file1 = Files.readAllLines(Paths.get(original_file_path));
			List<String> file2 = Files.readAllLines(Paths.get(new_file_path));

			assertEquals(file1.size(), file2.size());

			for(int i = 0; i < file1.size(); i++) {
			   //System.out.println("Comparing line: " + i);
			   assertEquals(file1.get(i), file2.get(i));
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void test_writeToKML()
	{
		String input_path = "C:/ex0";
		String output_path = "C:/ex0/OUT/";
		String original_file_path = "C:/ex0/Test/RESULT_Merged_Correct.kml";
		String new_file_path = "C:\\ex0\\OUT\\RESULT.kml";

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
		
		byte[] fileORIGBytes, fileNEWBytes;
		try {
			fileORIGBytes = Files.readAllBytes(Paths.get(original_file_path));
			fileNEWBytes = Files.readAllBytes(Paths.get(new_file_path));
			
			String file1 = new String(fileORIGBytes, StandardCharsets.UTF_8);
			String file2 = new String(fileNEWBytes, StandardCharsets.UTF_8);
			assertEquals("The content in the strings should match", file1, file2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void test_writeToKML_byRows()
	{
		String input_path = "C:/ex0";
		String output_path = "C:/ex0/OUT/";
		String original_file_path = "C:\\ex0\\OUT\\RESULT_Merged.csv.kml";
		String new_file_path = "C:\\ex0\\OUT\\RESULT.kml";

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
		
		
		byte[] fileORIGBytes, fileNEWBytes;
		try {
			List<String> file1 = Files.readAllLines(Paths.get(original_file_path));
			List<String> file2 = Files.readAllLines(Paths.get(new_file_path));

			assertEquals(file1.size(), file2.size());

			for(int i = 0; i < file1.size(); i++) {
			   //System.out.println("Comparing line: " + i);
			   assertEquals(file1.get(i), file2.get(i));
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
