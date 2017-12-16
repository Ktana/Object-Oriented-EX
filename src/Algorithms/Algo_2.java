package Algorithms;

import Global.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Algo_2 {
	
	/** Second Algorithm that takes 3 MAC's and 3 signals, then returns one coordinate that closest to them all.
	 * this coordinate represents a place on map that we can't really see.
	 * @param mac1	MAC contains 6 pairs of hexadecimal numbers and separated by ':' 
	 * @param signal1	some signal that the user choose 
	 * @param mac2	MAC contains 6 pairs of hexadecimal numbers and separated by ':' 
	 * @param signal2	some signal that the user choose
	 * @param mac3	MAC contains 6 pairs of hexadecimal numbers and separated by ':' 
	 * @param signal3	some signal that the user choose
	 * @return	Full_Coordinate object 
	 * @see Full_Coordinate
	 * @author Alona+Alex
	 */
	public static   Full_Coordinate algorithm_2(String mac1, int signal1, String mac2, int signal2, String mac3, int signal3) {
		//////////////////////////////////////////////////////////////////
		List<CSV_Merged_Row> rowMergeMACList = new ArrayList<CSV_Merged_Row>();
		List<CSV_Merged_Row> rowMergeList = toCSVtoKML.getRowMergeList();
		//System.out.println(rowMergeList.toString());
		rowMergeMACList = rowMergeList.stream().filter(r ->  r.compareByMAC(mac1)).collect(Collectors.toList());
		Full_Coordinate[]C1 = new Full_Coordinate[rowMergeMACList.size()];
		int index = 0;
		int[]s1 = new int[rowMergeMACList.size()];
		String buffer = "";
		for (int i = 0; i < rowMergeMACList.size(); i++) {
			if(rowMergeMACList.get(i).compareByMAC(mac1)){
				String alt = rowMergeMACList.get(i).getAlt();
				String lat = rowMergeMACList.get(i).getLat();
				String lon = rowMergeMACList.get(i).getLon();
				String sig = rowMergeMACList.get(i).getMacSignal(mac1);

				buffer = lat+","+lon+","+alt+","+sig;
				if(buffer.equals("")) {
					s1[index] = -120;
				}
				else s1[index] = Integer.parseInt(sig);
				Full_Coordinate temp = new Full_Coordinate(buffer);
				C1[index++] = temp; 
			}
		}
		Arrays.sort(s1);
		//////////////////////////////////////////////////////////////////
		List<CSV_Merged_Row> rowMergeMACList2 = new ArrayList<CSV_Merged_Row>();
		rowMergeMACList2 = rowMergeList.stream().filter(r ->  r.compareByMAC(mac2)).collect(Collectors.toList());
		Full_Coordinate[]C2 = new Full_Coordinate[rowMergeMACList2.size()];
		int[]s2 = new int[rowMergeMACList2.size()];
		index=0;
		buffer = "";
		for (int i = 0; i < rowMergeMACList2.size(); i++) {
			if(rowMergeMACList2.get(i).compareByMAC(mac2)){
				String alt = rowMergeMACList2.get(i).getAlt();
				String lat = rowMergeMACList2.get(i).getLat();
				String lon = rowMergeMACList2.get(i).getLon();
				String sig = rowMergeMACList2.get(i).getMacSignal(mac2);

				buffer = lat+","+lon+","+alt+","+sig;
				if(buffer.equals("")) {
					s2[index] = -120;
				}
				else s2[index] = Integer.parseInt(sig);
				Full_Coordinate temp = new Full_Coordinate(buffer);
				C2[index++] = temp; 
			}
		}
		index=0;
		Arrays.sort(s2);		
		//////////////////////////////////////////////////////////////////
		List<CSV_Merged_Row> rowMergeMACList3 = new ArrayList<CSV_Merged_Row>();
		rowMergeMACList3 = rowMergeList.stream().filter(r ->  r.compareByMAC(mac3)).collect(Collectors.toList());
		Full_Coordinate[]C3 = new Full_Coordinate[rowMergeMACList3.size()];
		int[]s3 = new int[rowMergeMACList3.size()];
		index=0;
		buffer = "";
		for (int i = 0; i < rowMergeMACList3.size(); i++) {
			if(rowMergeMACList3.get(i).compareByMAC(mac3)){
				String alt = rowMergeMACList3.get(i).getAlt();
				String lat = rowMergeMACList3.get(i).getLat();
				String lon = rowMergeMACList3.get(i).getLon();
				String sig = rowMergeMACList3.get(i).getMacSignal(mac3);

				buffer = lat+","+lon+","+alt+","+sig;
				if(buffer.equals("")) {
					s3[index] = -120;
				}
				else s3[index] = Integer.parseInt(sig);
				Full_Coordinate temp = new Full_Coordinate(buffer);
				C3[index++] = temp;
			}
		}
		index=0;
		Arrays.sort(s3);
		//////////////////////////////////////////////////////////////////
		int close1_s1 = close_signal(signal1, s1);
		int close1_s2 = close_signal(signal2, s2);
		int close1_s3 = close_signal(signal3, s3);

		int j = 0, close3_s1, close2_s2, close2_s1;
		boolean f = true;
		while(j<s1.length && f) { 
			if(s1[j]!=0) j++;
			else f = false;
		}
		if(j>=s1.length)
			close2_s1 = s1[s1.length-1];
		else
			close2_s1 = s1[j];
		if(j>=s1.length)
			 close3_s1 = s1[s1.length-1];
		else
			 close3_s1 = s1[j+1];
		int close3_s2;
		j = 0;
		f = true;
		while(j<s3.length && f) {
			if(s1[j]!=0) j++;
			else f = false;
		}
		if(j>=s2.length)
			close2_s2 = s2[s2.length-1];
		else
			close2_s2 = s2[j];
		if(j>=s2.length)
			close3_s2 = s2[s2.length-1];
		else
			close3_s2 = s2[j+1];
		int close2_s3, close3_s3;
		j = 0;
		f = true;
		while(j<s3.length && f) {
			if(s1[j]!=0) j++;
			else f = false;
		}
		if(j>=s3.length)
			close2_s3 = s3[s3.length-1];
		else
			close2_s3 = s3[j];
		if(j>=s3.length)
			close3_s3 = s3[s3.length-1];
		else
			close3_s3 = s3[j+1];
		
		double pi1 = pi(signal1,close1_s1,signal2,close1_s2,signal3,close1_s3);
		double pi2 = pi(signal1,close2_s1,signal2,close2_s2,signal3,close2_s3);
		double pi3 = pi(signal1,close3_s1,signal2,close3_s2,signal3,close3_s3);	
		Full_Coordinate coo1 = Algo_1.algorithm_1(mac1);
		Full_Coordinate coo2 = Algo_1.algorithm_1(mac2);
		Full_Coordinate coo3 = Algo_1.algorithm_1(mac3);

		Full_Coordinate final_coo = new Full_Coordinate();
		final_coo.setSignal(pi1 + pi2 + pi3);
		final_coo.setAlt((coo1.getAlt()*pi1 + coo2.getAlt()*pi2 + coo3.getAlt()*pi3)/final_coo.getSignal());
		final_coo.setLat((coo1.getLat()*pi1 + coo2.getLat()*pi2 + coo3.getLat()*pi3)/final_coo.getSignal());
		final_coo.setLon((coo1.getLon()*pi1 + coo2.getLon()*pi2 + coo3.getLon()*pi3)/final_coo.getSignal());
		return final_coo;
	}
	

	private static   double pi(int signal1,int close_s1,int signal2,int close_s2,int signal3,int close_s3 ) {
		int df1, df2, df3;
		if(close_s1== -120) df1 = 80;
		else df1 = MAX(Math.abs(signal1)-Math.abs(close_s1), 3);
		if(close_s2== -120) df2 = 80;
		else df2 = MAX(Math.abs(signal2)-Math.abs(close_s2), 3);
		if(close_s3== -120) df3 = 80;
		else df3 = MAX(Math.abs(signal3)-Math.abs(close_s3), 3);
		double w1 = 10000/(Math.pow(df1,0.4)*Math.pow(signal1,2));
		double w2 = 10000/(Math.pow(df2,0.4)*Math.pow(signal2,2));
		double w3 = 10000/(Math.pow(df3,0.4)*Math.pow(signal3,2));
		return w1*w2*w3;
	}

	/** private method that return the closest signal to ours. 
	 * @param signal that was given
	 * @param a	array of signals
	 * @return integer that is the closest signal.
	 */
	private static   int close_signal(int signal, int[]a) {
		//Suppose that the array is sorted
		int i=0, tempA=0, tempB=0;
		boolean b = true;
		while(i<a.length && b) {
			if(signal>=a[i]) {
				i++;		
			}
			else b = false;
		}
		if(i == a.length){
			tempA = Math.abs(a[i-1]) - Math.abs(signal);
			return a[i-1];
		}
		else{
			tempA = Math.abs(a[i]) - Math.abs(signal);
			if(a[i+1]!= 0) {
				tempB = Math.abs(signal) - Math.abs(a[i+1]);
				if (tempA<tempB) return a[i];
				else return a[i+1];
		}

		}
		int ans = a[i];
		a[i] = 0;
		Arrays.sort(a);
		return ans;
	}
	
	/** biggest number between two numbers
	 * @param a
	 * @param b
	 * @return MAX(a,b)
	 */
	private static   int MAX(int a, int b) {
		if(a>=b) return a;
		return b;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		toCSVtoKML.run();
		Full_Coordinate fc2 = new Full_Coordinate();
		fc2 = algorithm_2("14:ae:db:3d:b1:52",-90,"cc:b2:55:e8:4c:e6",-54, "30:e1:71:c5:17:eb",-76);
		System.out.println(fc2);

	}

}
