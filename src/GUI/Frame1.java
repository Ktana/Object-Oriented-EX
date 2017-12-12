package GUI;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Frame1 {

	private JFrame frame;
	private JTextField txtSize;
	private double totalSizeInKB=0;
	private int totalLinesCount=0;
	private JTextField txtMac;
	private JTextField txtAltLonLat;
	private JTextField txtNumoflines;
	private JTextField txtMac_1;
	private JTextField txtMac_2;
	private JTextField txtMac_3;
	private JTextField txtSignal;
	private JTextField txtSignal_1;
	private JTextField txtSignal_2;
	private JTextField txtAltitudeLongitudeLatitude;
	private JTextField txtMaxlat;
	private JTextField txtMaxalt;
	private JTextField textField;
	private JTextField maxTime;
	private JTextField txtMintime;
	private JTextField txtMinalt;
	private JTextField txtMinlat;
	private JTextField txtMinalt_1;
	//private JRadioButton rdbtnAlt;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				Frame1 window = new Frame1();
				window.frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		addDir();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void addDir() {
		try {
			frame = new JFrame();
			frame.getContentPane().setLocation(new Point(100, 0));
			frame.getContentPane().setMaximumSize(new Dimension(10000000, 2147483647));
			frame.setBounds(20, 50, 950, 550);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Data Size Field 
			JLabel lblDataSize = new JLabel("      Data Size:");
			lblDataSize.setFont(new Font("Tahoma", Font.BOLD, 16));
			
			txtSize = new JTextField();
			txtSize.setFont(new Font("Tahoma", Font.BOLD, 15));
			txtSize.setEditable(false);
			txtSize.setText("0 KB");
			txtSize.setColumns(10);

			//Number Of Lines in a CSV file
			JLabel lblOfAp = new JLabel("Number of AP:");
			lblOfAp.setFont(new Font("Tahoma", Font.BOLD, 16));
			
			txtNumoflines = new JTextField();
			txtNumoflines.setEditable(false);
			txtNumoflines.setFont(new Font("Tahoma", Font.BOLD, 15));
			txtNumoflines.setText("numOfLines");
			txtNumoflines.setColumns(10);

			//Directory Button

			JButton btnDir = new JButton("Add Directory");
			btnDir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileFilter(null ); 
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.setCurrentDirectory(null); //null sets default directory 
					int returnVal = chooser.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						System.out.println("You chose to open this folder: " +
								chooser.getSelectedFile().getPath());
						
						try {
							Path folder = Paths.get(chooser.getSelectedFile().getPath());
							Files.newDirectoryStream(folder ,"*.csv").forEach(s -> totalSizeInKB+=(s.toFile().length())/1024);	//fileNameArray.add(s.toString())
							Files.newDirectoryStream(folder, "*.csv").forEach(s -> {
								try {
									totalLinesCount+=countLines(s.toFile().getPath());
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							});
							txtSize.setText((totalSizeInKB)+"KB");
							txtNumoflines.setText(totalLinesCount+"");
						}
						catch(IOException e1) {
							e1.printStackTrace();	
						}
					}
				}
			});


			//CSV button

			JButton btnCSV = new JButton("Add CSV File");
			btnCSV.setAlignmentY(Component.BOTTOM_ALIGNMENT);
			btnCSV.setSize(new Dimension(105, 25));
			btnCSV.setMargin(new Insets(2, 5, 2, 14));

			btnCSV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							"CSV files", "csv");
					chooser.setFileFilter(filter); //new FolderFilter()
					chooser.setCurrentDirectory(null); //null sets default directory 
					int returnVal = chooser.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION)
					{
						if(chooser.getSelectedFile().getName().endsWith(".csv")) {
							System.out.println("You choose this file: " +
									chooser.getSelectedFile().getName());
							double sizeInKB = (chooser.getSelectedFile().length())/1024;//*0.001 ;
							txtSize.setText(sizeInKB+"KB");
							
							String filename = chooser.getSelectedFile().getPath();
							
								try {
									
									txtNumoflines.setText(countLines(filename)+"");
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							
						}
						else{
							JOptionPane.showMessageDialog(frame, "This is not a vaild file format");
						}
					}
				}
			});
			
			
			//Clear Data
			
			JButton btnClear = new JButton("Clear Data");
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(frame, "Data cleared!");
				}
			});
			
			
			//Save To CSV
			JButton btnSaveToCSV = new JButton("Save to CSV");
			btnSaveToCSV.addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent e) { 
					 JOptionPane.showMessageDialog(frame, "File saved to mereged CSV!");
				 }
			 });
			
			//Filter Menu
			JLabel lblNewLabel = new JLabel("Filter By Fields");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
			
			JLabel lblNewLabel_1 = new JLabel("Time");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
			
			JLabel lblLatitude = new JLabel("Latitude");
			lblLatitude.setFont(new Font("Tahoma", Font.BOLD, 16));
			
			JLabel lblLongtitude = new JLabel("Longtitude");
			lblLongtitude.setFont(new Font("Tahoma", Font.BOLD, 16));
			
			JLabel lblAltitude = new JLabel("Altitude");
			lblAltitude.setFont(new Font("Tahoma", Font.BOLD, 16));
			
			JRadioButton rdbtnTime = new JRadioButton("");
			
			JRadioButton rdbtnLat = new JRadioButton("");
			
			JRadioButton rdbtnLon = new JRadioButton("");
			
			JRadioButton rdbtnAlt = new JRadioButton("");
			
			ButtonGroup filterBtns = new ButtonGroup();
			filterBtns.add(rdbtnTime);
			filterBtns.add(rdbtnLon);
			filterBtns.add(rdbtnLat);
			filterBtns.add(rdbtnAlt);
			
			
			/**
			 * Submit filter button
			 */
			 JButton btnSubmitFilter = new JButton("Submit Filter");
			 btnSubmitFilter.addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent e) { 
					 filterBtns.clearSelection();
				 }
			 });
			
			JLabel lblAlgorithms = new JLabel("Algorithms");
			lblAlgorithms.setFont(new Font("Tahoma", Font.PLAIN, 20));
			
			/**
			 * First algorithm GUI implementation 
			 * @input mac 
			 * @output coordinate 
			 */
			txtMac = new JTextField();
			txtMac.setText("MAC");
			txtMac.setColumns(10);
			txtMac.addFocusListener(new FocusAdapter() {
			    public void focusGained(FocusEvent e) { 
			    	txtMac= (JTextField)e.getComponent();
			    	txtMac.setText("");
			    	txtMac.removeFocusListener(this);
			    }
			});
			
			JButton btnSubmitMac = new JButton("Submit MAC");
			
			txtAltLonLat = new JTextField();
			txtAltLonLat.setFont(new Font("Tahoma", Font.BOLD, 15));
			txtAltLonLat.setEditable(false);
			txtAltLonLat.setText("Altitude, Longitude, Latitude, Accuracy");
			txtAltLonLat.setColumns(10);
			
			/**
			 * Second algorithm GUI implementation 
			 * @input three different mac's 
			 * @output coordinate 
			 */
			txtMac_1 = new JTextField();
			txtMac_1.setText("Mac1");
			txtMac_1.setColumns(10);
			
			txtMac_2 = new JTextField();
			txtMac_2.setText("Mac2");
			txtMac_2.setColumns(10);
			
			txtMac_3 = new JTextField();
			txtMac_3.setText("Mac3");
			txtMac_3.setColumns(10);
			
			txtSignal = new JTextField();
			txtSignal.setText("Signal1");
			txtSignal.setColumns(10);
			
			txtSignal_1 = new JTextField();
			txtSignal_1.setText("Signal2");
			txtSignal_1.setColumns(10);
			
			txtSignal_2 = new JTextField();
			txtSignal_2.setText("Signal3");
			txtSignal_2.setColumns(10);
			
			JButton btnSubmit = new JButton("Submit");
			
			txtAltitudeLongitudeLatitude = new JTextField();
			txtAltitudeLongitudeLatitude.setText("Altitude, Longitude, Latitude, Accuracy");
			txtAltitudeLongitudeLatitude.setFont(new Font("Tahoma", Font.BOLD, 15));
			txtAltitudeLongitudeLatitude.setEditable(false);
			txtAltitudeLongitudeLatitude.setColumns(10);
			
			txtMaxlat = new JTextField();
			txtMaxlat.setText("maxLat");
			txtMaxlat.setColumns(10);
			
			txtMaxalt = new JTextField();
			txtMaxalt.setText("maxAlt");
			txtMaxalt.setColumns(10);
			
			textField = new JTextField();
			textField.setText("maxAlt");
			textField.setColumns(10);
			
			maxTime = new JTextField();
			maxTime.setText("maxTime");
			maxTime.setColumns(10);
			
			txtMintime = new JTextField();
			txtMintime.setText("minTime");
			txtMintime.setColumns(10);
			
			txtMinalt = new JTextField();
			txtMinalt.setText("minAlt");
			txtMinalt.setColumns(10);
			
			txtMinlat = new JTextField();
			txtMinlat.setText("minLat");
			txtMinlat.setColumns(10);
			
			txtMinalt_1 = new JTextField();
			txtMinalt_1.setText("minAlt");
			txtMinalt_1.setColumns(10);
			
