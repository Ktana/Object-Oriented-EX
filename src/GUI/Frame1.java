package GUI;

import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import Algorithms.Full_Coordinate;
import Global.MainRun;
import Predicate.Filter;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import javax.swing.JList;
import javax.swing.JCheckBox;

public class Frame1 {

	private JFrame frame;
	private JTextField txtSize;
	private double totalSizeInKB = 0;
	private int totalLinesCount = 0;
	private JTextField txtAltLonLat;
	private JTextField txtNumoflines;
	private JTextField txtMac_1;
	private JTextField txtMac_2;
	private JTextField txtMac_3;
	private JTextField txtSignal;
	private JTextField txtSignal_1;
	private JTextField txtSignal_2;
	private JTextField txtAltitudeLongitudeLatitude;
	private JTextField txtMaxLon;
	private JTextField txtMaxAlt;
	private JTextField txtMaxLat;
	private JTextField txtMaxTime;
	private JTextField txtMintime;
	private JTextField txtMinLat;
	private JTextField txtMinLon;
	private JTextField txtMinAlt;

	private JButton btnDir;
	private JButton btnSaveToCSV;
	private JButton btnClear;
	private JButton btnCSV;
	private JButton btnAndFilter;
	private JButton btnSubmitMac;
	private JButton btnSubmit;
	private JButton btnSaveToKml;
	private JButton btnNotFilter;
	private JRadioButton rdbtnByName;
	private JRadioButton rdbtnNotByName;
	private JButton btnOrFilter;
	private JButton btnSaveFilter;
	private JButton btnUploadFilter;

	private JLabel lblOfAp;
	private JLabel lblDataSize;
	private JLabel lblTime;
	private JLabel lblLatitude;
	private JLabel lblLongtitude;
	private JLabel lblAltitude;
	private JLabel lblNewLabel;
	private JLabel lblAlgorithms;
	private JLabel lblDevice;

	private JCheckBox chkBxTime;
	private JCheckBox chkBxLat;
	private JCheckBox chkBxLon;
	private JCheckBox chkBxAlt;
	private JCheckBox chkBxID;

	private String folderPath;
	private JFileChooser dirChooser;
	public MainRun main = new MainRun();
	private JTextField txtName;
	private JButton btnUndoFilter;
	private Full_Coordinate fc;

