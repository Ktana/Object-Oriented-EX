package Algorithms;

import Global.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Algo_1 {
	
	/** First Algorithm that takes 3 coordinations and returns one new coordinate that closest to them all
	 * @param mac	MAC contains 6 pairs of hexadecimal numbers and separated by ':'  
	 * @return	Full_Coordinate object
	 * @see Full_Coordinate
	 * @author Alona+Alex
	 */
	public static Full_Coordinate algorithm_1(String mac) {
		Full_Coordinate[]C = new Full_Coordinate[10];
		int indexSig = 0;
		int[]a = new int[10];
		//////////// sort By MAC: /////////////////////////////
		List<CSV_Merged_Row> rowMergeMACList = new ArrayList<CSV_Merged_Row>();
		List<CSV_Merged_Row> rowMergeList = new ArrayList<CSV_Merged_Row>();
		rowMergeList = toCSVtoKML.getRowMergeList();
		rowMergeMACList = rowMergeList.stream()
				.filter(r ->  r.compareByMAC(mac)).collect(Collectors.toList());
		String buffer = "";
		for (int i = 0; i < rowMergeMACList.size(); i++) {
			if(rowMergeMACList.get(i).compareByMAC(mac)){
				String alt = rowMergeMACList.get(i).getAlt();
				String lat = rowMergeMACList.get(i).getLat();
				String lon = rowMergeMACList.get(i).getLon();
				String sig = rowMergeMACList.get(i).getMacSignal(mac);

				a[indexSig] = Integer.parseInt(sig);
				buffer = lat+","+lon+","+alt+","+sig;
				Full_Coordinate temp = new Full_Coordinate(buffer);
				C[indexSig++] = temp; 

			}
		}
		///////SET 3 coordinates by the biggest signals///////
		Arrays.sort(a);
		
		double sig1=0, sig2=0, sig3=0;
		int i=a.length-1;
		boolean f = true;
		while(i >= 0 && f) {
			if(a[i]==0) { 
				i--;
			}
			else f=false;
		}

		if(i>=0) sig1 = a[i];
		else sig1 = -120;
		if(i-1>=0) sig2 = a[i-1];
		else sig2 = -120;
		if(i-2>=0) sig3 = a[i-2];
		else sig3 = -120;

		Full_Coordinate coo1 = new Full_Coordinate();
		Full_Coordinate coo2 = new Full_Coordinate();
		Full_Coordinate coo3 = new Full_Coordinate();
		for(int j=0; j<C.length; j++) {
			if(C[j] != null) {
				if(C[j].getSignal() == sig1)
					coo1 = C[j];
				else if(C[j].getSignal() == sig2)
					coo2 = C[j];
				else if(C[j].getSignal() == sig3)
					coo3 = C[j];
			}
		}

		/////////////////////the MAC_Algo/////////////////////
		Full_Coordinate final_coo = new Full_Coordinate();
		double signal_weight_1 = 1/(Math.pow(coo1.getSignal(),2));
		double signal_weight_2 = 1/(Math.pow(coo2.getSignal(),2));
		double signal_weight_3 = 1/(Math.pow(coo3.getSignal(),2));

		double wLat1 = signal_weight_1*coo1.getLat();
		double wLat2 = signal_weight_2*coo2.getLat();
		double wLat3 = signal_weight_3*coo3.getLat();

		double wLon1 = signal_weight_1*coo1.getLon();
		double wLon2 = signal_weight_2*coo2.getLon();
		double wLon3 = signal_weight_3*coo3.getLon();

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
		fc = algorithm_1("14:ae:db:3d:b1:52");
		System.out.println(fc);

	}

}