//			JRadioButton rdbtnTime = new JRadioButton("");
//			
//			JRadioButton rdbtnLat = new JRadioButton("");
//			
//			JRadioButton rdbtnLon = new JRadioButton("");
//			
//			JRadioButton rdbtnAlt = new JRadioButton("");
//			
//			ButtonGroup filterBtns = new ButtonGroup();
			
			//Number Of Lines in a CSV file
//			JLabel lblOfAp = new JLabel("Number of AP:");
//			lblOfAp.setFont(new Font("Tahoma", Font.BOLD, 16));
//			
//			txtNumoflines = new JTextField();
//			txtNumoflines.setEditable(false);
//			txtNumoflines.setFont(new Font("Tahoma", Font.BOLD, 15));
//			txtNumoflines.setText("numOfLines");
//			txtNumoflines.setColumns(10);
			
//			//Data Size Field 
//			JLabel lblDataSize = new JLabel("      Data Size:");
//			lblDataSize.setFont(new Font("Tahoma", Font.BOLD, 16));
//			
//			textField = new JTextField();
//			textField.setEditable(false);
//			textField.setText("0 KB");
//			textField.setColumns(10);
			
			
			
			/**
			 * Layout of the window 
			 */
			GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(12)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(40)
										.addComponent(lblOfAp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(btnSaveToCSV, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnClear, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnCSV, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnDir, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
											.addComponent(lblDataSize, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel_1)
											.addComponent(lblLatitude, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(7)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
											.addGroup(groupLayout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
													.addComponent(txtNumoflines, Alignment.LEADING)
													.addComponent(txtSize, Alignment.LEADING)))
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(btnSubmitFilter, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createSequentialGroup()
													.addGap(1)
													.addComponent(lblLongtitude)
													.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(txtMaxlat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(groupLayout.createSequentialGroup()
													.addGap(5)
													.addComponent(lblAltitude)
													.addGap(27)
													.addComponent(txtMaxalt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addComponent(textField, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(maxTime, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
								.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(txtMintime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(rdbtnTime))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(txtMinalt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(rdbtnLat, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(txtMinlat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(rdbtnLon, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(txtMinalt_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(rdbtnAlt, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
								.addGap(3))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
								.addGap(50)))
						.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblAlgorithms, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
								.addGap(128))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(19)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(txtMac_2, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(txtSignal_1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(txtMac_1, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(txtSignal, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
											.addComponent(txtAltLonLat)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(txtMac, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnSubmitMac, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(txtMac_3, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(txtSignal_2, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(118)
										.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap())
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(txtAltitudeLongitudeLatitude, GroupLayout.PREFERRED_SIZE, 346, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblAlgorithms, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(btnDir, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnCSV, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnSaveToCSV, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnSubmitMac, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNewLabel_1)
												.addComponent(txtMac, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(8)
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
													.addComponent(rdbtnTime)
													.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(txtMintime, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
														.addComponent(maxTime, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(3)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
													.addGroup(groupLayout.createSequentialGroup()
														.addGap(15)
														.addComponent(txtAltLonLat, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
															.addComponent(txtSignal)
															.addComponent(txtMac_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
															.addGroup(groupLayout.createSequentialGroup()
																.addComponent(txtMac_2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																	.addComponent(txtMac_3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
																	.addComponent(txtSignal_2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
																	.addComponent(btnSubmitFilter, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
															.addComponent(txtSignal_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
													.addGroup(groupLayout.createSequentialGroup()
														.addGap(43)
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
															.addComponent(lblLongtitude, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
															.addComponent(txtMaxlat, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
															.addComponent(txtMinlat, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
															.addGroup(groupLayout.createSequentialGroup()
																.addGap(11)
																.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																	.addComponent(lblAltitude, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
																	.addComponent(txtMaxalt, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
															.addGroup(groupLayout.createSequentialGroup()
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																	.addComponent(txtMinalt_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
																	.addComponent(rdbtnAlt)))))))
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(51)
												.addComponent(rdbtnLon)))))
								.addGap(20)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtSize, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblDataSize, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblOfAp, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtNumoflines, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtAltitudeLongitudeLatitude, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
								.addGap(144))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblLatitude, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtMinalt, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addComponent(rdbtnLat))
								.addGap(399)))
						.addGap(0, 0, Short.MAX_VALUE))
			);
			frame.getContentPane().setLayout(groupLayout);



		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int countLines(String filename) throws IOException
	{
		 BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	     String input;
	     int count = 0;
	     while((input = bufferedReader.readLine()) != null)
	         count++;
	     
	     bufferedReader.close();
	     return count;
	}
}