	private int i =0;
	private JLabel lblFirstAlgorithm;


	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		GUIrun();
	}

	public MainRun getMainRun()
	{
		return this.main;
	}

	public void GUIrun() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Frame1 window = new Frame1();
				window.frame.pack();
				window.frame.setVisible(true);
				window.frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						System.out.println("A is closing");
						if(main.getThread() != null){
							main.getThread().interrupt();
						}
						if(main.getThreadFiles() != null){
							main.getThreadFiles().interrupt();
						}
					}

					//		            @Override
					//		            public void windowClosed(WindowEvent e) {
					//		                System.out.println("A has closed");
					//		            }

				});
			}
		});


	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		addDir();
		addCSV();
		clearData();
		saveToCSV();
		saveToKML();
		filterMenu();
		firstAlgorithm();
		secondAlgorithm();
		groupLayout();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void addDir() {
		try {
			frame = new JFrame();
			frame.getContentPane().setLocation(new Point(100, 0));
			frame.getContentPane().setMaximumSize(new Dimension(10000000, 2147483647));
			frame.setBounds(20, 50, 1036, 549);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// Data Size Field
			lblDataSize = new JLabel("      Data Size:");
			lblDataSize.setFont(new Font("Tahoma", Font.BOLD, 16));

			txtSize = new JTextField();
			txtSize.setFont(new Font("Tahoma", Font.BOLD, 15));
			txtSize.setEditable(false);
			txtSize.setText("0 KB");
			txtSize.setColumns(10);

			// Number Of Lines in a CSV file
			lblOfAp = new JLabel("Number of AP:");
			lblOfAp.setFont(new Font("Tahoma", Font.BOLD, 16));

			txtNumoflines = new JTextField();
			txtNumoflines.setEditable(false);
			txtNumoflines.setFont(new Font("Tahoma", Font.BOLD, 15));
			txtNumoflines.setText("numOfLines");
			txtNumoflines.setColumns(10);

			// Directory Button
			btnDir = new JButton("Add Directory");
			btnDir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dirChooser = new JFileChooser();
					dirChooser.setFileFilter(null);
					dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					dirChooser.setCurrentDirectory(null); // null sets default
					// directory
					int returnVal = dirChooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						folderPath = dirChooser.getSelectedFile().getPath();
						main.saveFolderPath(folderPath);

						try {
							Path folder = Paths.get(dirChooser.getSelectedFile().getPath());
							Files.newDirectoryStream(folder, "*.csv")
							.forEach(s -> totalSizeInKB += (s.toFile().length()) / 1024); // fileNameArray.add(s.toString())
							Files.newDirectoryStream(folder, "*.csv").forEach(s -> {
								try {
									totalLinesCount += countLines(s.toFile().getPath());
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							});
							txtSize.setText((totalSizeInKB) + "KB");
							txtNumoflines.setText(totalLinesCount + "");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addCSV() {
		// CSV button
		btnCSV = new JButton("Add CSV File");
		btnCSV.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnCSV.setSize(new Dimension(105, 25));
		btnCSV.setMargin(new Insets(2, 5, 2, 14));

		btnCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
				chooser.setFileFilter(filter); // new FolderFilter()
				chooser.setCurrentDirectory(null); // null sets default
				// directory
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile().getName().endsWith(".csv")) {
						main.saveFilePath(chooser.getSelectedFile().getPath());
						if (!main.isMerged()) {
							double sizeInKB = (chooser.getSelectedFile().length()) / 1024;// *0.001
							// ;
							txtSize.setText(sizeInKB + "KB");
							String filename = chooser.getSelectedFile().getPath();

							try {

								txtNumoflines.setText(countLines(filename) + "");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(frame, "This is not a merged CSV table!");
							main.saveMeregedPath(null);
						} else {
							double sizeInKB = (chooser.getSelectedFile().length()) / 1024;// *0.001
							// ;
							txtSize.setText(sizeInKB + "KB");
							main.saveMeregedPath(chooser.getSelectedFile().getPath());
							// Here we should count the num of different MACs in
							// table
						}

					} else {
						JOptionPane.showMessageDialog(frame, "This is not a vaild file format");
					}
				}
			}
		});
	}

	private void clearData() {
		// Clear Data
		btnClear = new JButton("Clear Data");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "Data cleared!");
				main.clearData();
			}
		});

	}

	private void saveToCSV() {
		// Save To CSV
		btnSaveToCSV = new JButton("Save to CSV");
		btnSaveToCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (main.getFolderPath() == null) {
					JOptionPane.showMessageDialog(frame, "Choose a folder path!");
				} else {
					main.saveToCSV();
					JOptionPane.showMessageDialog(frame, "File saved to CSV!");
				}
			}
		});
	}

	private void saveToKML() {
		btnSaveToKml = new JButton("Save to KML");
		btnSaveToKml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (main.getFolderPath() == null) {
					JOptionPane.showMessageDialog(frame, "Choose a folder path!");
				} else {
					main.saveToKML();
					JOptionPane.showMessageDialog(frame, "File saved to KML!");
				}
			}
		});

	}

	private void filterMenu() {
		// Filter Menu
		lblNewLabel = new JLabel("Filter By Fields");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblTime = new JLabel("Time");
		lblTime.setFont(new Font("Tahoma", Font.BOLD, 16));

		lblLatitude = new JLabel("Latitude");
		lblLatitude.setFont(new Font("Tahoma", Font.BOLD, 16));

		lblLongtitude = new JLabel("Longtitude");
		lblLongtitude.setFont(new Font("Tahoma", Font.BOLD, 16));

		lblAltitude = new JLabel("Altitude");
		lblAltitude.setFont(new Font("Tahoma", Font.BOLD, 16));

		chkBxTime = new JCheckBox("");

		chkBxLat = new JCheckBox("");

		chkBxLon = new JCheckBox("");

		chkBxAlt = new JCheckBox("");

		chkBxID = new JCheckBox("");

		txtMaxLon = new JTextField();
		txtMaxLon.setText("maxLon");
		txtMaxLon.setColumns(10);
		txtMaxLon.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMaxLon.getText().equals("maxLon"))
					txtMaxLon.setText("");
			}
		});

		txtMaxAlt = new JTextField();
		txtMaxAlt.setText("maxAlt");
		txtMaxAlt.setColumns(10);
		txtMaxAlt.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMaxAlt.getText().equals("maxAlt"))
					txtMaxAlt.setText("");
			}
		});

		txtMaxLat = new JTextField();
		txtMaxLat.setText("maxLat");
		txtMaxLat.setColumns(10);
		txtMaxLat.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMaxLat.getText().equals("maxLat"))
					txtMaxLat.setText("");
			}
		});


		txtMaxTime = new JTextField();
		txtMaxTime.setText("maxTime");
		txtMaxTime.setColumns(10);
		txtMaxTime.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMaxTime.getText().equals("maxTime"))
					txtMaxTime.setText("");
			}
		});

		txtMintime = new JTextField();
		txtMintime.setText("minTime");
		txtMintime.setColumns(10);
		txtMintime.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMintime.getText().equals("minTime"))
					txtMintime.setText("");
			}
		});

		txtMinLat = new JTextField();
		txtMinLat.setText("minLat");
		txtMinLat.setColumns(10);
		txtMinLat.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMinLat.getText().equals("minLat"))
					txtMinLat.setText("");
			}
		});

		txtMinLon = new JTextField();
		txtMinLon.setText("minLon");
		txtMinLon.setColumns(10);
		txtMinLon.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMinLon.getText().equals("minLon"))
					txtMinLon.setText("");
			}
		});

		txtMinAlt = new JTextField();
		txtMinAlt.setText("minAlt");
		txtMinAlt.setColumns(10);
		txtMinAlt.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMinAlt.getText().equals("minAlt"))
					txtMinAlt.setText("");
			}
		});

		lblDevice = new JLabel("Device");
		lblDevice.setFont(new Font("Tahoma", Font.BOLD, 16));

		txtName = new JTextField();
		txtName.setText("name");
		txtName.setColumns(10);
		txtName.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtName.getText().equals("name"))
					txtName.setText("");
			}
		});

		rdbtnByName = new JRadioButton("Includes Name");
		rdbtnByName.setSelected(true);
		rdbtnNotByName = new JRadioButton("Not Includes Name");
		ButtonGroup deviceFilter = new ButtonGroup();
		deviceFilter.add(rdbtnByName);
		deviceFilter.add(rdbtnNotByName);

		btnUndoFilter = new JButton("Undo Filter");
		btnUndoFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFilterData();
				if (main.getFolderPath() == null) {
					JOptionPane.showMessageDialog(frame, "Choose a folder path!");
				} else {
					main.saveToCSV();
					JOptionPane.showMessageDialog(frame, "Filter cleared, File saved to CSV!");
				}
			}
		});

		// ButtonGroup deviceFilter = new ButtonGroup();
		// deviceFilter.add(rdbtnByName);
		// deviceFilter.add(rdbtnNotByName);
		/**
		 * Submit filter buttons
		 */
		btnAndFilter = new JButton("And Filter");
		btnAndFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFilter("AND");
				if (main.getFolderPath() == null) {
					JOptionPane.showMessageDialog(frame, "Choose a folder path!");
				} else {
					main.saveToCSV();
					JOptionPane.showMessageDialog(frame, "File saved to CSV!");
				}
			}
		});

		btnNotFilter = new JButton("Not Filter");
		btnNotFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFilter("NOT");
				if (main.getFolderPath() == null) {
					JOptionPane.showMessageDialog(frame, "Choose a folder path!");
				} else {
					main.saveToCSV();
					JOptionPane.showMessageDialog(frame, "File saved to CSV!");
				}


			}
		});

		btnOrFilter = new JButton("Or Filter");
		btnOrFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFilter("OR");
				if (main.getFolderPath() == null) {
					JOptionPane.showMessageDialog(frame, "Choose a folder path!");
				} else {
					main.saveToCSV();
					JOptionPane.showMessageDialog(frame, "File saved to CSV!");
				}


			}
		});

		btnSaveFilter = new JButton("Save Filter");
		btnSaveFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean TimechkBx = chkBxTime.isSelected();
				String TIME_MinVal = txtMintime.getText();
				String TIME_MaxVal = txtMaxTime.getText();
				boolean AltchkBx =  chkBxAlt.isSelected();
				String ALT_MinVal = txtMinAlt.getText();
				String ALT_MaxVal = txtMaxAlt.getText();
				boolean LATchkBx = chkBxLat.isSelected();
				String LAT_MinVal = txtMinLat.getText();
				String LAT_MaxVal = txtMaxLat.getText();
				boolean LONchkBx = chkBxLon.isSelected();
				String LON_MinVal = txtMinLon.getText();
				String LON_MaxVal = txtMaxLon.getText();
				boolean IDchkBx = chkBxID.isSelected();
				String ID_MinVal = txtName.getText();

				Filter filter = new Filter( TimechkBx, TIME_MinVal, TIME_MaxVal, AltchkBx, ALT_MinVal, ALT_MaxVal, LATchkBx, LAT_MinVal,
						LAT_MaxVal,  LONchkBx, LON_MinVal, LON_MaxVal, IDchkBx, ID_MinVal);

				String binFileName = "C:/ex0/OUT/filter"+i+".bin";
				i++;
				try{
					ObjectOutputStream filterWrite = new ObjectOutputStream(new FileOutputStream(binFileName));
					filterWrite.writeObject(filter);
					filterWrite.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(frame, "Filter saved to bin file!");

			}
		});

		btnUploadFilter = new JButton("Upload Filter");
		btnUploadFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("bin files", "bin");
				chooser.setFileFilter(filter); // new FolderFilter()
				chooser.setCurrentDirectory(null); // null sets default
				// directory
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile().getName().endsWith(".bin")) {
						double sizeInKB = (chooser.getSelectedFile().length()) / 1024;// *0.001
						txtSize.setText(sizeInKB + "KB");
						main.saveBinPath(chooser.getSelectedFile().getPath());
						txtNumoflines.setText("0");
						main.uploadBinFile();
					}
					else {
						JOptionPane.showMessageDialog(frame, "This is not a vaild file format");
					}
				} 
			}
		});
	}

	private void setFilter(String logicOperator){
		clearFilterData();
		MainRun.LogicalOperator=logicOperator;
		int i=0;
		if(chkBxTime.isSelected()){
			MainRun.PredicateType[i]="TIME";
			MainRun.MinVal[i] = txtMintime.getText();
			MainRun.MaxVal[i] = txtMaxTime.getText();
			i++;
		}

		if(chkBxAlt.isSelected()){
			MainRun.PredicateType[i]="ALT";
			MainRun.MinVal[i] = txtMinAlt.getText();
			MainRun.MaxVal[i] = txtMaxAlt.getText();
			i++;
		}

		if(chkBxLat.isSelected()){
			MainRun.PredicateType[i]="LAT";
			MainRun.MinVal[i] = txtMinLat.getText();
			MainRun.MaxVal[i] = txtMaxLat.getText();
			i++;
		}

		if(chkBxLon.isSelected()){
			MainRun.PredicateType[i]="LON";
			MainRun.MinVal[i] = txtMinLon.getText();
			MainRun.MaxVal[i] = txtMaxLon.getText();
			i++;
		}

		if(chkBxID.isSelected()){
			if(rdbtnByName.isSelected()){
				MainRun.PredicateType[i]="ID_INCLUDE";
			}
			else{
				MainRun.PredicateType[i]="ID_EXCLUDE";
			}
			MainRun.MinVal[i] = txtName.getText();
			MainRun.MaxVal[i] = "";
			i++;
		}
	}

	private void clearFilterData(){
		MainRun.LogicalOperator="CLEAR_FILTER";
		MainRun.PredicateType = new String[5];
		MainRun.MinVal= new String[5];
		MainRun.MaxVal= new String[5];
	}

	private void firstAlgorithm() {
		lblAlgorithms = new JLabel("Algorithms");
		lblAlgorithms.setFont(new Font("Tahoma", Font.PLAIN, 20));


		txtAltLonLat = new JTextField();
		txtAltLonLat.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtAltLonLat.setEditable(false);
		txtAltLonLat.setText("Altitude, Longitude, Latitude");
		txtAltLonLat.setColumns(10);

		btnSubmitMac = new JButton("Submit MAC");
		btnSubmitMac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String mac = txtMac.getText();
				if(main.getFilePath() != null ){//&& mac != null){
//					fc =
							main.algorhthm1();
//					txtAltLonLat.setText(fc.toStringGUI());
				}
				else{
					JOptionPane.showMessageDialog(frame, "Enter MAC and upload file to run Algorithm 1!");
				}
			}
		});
	}

	private void secondAlgorithm() {
		/**
		 * Second algorithm GUI implementation
		 * 
		 * @input three different mac's
		 * @output coordinate
		 */
		txtMac_1 = new JTextField();
		txtMac_1.setText("Mac1");
		txtMac_1.setColumns(10);
		txtMac_1.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMac_1.getText().equals("Mac1"))
					txtMac_1.setText("");
			}
		});

		txtMac_2 = new JTextField();
		txtMac_2.setText("Mac2");
		txtMac_2.setColumns(10);
		txtMac_2.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMac_2.getText().equals("Mac2"))
					txtMac_2.setText("");
			}
		});

		txtMac_3 = new JTextField();
		txtMac_3.setText("Mac3");
		txtMac_3.setColumns(10);
		txtMac_3.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtMac_3.getText().equals("Mac3"))
					txtMac_3.setText("");
			}
		});

		txtSignal = new JTextField();
		txtSignal.setText("Signal1");
		txtSignal.setColumns(10);
		txtSignal.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtSignal.getText().equals("Signal1"))
					txtSignal.setText("");
			}
		});

		txtSignal_1 = new JTextField();
		txtSignal_1.setText("Signal2");
		txtSignal_1.setColumns(10);
		txtSignal_1.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtSignal_1.getText().equals("Signal2"))
					txtSignal_1.setText("");
			}
		});

		txtSignal_2 = new JTextField();
		txtSignal_2.setText("Signal3");
		txtSignal_2.setColumns(10);
		txtSignal_2.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(txtSignal_2.getText().equals("Signal3"))
					txtSignal_2.setText("");
			}
		});

		txtAltitudeLongitudeLatitude = new JTextField();
		txtAltitudeLongitudeLatitude.setText("Altitude, Longitude, Latitude");
		txtAltitudeLongitudeLatitude.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtAltitudeLongitudeLatitude.setEditable(false);
		txtAltitudeLongitudeLatitude.setColumns(10);

		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mac1 = txtMac_1.getText();
				String mac2 = txtMac_2.getText();
				String mac3 = txtMac_3.getText();
				String signal1 = txtSignal.getText();
				String signal2 = txtSignal_1.getText();
				String signal3 = txtSignal_2.getText();
				if(main.getFilePath() != null && mac1 != null && !mac1.equals("Mac1") && mac2 != null && !mac2.equals("Mac2") && mac3 != null && !mac3.equals("Mac3")&& signal1 != null && !signal1.equals("Signal1") && signal2 != null && !signal2.equals("Signal2") && signal3 != null && !signal3.equals("Signal3")){
					int sig1 = Integer.parseInt(signal1);
					int sig2 = Integer.parseInt(signal2);
					int sig3 = Integer.parseInt(signal3);
					Full_Coordinate fuc = main.algorhthm2(mac1, sig1, mac2, sig2, mac3, sig3);
					txtAltitudeLongitudeLatitude.setText(fuc.toStringGUI());
				}
				else{
					JOptionPane.showMessageDialog(frame, "Enter all the data to run Algorithm 2 and upload a file!");
				}

			}
		});

	}

	private void groupLayout() {
		/**
		 * Layout of the window
		 */

		JList list = new JList();
		
		lblFirstAlgorithm = new JLabel("First Algorithm");
		lblFirstAlgorithm.setFont(new Font("Tahoma", Font.BOLD, 16));


		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(btnSaveToCSV, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnClear, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnCSV, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnDir, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
								.addComponent(btnSaveToKml, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnUndoFilter, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDataSize, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblOfAp, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(12)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
													.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
														.addComponent(txtMaxTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(txtMaxLon, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
														.addGroup(groupLayout.createSequentialGroup()
															.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(lblAltitude)
																.addComponent(lblDevice, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
															.addGap(27)
															.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
																.addComponent(txtMaxAlt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
														.addComponent(txtMaxLat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
													.addGap(18)
													.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup()
															.addComponent(txtMinLat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
															.addPreferredGap(ComponentPlacement.RELATED)
															.addComponent(chkBxLat, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
														.addGroup(groupLayout.createSequentialGroup()
															.addComponent(txtMintime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
															.addPreferredGap(ComponentPlacement.RELATED)
															.addComponent(chkBxTime, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
														.addGroup(groupLayout.createSequentialGroup()
															.addComponent(txtMinLon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
															.addPreferredGap(ComponentPlacement.RELATED)
															.addComponent(chkBxLon))
														.addComponent(rdbtnNotByName, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
														.addGroup(groupLayout.createSequentialGroup()
															.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addGroup(groupLayout.createSequentialGroup()
																	.addComponent(txtMinAlt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(ComponentPlacement.RELATED))
																.addGroup(groupLayout.createSequentialGroup()
																	.addComponent(rdbtnByName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																	.addGap(21)))
															.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(chkBxID, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
																.addComponent(chkBxAlt, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)))))
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(btnAndFilter, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(btnNotFilter, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(btnSaveFilter, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
														.addComponent(btnOrFilter, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
														.addComponent(btnUploadFilter, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))))
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
											.addComponent(lblTime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(8)
												.addComponent(lblLongtitude)))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(7)
											.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(txtNumoflines, Alignment.LEADING)
												.addComponent(txtSize, Alignment.LEADING))
											.addGap(242)))
									.addGap(79))
								.addComponent(lblLatitude, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
							.addGap(0))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
							.addGap(211)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(txtMac_1, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtMac_2, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtMac_3, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblFirstAlgorithm, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(txtSignal, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnSubmitMac, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtSignal_1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtSignal_2, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))
								.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(txtAltitudeLongitudeLatitude, Alignment.LEADING)
									.addComponent(txtAltLonLat, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(91)
							.addComponent(lblAlgorithms, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
					.addGap(743))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAlgorithms, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnDir, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnCSV, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLatitude, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtMaxLat, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSaveToCSV, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSaveToKml, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblTime)
									.addComponent(btnSubmitMac, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(8)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblFirstAlgorithm, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(chkBxTime, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(txtMintime, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtMaxTime, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
											.addGap(7)))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(15)
									.addComponent(txtAltLonLat, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addGap(22)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtMac_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
										.addComponent(txtSignal, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtMac_2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtSignal_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtMac_3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtSignal_2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(5)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(txtMinLat, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
										.addComponent(chkBxLat, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblLongtitude, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
											.addComponent(txtMinLon, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
											.addComponent(txtMaxLon, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(chkBxLon)
											.addGap(8)))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(11)
											.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblAltitude, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtMaxAlt, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtMinAlt, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(18)
											.addComponent(chkBxAlt)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblDevice, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
											.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
											.addComponent(rdbtnByName))
										.addComponent(chkBxID))
									.addGap(2)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnNotByName)
								.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnAndFilter, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnUndoFilter, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnNotFilter, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnOrFilter, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtSize, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDataSize, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnSaveFilter, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnUploadFilter, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(36)
							.addComponent(txtAltitudeLongitudeLatitude, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtNumoflines, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOfAp, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
					.addGap(32))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setResizable(false);
	}

	public int countLines(String filename) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
		String input;
		int count = 0;
		while ((input = bufferedReader.readLine()) != null)
			count++;

		bufferedReader.close();
		return count;
	}
}