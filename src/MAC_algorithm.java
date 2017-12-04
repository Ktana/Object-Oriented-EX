import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MAC_algorithm {
	private String path;
	public MAC_algorithm(String path) {this.path = path;}
	public MAC_algorithm(MAC_algorithm ma) {this.path = ma.path;}
	
	
	/** First Algorithm that takes 3 coordinations and returns one new coordinate that closest to them all
	 * @param file_path	need to be a path on your computer this is a String.
	 * @param mac	MAC contains 6 pairs of hexadecimal numbers and separated by ':'  
	 * @return	Full_Coordinate object
	 * @see Full_Coordinate
	 * @author Alona+Alex
	 */
	public static Full_Coordinate algorithm_1(String file_path, String mac) {
		//here needs to be a line that SORT by MAC:
		//________________________________________//
		
		Full_Coordinate coo1 = new Full_Coordinate();
		Full_Coordinate coo2 = new Full_Coordinate();
		Full_Coordinate coo3 = new Full_Coordinate();

		///////SET 3 coordinates by the biggest signals///////
		String line = null;
		try {
			FileReader fr = new FileReader(file_path);
			BufferedReader bf = new BufferedReader(fr);
			line = bf.readLine();
			line = bf.readLine();
			Scanner sc = new Scanner(line).useDelimiter(",");
			coo1.setLat(sc.nextDouble());
			coo1.setLon(sc.nextDouble());
			coo1.setAlt(sc.nextDouble());
			coo1.setSignal(sc.nextInt());
			line = bf.readLine();
			sc = new Scanner(line).useDelimiter(",");
			coo2.setLat(sc.nextDouble());
			coo2.setLon(sc.nextDouble());
			coo2.setAlt(sc.nextDouble());
			coo2.setSignal(sc.nextInt());
			line = bf.readLine();
			sc = new Scanner(line).useDelimiter(",");
			coo3.setLat(sc.nextDouble());
			coo3.setLon(sc.nextDouble());
			coo3.setAlt(sc.nextDouble());
			coo3.setSignal(sc.nextInt());
		}
		catch (IOException e){e.printStackTrace();}
		//////////////////////////////////////////////////////
		
		Full_Coordinate final_coo = new Full_Coordinate();
		/////////////////////the MAC_Algo/////////////////////
		double signal_weight_1 = 1/(Math.pow(coo1.getSignal(),2));
		double signal_weight_2 = 1/(Math.pow(coo2.getSignal(),2));
		double signal_weight_3 = 1/(Math.pow(coo3.getSignal(),2));
		
		double wLat1 = signal_weight_1*coo1.getLat();
		double wLat2 = signal_weight_2*coo2.getLat();
		double wLat3 = signal_weight_3*coo3.getLat();
		
		double wLon1 = signal_weight_1*coo1.getLon();
		double wLon2 = signal_weight_2*coo2.getLon();
		double wLon3 = signal_weight_2*coo2.getLon();
		
		double wAlt1 = signal_weight_1*coo1.getAlt();
		double wAlt2 = signal_weight_2*coo2.getAlt();
		double wAlt3 = signal_weight_3*coo3.getAlt();

		final_coo.setSignal(signal_weight_1+signal_weight_2+signal_weight_3);
		final_coo.setLat((wLat1+wLat2+wLat3)/final_coo.getSignal());
		final_coo.setLon((wLon1+wLon2+wLon3)/final_coo.getSignal());
		final_coo.setAlt((wAlt1+wAlt2+wAlt3)/final_coo.getSignal());
		//////////////////////////////////////////////////////
		return final_coo;
	}


	public static void main(String[] args) {
		Full_Coordinate fc = new Full_Coordinate();
		fc = algorithm_1("C:/ex0/MAC_Merge_Regal.csv","90:a7:c1:0c:4d:14");
		System.out.println(fc);
	}

}
