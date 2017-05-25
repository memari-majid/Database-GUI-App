package model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class search {
	private Connection con;
	private PreparedStatement ps;
	private static int test;

	public search() throws Exception {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "hr");

	}

	public JTextField tfosngID = null;
	public JTextField tfAuthor, tfTitle, tfAlbumID, tfspeed, tfsearch;
	public DefaultTableModel defaultTableModel;
	public JButton badd, bremove, bsearch;
	public JTable jTable;
	public JLabel lSONGID, lAUTHOR, lTITLE, lALBUMIDENTIFIER, lSPEED;
	public JPanel jPanel;
	public ResultSet rows;
	public static java.util.Date dateBirthDate, sqlBirthDate;

	public JPanel updateperform() throws Exception {

		Object databaseresult[][] = {};
		Object columns[] = { "SONGID", "SSN" };
		defaultTableModel = new DefaultTableModel(databaseresult, columns) {
			public Class getclasscolumn(int column) {
				Class returnvalue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnvalue = getValueAt(0, column).getClass();
				} else {
					returnvalue = Object.class;

				}

				return returnvalue;
			}

		};

		rows = null;
		Object[] temprow;

		String selectstuff = "select SONGID ,SSN   FROM perform ";

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rows = stmt.executeQuery(selectstuff);

			while (rows.next()) {
				temprow = new Object[] { rows.getInt(1), rows.getString(2) };
				defaultTableModel.addRow(temprow);
				System.out.println("initial rows= " + temprow[0]);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		jTable = new JTable(defaultTableModel);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);
		
		bsearch = new JButton("serach");
		bsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] temprow;
				String searchvalue = tfsearch.getText();
				System.out.println("searchvalue=" + searchvalue);

				String selectstuff = "select SONGID ,SSN  FROM places where   SSN = '" + searchvalue + "' or SONGID = '"
						+ searchvalue + "' ";

				if (defaultTableModel.getRowCount() > 0) {
					for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
						defaultTableModel.removeRow(i);
					}
				}

				try {
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rows = stmt.executeQuery(selectstuff);

					while (rows.next()) {
						temprow = new Object[] { rows.getString(1), rows.getString(2) };
						defaultTableModel.addRow(temprow);
						System.out.println("initial rows= " + temprow[0]);

					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			}
		});

		/////////////////////
		badd = new JButton("add");
		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String SONGID = "null", SSN = "null";
				SONGID = tfosngID.getText();
				SSN = tfAuthor.getText();
				
				try {
					rows.moveToInsertRow();
					
					
					rows.updateString("SONGID", SONGID);
					rows.updateString("SSN", SSN);
					

					try {
						rows.insertRow();
						Object[] intrument = { SONGID, SSN };
						defaultTableModel.addRow(intrument);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select  SONGID, SSN  from  PERFORM ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getInt(1), rows.getString(2) };
								defaultTableModel.addRow(temprow);
								System.out.println("rowcount in add=" + defaultTableModel.getRowCount());
								System.out.println("rows in add=" + temprow[0]);
							}
							

						} catch (SQLException eee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							eee.printStackTrace();
						}
					}

					rows.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}

			}
		});

		bremove = new JButton("remove ");
		bremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				test = jTable.getSelectedRow();
				System.out.println("absolootee avvalie== " + test);
				defaultTableModel.removeRow(test);

				try {
					System.out.println("absolootee== " + test);
					rows.absolute(test + 1);
					rows.deleteRow();

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});

		

		tfosngID = new JTextField("songid ", 15);
		tfAuthor = new JTextField("ssn", 15);
		

		
		tfsearch = new JTextField("search", 15);
		jPanel = new JPanel();

		
		jPanel.add(tfosngID);


		jPanel.add(tfAuthor);

		

		jPanel.add(badd);
		jPanel.add(bremove);

		jPanel.add(bsearch);
		jPanel.add(tfsearch);

		jPanel.add(jScrollPane);
		

		jTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent me) {
				if (me.getClickCount() == 2) {
					System.out.println("raft tu if double click");
					System.out.println("row= " + jTable.getSelectedRow());
					System.out.println("col= " + jTable.getSelectedColumn());
					String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");

					if (value != null) {
						System.out.println("raft tu if e value");
						jTable.setValueAt(value, jTable.getSelectedRow(), jTable.getSelectedColumn());

					}

					try {
						System.out.println("absolute==" + (jTable.getSelectedRow() + 1));
						rows.absolute(jTable.getSelectedRow() + 1);
						String updatecolumn = defaultTableModel.getColumnName(jTable.getSelectedColumn());

						
						 */ rows.updateString(updatecolumn, value);
						System.out.println("Current Row: " + rows.getRow());
						try {
							rows.updateRow();
							// break;
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						

						try {
							rows.updateRow();
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("tu update rowcount avval=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select  SONGID, ssn  FROM perform  ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getInt(1), rows.getString(2) }
								defaultTableModel.addRow(temprow);
								System.out.println("ezafe shod tu update=" + temprow);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
						// }

					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					System.out.println("raft tu else tu update");
				}
			}
		});

		return jPanel;
	}

	public JPanel updateplaces() throws Exception {

		Object databaseresult[][] = {};
		Object columns[] = { "ADDRESS" };
		defaultTableModel = new DefaultTableModel(databaseresult, columns) {
			public Class getclasscolumn(int column) {
				Class returnvalue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnvalue = getValueAt(0, column).getClass();
				} else {
					returnvalue = Object.class;

				}

				return returnvalue;
			}

		};

		rows = null;
		Object[] temprow;

		String selectstuff = "select ADDRESS  FROM places ";

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rows = stmt.executeQuery(selectstuff);

			while (rows.next()) {
				temprow = new Object[] { rows.getString(1) };
				defaultTableModel.addRow(temprow);
				System.out.println("initial rows= " + temprow[0]);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		jTable = new JTable(defaultTableModel);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);

		bsearch = new JButton("serach");
		bsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] temprow;
				String searchvalue = tfsearch.getText();
				System.out.println("searchvalue=" + searchvalue);

				String selectstuff = "select ADDRESS  FROM places where   ADDRESS = '" + searchvalue + "' ";

				if (defaultTableModel.getRowCount() > 0) {
					for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
						defaultTableModel.removeRow(i);
					}
				}

				try {
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rows = stmt.executeQuery(selectstuff);

					while (rows.next()) {
						temprow = new Object[] { rows.getString(1) };
						defaultTableModel.addRow(temprow);
						System.out.println("initial rows= " + temprow[0]);

					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			}
		});

		/////////////////////
		badd = new JButton("add");
		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String ADDRESS = "null";
				ADDRESS = tfTitle.getText();

				try {
					rows.moveToInsertRow();

					rows.updateString("ADDRESS  ", ADDRESS);

					try {
						rows.insertRow();
						Object[] intrument = { ADDRESS };
						defaultTableModel.addRow(intrument);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select  ADDRESS  from  places ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1) };
								defaultTableModel.addRow(temprow);
								System.out.println("rowcount in add=" + defaultTableModel.getRowCount());
								System.out.println("rows in add=" + temprow[0]);
							}

						} catch (SQLException eee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							eee.printStackTrace();
						}
					}

					rows.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}

			}
		});

		bremove = new JButton("remove ");
		bremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				test = jTable.getSelectedRow();
				System.out.println("absolootee avvalie== " + test);
				defaultTableModel.removeRow(test);

				try {
					System.out.println("absolootee== " + test);
					rows.absolute(test + 1);
					rows.deleteRow();

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});

		tfTitle = new JTextField("address", 15);

		tfsearch = new JTextField("search", 15);
		jPanel = new JPanel();

		//
		jPanel.add(tfTitle);

		jPanel.add(badd);
		jPanel.add(bremove);

		jPanel.add(bsearch);
		jPanel.add(tfsearch);

		jPanel.add(jScrollPane);

		jTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent me) {
				if (me.getClickCount() == 2) {
					System.out.println("raft tu if double click");
					String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");

					if (value != null) {
						System.out.println("raft tu if e value");
						jTable.setValueAt(value, jTable.getSelectedRow(), jTable.getSelectedColumn());

					}

					try {
						System.out.println("absolute==" + jTable.getSelectedRow() + 1);
						rows.absolute(jTable.getSelectedRow() + 1);
						String updatecolumn = defaultTableModel.getColumnName(jTable.getSelectedColumn());

						if (updatecolumn.equals("COPYRIGHTDATE")) {

							sqlBirthDate = getADate(value);
							rows.updateDate(updatecolumn, (Date) sqlBirthDate);

							rows.updateRow();

						}

						else {
							rows.updateString(updatecolumn, value);
							System.out.println("Current Row: " + rows.getRow());

							rows.updateRow();

						}

						try {
							rows.updateRow();
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("tu update rowcount avval=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select  ADDRESS  FROM places ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1) };
								defaultTableModel.addRow(temprow);
								System.out.println("ezafe shod tu update=" + temprow);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
						// }

					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					System.out.println("raft tu else tu update");
				}
			}
		});

		return jPanel;
	}

	public JPanel updatelives() throws Exception {

		Object databaseresult[][] = {};
		Object columns[] = { "SSN", "PHONE", "ADDRESS" };
		defaultTableModel = new DefaultTableModel(databaseresult, columns) {
			public Class getclasscolumn(int column) {
				Class returnvalue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnvalue = getValueAt(0, column).getClass();
				} else {
					returnvalue = Object.class;

				}

				return returnvalue;
			}

		};

		rows = null;
		Object[] temprow;

		String selectstuff = "select SSN,PHONE, ADDRESS  FROM lives ";

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rows = stmt.executeQuery(selectstuff);

			while (rows.next()) {
				temprow = new Object[] { rows.getString(1), rows.getString(2), rows.getString(3) };
				defaultTableModel.addRow(temprow);
				System.out.println("initial rows= " + temprow[0]);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		jTable = new JTable(defaultTableModel);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);

		bsearch = new JButton("serach");
		bsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] temprow;
				String searchvalue = tfsearch.getText();
				System.out.println("searchvalue=" + searchvalue);

				String selectstuff = "select SSN,PHONE, ADDRESS  FROM lives where  SSN = '" + searchvalue
						+ "' or PHONE = '" + searchvalue + "' or ADDRESS = '" + searchvalue + "' ";
				if (defaultTableModel.getRowCount() > 0) {
					for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
						defaultTableModel.removeRow(i);
					}
				}

				try {
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rows = stmt.executeQuery(selectstuff);

					while (rows.next()) {
						temprow = new Object[] { rows.getString(1), rows.getString(2), rows.getString(3) };
						defaultTableModel.addRow(temprow);
						System.out.println("initial rows= " + temprow[0]);

					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			}
		});

		/////////////////////
		badd = new JButton("add");
		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String SSN = "null", PHONE = "null", ADDRESS = "null";
				SSN = tfosngID.getText();
				PHONE = tfAuthor.getText();

				ADDRESS = tfTitle.getText();

				try {
					rows.moveToInsertRow();

					rows.updateString("SSN ", SSN);
					rows.updateString("PHONE ", PHONE);
					rows.updateString("ADDRESS  ", ADDRESS);

					try {
						rows.insertRow();
						Object[] intrument = { SSN, PHONE, ADDRESS };
						defaultTableModel.addRow(intrument);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select SSN,PHONE, ADDRESS  from  lives ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2), rows.getString(3) };
								defaultTableModel.addRow(temprow);
								System.out.println("rowcount in add=" + defaultTableModel.getRowCount());
								System.out.println("rows in add=" + temprow[0]);
							}

						} catch (SQLException eee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							eee.printStackTrace();
						}
					}

					rows.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}

			}
		});

		bremove = new JButton("remove ");
		bremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				test = jTable.getSelectedRow();
				System.out.println("absolootee avvalie== " + test);
				defaultTableModel.removeRow(test);

				try {
					System.out.println("absolootee== " + test);
					rows.absolute(test + 1);
					rows.deleteRow();

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});

		tfosngID = new JTextField("ssn ", 15);
		tfAuthor = new JTextField("phone", 15);

		tfTitle = new JTextField("address", 15);

		tfsearch = new JTextField("search", 15);
		jPanel = new JPanel();

		jPanel.add(tfosngID);

		jPanel.add(tfAuthor);

		jPanel.add(tfTitle);

		jPanel.add(badd);
		jPanel.add(bremove);

		jPanel.add(bsearch);
		jPanel.add(tfsearch);

		jPanel.add(jScrollPane);

		jTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent me) {
				if (me.getClickCount() == 2) {
					System.out.println("raft tu if double click");
					String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");

					if (value != null) {
						System.out.println("raft tu if e value");
						jTable.setValueAt(value, jTable.getSelectedRow(), jTable.getSelectedColumn());

					}

					try {
						System.out.println("absolute==" + jTable.getSelectedRow() + 1);
						rows.absolute(jTable.getSelectedRow() + 1);
						String updatecolumn = defaultTableModel.getColumnName(jTable.getSelectedColumn());

						if (updatecolumn.equals("COPYRIGHTDATE")) {

							sqlBirthDate = getADate(value);
							rows.updateDate(updatecolumn, (Date) sqlBirthDate);

							rows.updateRow();

						}

						else {
							rows.updateString(updatecolumn, value);
							System.out.println("Current Row: " + rows.getRow());

							rows.updateRow();

						}

						try {
							rows.updateRow();
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("tu update rowcount avval=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select SSN , PHONE , ADDRESS  FROM lives ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2), rows.getString(3) };
								defaultTableModel.addRow(temprow);
								System.out.println("ezafe shod tu update=" + temprow);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
						// }

					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					System.out.println("raft tu else tu update");
				}
			}
		});

		return jPanel;
	}

	public JPanel updateTelephone_Home() throws Exception {

		Object databaseresult[][] = {};
		Object columns[] = { "PHONE", "ADDRESS" };
		defaultTableModel = new DefaultTableModel(databaseresult, columns) {
			public Class getclasscolumn(int column) {
				Class returnvalue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnvalue = getValueAt(0, column).getClass();
				} else {
					returnvalue = Object.class;

				}

				return returnvalue;
			}

		};

		rows = null;
		Object[] temprow;

		String selectstuff = "select PHONE,ADDRESS  FROM Telephone_Home ";

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rows = stmt.executeQuery(selectstuff);

			while (rows.next()) {
				temprow = new Object[] { rows.getString(1), rows.getString(2) };
				defaultTableModel.addRow(temprow);
				System.out.println("initial rows= " + temprow[0]);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		jTable = new JTable(defaultTableModel);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);

		bsearch = new JButton("serach");
		bsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] temprow;
				String searchvalue = tfsearch.getText();
				System.out.println("searchvalue=" + searchvalue);

				String selectstuff = "select PHONE,ADDRESS  FROM Telephone_Home where phone ='" + searchvalue
						+ "' or address = '" + searchvalue + "' ";
				if (defaultTableModel.getRowCount() > 0) {
					for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
						defaultTableModel.removeRow(i);
					}
				}

				try {
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rows = stmt.executeQuery(selectstuff);

					while (rows.next()) {
						temprow = new Object[] { rows.getString(1), rows.getString(2) };
						defaultTableModel.addRow(temprow);
						System.out.println("initial rows= " + temprow[0]);

					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			}
		});

		badd = new JButton("add");
		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String PHONE = "null", ADDRESS = "null";
				PHONE = tfosngID.getText();
				ADDRESS = tfAuthor.getText();

				try {
					rows.moveToInsertRow();

					rows.updateString("PHONE", PHONE);
					rows.updateString("ADDRESS", ADDRESS);

					try {
						rows.insertRow();
						Object[] intrument = { PHONE, ADDRESS };
						defaultTableModel.addRow(intrument);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select PHONE, ADDRESS  from  Telephone_Home ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2) };
								defaultTableModel.addRow(temprow);
								System.out.println("rowcount in add=" + defaultTableModel.getRowCount());
								System.out.println("rows in add=" + temprow[0]);
							}

						} catch (SQLException eee) {
							eee.printStackTrace();
						}
					}

					rows.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}

			}
		});

		bremove = new JButton("remove ");
		bremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				test = jTable.getSelectedRow();
				System.out.println("absolootee avvalie== " + test);
				defaultTableModel.removeRow(test);

				try {
					System.out.println("absolootee== " + test);
					rows.absolute(test + 1);
					rows.deleteRow();

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});

		tfosngID = new JTextField("PHONE ", 15);
		tfAuthor = new JTextField("ADDRESS", 15);

		tfsearch = new JTextField("search", 15);
		jPanel = new JPanel();

		jPanel.add(tfosngID);

		jPanel.add(tfAuthor);

		jPanel.add(badd);
		jPanel.add(bremove);

		jPanel.add(bsearch);
		jPanel.add(tfsearch);

		jPanel.add(jScrollPane);

		jTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent me) {
				if (me.getClickCount() == 2) {
					System.out.println("raft tu if double click");
					String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");

					if (value != null) {
						System.out.println("raft tu if e value");
						jTable.setValueAt(value, jTable.getSelectedRow(), jTable.getSelectedColumn());

					}

					try {
						System.out.println("absolute==" + jTable.getSelectedRow() + 1);
						rows.absolute(jTable.getSelectedRow() + 1);
						String updatecolumn = defaultTableModel.getColumnName(jTable.getSelectedColumn());

						if (updatecolumn.equals("COPYRIGHTDATE")) {

							sqlBirthDate = getADate(value);
							rows.updateDate(updatecolumn, (Date) sqlBirthDate);

							rows.updateRow();

						}

						else {
							rows.updateString(updatecolumn, value);
							System.out.println("Current Row: " + rows.getRow());

							rows.updateRow();

						}

						try {
							rows.updateRow();
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("tu update rowcount avval=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select PHONE , ADDRESS  FROM Telephone_Home ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2) };
								defaultTableModel.addRow(temprow);
								System.out.println("ezafe shod tu update=" + temprow);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
						// }

					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					System.out.println("raft tu else tu update");
				}
			}
		});

		return jPanel;
	}

	public JPanel updateplays() throws Exception {

		Object databaseresult[][] = {};
		Object columns[] = { "SSN", "INSTRID" };
		defaultTableModel = new DefaultTableModel(databaseresult, columns) {
			public Class getclasscolumn(int column) {
				Class returnvalue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnvalue = getValueAt(0, column).getClass();
				} else {
					returnvalue = Object.class;

				}

				return returnvalue;
			}

		};

		rows = null;
		Object[] temprow;

		String selectstuff = "select SSN,INSTRID  FROM plays ";

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rows = stmt.executeQuery(selectstuff);

			while (rows.next()) {
				temprow = new Object[] { rows.getString(1), rows.getString(2) };
				defaultTableModel.addRow(temprow);
				System.out.println("initial rows= " + temprow[0]);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		jTable = new JTable(defaultTableModel);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);

		bsearch = new JButton("serach");
		bsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] temprow;
				String searchvalue = tfsearch.getText();
				System.out.println("searchvalue=" + searchvalue);

				String selectstuff = "select SSN,INSTRID  from  PLAYS where SSN = '" + searchvalue + "'  or INSTRID = '"
						+ searchvalue + "' ";

				if (defaultTableModel.getRowCount() > 0) {
					for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
						defaultTableModel.removeRow(i);
					}
				}

				try {
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rows = stmt.executeQuery(selectstuff);

					while (rows.next()) {
						temprow = new Object[] { rows.getString(1), rows.getString(2) };
						defaultTableModel.addRow(temprow);
						System.out.println("initial rows= " + temprow[0]);

					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			}
		});

		badd = new JButton("add");
		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String INSTRID = "null", SSN = "null";
				INSTRID = tfosngID.getText();
				SSN = tfAuthor.getText();

				try {
					rows.moveToInsertRow();

					rows.updateString("SSN", SSN);
					rows.updateString("INSTRID", INSTRID);

					try {
						rows.insertRow();
						Object[] intrument = { INSTRID, SSN };
						defaultTableModel.addRow(intrument);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select SSN, INSTRID  from  PLAYS ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2) };
								defaultTableModel.addRow(temprow);
								System.out.println("rowcount in add=" + defaultTableModel.getRowCount());
								System.out.println("rows in add=" + temprow[0]);
							}

						} catch (SQLException eee) {
							eee.printStackTrace();
						}
					}

					rows.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}

			}
		});

		bremove = new JButton("remove ");
		bremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				test = jTable.getSelectedRow();
				System.out.println("absolootee avvalie== " + test);
				defaultTableModel.removeRow(test);

				try {
					System.out.println("absolootee== " + test);
					rows.absolute(test + 1);
					rows.deleteRow();

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});

		tfosngID = new JTextField("ssn", 15);
		tfAuthor = new JTextField("INSTRID", 15);

		tfsearch = new JTextField("search", 15);

		jPanel = new JPanel();

		jPanel.add(tfosngID);

		jPanel.add(tfAuthor);

		jPanel.add(badd);
		jPanel.add(bremove);

		jPanel.add(bsearch);
		jPanel.add(tfsearch);

		jPanel.add(jScrollPane);

		jTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent me) {
				if (me.getClickCount() == 2) {
					System.out.println("raft tu if double click");
					String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");

					if (value != null) {
						System.out.println("raft tu if e value");
						jTable.setValueAt(value, jTable.getSelectedRow(), jTable.getSelectedColumn());

					}

					try {
						System.out.println("absolute==" + jTable.getSelectedRow() + 1);
						rows.absolute(jTable.getSelectedRow() + 1);
						String updatecolumn = defaultTableModel.getColumnName(jTable.getSelectedColumn());

						if (updatecolumn.equals("COPYRIGHTDATE")) {

							sqlBirthDate = getADate(value);
							rows.updateDate(updatecolumn, (Date) sqlBirthDate);

							rows.updateRow();

						}

						else {
							rows.updateString(updatecolumn, value);
							System.out.println("Current Row: " + rows.getRow());

							rows.updateRow();

						}

						try {
							rows.updateRow();
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("tu update rowcount avval=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select SSN , INSTRID   from  PLAYS ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2) };
								defaultTableModel.addRow(temprow);
								System.out.println("ezafe shod tu update=" + temprow);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}

					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					System.out.println("raft tu else tu update");
				}
			}
		});

		return jPanel;
	}

	public JPanel updateAlbum() throws Exception {
		

		Object databaseresult[][] = {};
		Object columns[] = { "ALBUMIDENTIFIER", "SSN", "COPYRIGHTDATE", "SPEED", "TITLE" };
		defaultTableModel = new DefaultTableModel(databaseresult, columns) {
			public Class getclasscolumn(int column) {
				Class returnvalue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnvalue = getValueAt(0, column).getClass();
				} else {
					returnvalue = Object.class;

				}

				return returnvalue;
			}

		};

		rows = null;
		Object[] temprow;

		String selectstuff = "select ALBUMIDENTIFIER,SSN,COPYRIGHTDATE,SPEED,TITLE  from  Album_Producer ";

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rows = stmt.executeQuery(selectstuff);

			while (rows.next()) {
				temprow = new Object[] { rows.getInt(1), rows.getString(2), rows.getDate(3), rows.getInt(4),
						rows.getString(5) };
				defaultTableModel.addRow(temprow);
				System.out.println("initial rows= " + temprow[0]);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		jTable = new JTable(defaultTableModel);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);

		
		bsearch = new JButton("serach");
		bsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] temprow;
				String searchvalue = tfsearch.getText();
				System.out.println("searchvalue=" + searchvalue);

				String selectstuff = "select ALBUMIDENTIFIER,SSN,COPYRIGHTDATE,SPEED,TITLE  from  Album_Producer where ALBUMIDENTIFIER = "
						+ searchvalue + "  or TITLE = '" + searchvalue + "' or SSN = '" + searchvalue + "' or SPEED = "
						+ searchvalue + " ";

				if (defaultTableModel.getRowCount() > 0) {
					for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
						defaultTableModel.removeRow(i);
					}
				}

				try {
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rows = stmt.executeQuery(selectstuff);

					while (rows.next()) {
						temprow = new Object[] { rows.getInt(1), rows.getString(2), rows.getDate(3), rows.getInt(4),
								rows.getString(5) };
						defaultTableModel.addRow(temprow);
						System.out.println("initial rows= " + temprow[0]);

					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			}
		});

		
		badd = new JButton("add");
		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String albumid = "null", ssn = "null", copyright = "null", speed = "null", title = "null";
				albumid = tfosngID.getText();
				ssn = tfAuthor.getText();
				copyright = tfAlbumID.getText();
				title = tfTitle.getText();
				speed = tfspeed.getText();

				sqlBirthDate = getADate(copyright);
				

				if (albumid.equals("")) {
					System.out.println("raft toooooooooooooo");
					albumid = "'null'";
				}
				System.out.println(albumid);
				System.out.println(ssn);
				System.out.println(copyright);
				System.out.println(speed);
				System.out.println(title);
				try {
					rows.moveToInsertRow();
				
					rows.updateString("SSN", ssn);
					rows.updateString("ALBUMIDENTIFIER", albumid);

					rows.updateDate("COPYRIGHTDATE", (Date) sqlBirthDate);

					rows.updateString("SPEED", speed);
					rows.updateString("TITLE", title);
					try {
						rows.insertRow();
						Object[] intrument = { albumid, ssn, sqlBirthDate, speed, title };
						defaultTableModel.addRow(intrument);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select ALBUMIDENTIFIER , SSN , COPYRIGHTDATE , SPEED , TITLE  from  Album_Producer ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getInt(1), rows.getString(2), rows.getDate(3),
										rows.getInt(4), rows.getString(5) };/
								defaultTableModel.addRow(temprow);
								System.out.println("rowcount in add=" + defaultTableModel.getRowCount());
								System.out.println("rows in add=" + temprow[0]);
							}
						

						} catch (SQLException eee) {
							eee.printStackTrace();
						}
					}

					rows.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}

			}
		});

		bremove = new JButton("remove ");
		bremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				test = jTable.getSelectedRow();
				System.out.println("absolootee avvalie== " + test);
				defaultTableModel.removeRow(test);

				try {
					System.out.println("absolootee== " + test);
					rows.absolute(test + 1);
					rows.deleteRow();

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});

		
		tfosngID = new JTextField("albumID", 15);
		tfAuthor = new JTextField("ssn", 15);
		tfAlbumID = new JTextField("yyyy-MM-dd", 15);

		tfTitle = new JTextField("title", 15);
		tfspeed = new JTextField("speed", 15);
		tfsearch = new JTextField("search", 15);
		jPanel = new JPanel();

		jPanel.add(tfosngID);

		jPanel.add(tfAlbumID);

		jPanel.add(tfAuthor);

		
		jPanel.add(tfTitle);

	
		jPanel.add(tfspeed);

		jPanel.add(badd);
		jPanel.add(bremove);

		jPanel.add(bsearch);
		jPanel.add(tfsearch);

		jPanel.add(jScrollPane);
	

		jTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent me) {
				if (me.getClickCount() == 2) {
					System.out.println("raft tu if double click");
					String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");

					if (value != null) {
						System.out.println("raft tu if e value");
						jTable.setValueAt(value, jTable.getSelectedRow(), jTable.getSelectedColumn());

					}

					try {
						System.out.println("absolute==" + jTable.getSelectedRow() + 1);
						rows.absolute(jTable.getSelectedRow() + 1);
						String updatecolumn = defaultTableModel.getColumnName(jTable.getSelectedColumn());

						if (updatecolumn.equals("COPYRIGHTDATE")) {

							
							sqlBirthDate = getADate(value);
							rows.updateDate(updatecolumn, (Date) sqlBirthDate);

							rows.updateRow();
					
						}
						
						else {
							rows.updateString(updatecolumn, value);
							System.out.println("Current Row: " + rows.getRow());

							rows.updateRow();
					
						}
					

						try {
							rows.updateRow();
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("tu update rowcount avval=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select ALBUMIDENTIFIER , SSN , COPYRIGHTDATE , SPEED , TITLE  from  Album_Producer ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getInt(1), rows.getString(2), rows.getDate(3),
										rows.getInt(4), rows.getString(5) };
								defaultTableModel.addRow(temprow);
								System.out.println("ezafe shod tu update=" + temprow);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
					

					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					System.out.println("raft tu else tu update");
				}
			}
		});

		return jPanel;
	}

	public JPanel updateinstruments() throws Exception {

		Object databaseresult[][] = {};
		Object columns[] = { "INSTRID", "DNAME", "KEY" };
		defaultTableModel = new DefaultTableModel(databaseresult, columns) {
			public Class getclasscolumn(int column) {
				Class returnvalue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnvalue = getValueAt(0, column).getClass();
				} else {
					returnvalue = Object.class;

				}

				return returnvalue;
			}

		};

		rows = null;
		Object[] temprow;

		String selectstuff = "select INSTRID,DNAME,KEY from  Instruments ";

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rows = stmt.executeQuery(selectstuff);

			while (rows.next()) {
				temprow = new Object[] { rows.getString(1), rows.getString(2), rows.getString(3) };
				defaultTableModel.addRow(temprow);
				System.out.println("initial rows= " + temprow[0]);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		jTable = new JTable(defaultTableModel);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);

	
		badd = new JButton("add");
		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String instrid = "null", dname = "null", key = "null";
				instrid = tfosngID.getText();
				dname = tfAuthor.getText();
				
				key = tfAlbumID.getText();

				if (instrid.equals("")) {
					System.out.println("raft toooooooooooooo");
					instrid = "'null'";
				}
				System.out.println(instrid);
				System.out.println(dname);
				System.out.println(key);
			
				try {
					rows.moveToInsertRow();
					
					rows.updateString("INSTRID", instrid);
					rows.updateString("DNAME", dname);
					rows.updateString("KEY", key);
				
					try {
						rows.insertRow();
						Object[] intrument = { instrid, dname, key };
						defaultTableModel.addRow(intrument);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select INSTRID,DNAME,KEY from  Instruments ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2), rows.getInt(3) };/
								defaultTableModel.addRow(temprow);
								System.out.println("rowcount in add=" + defaultTableModel.getRowCount());
								System.out.println("rows in add=" + temprow[0]);
							}
							

						} catch (SQLException eee) {
							eee.printStackTrace();
						}
					}

					rows.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}

			}
		});

		bremove = new JButton("remove ");
		bremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				test = jTable.getSelectedRow();
				System.out.println("absolootee avvalie== " + test);
				defaultTableModel.removeRow(test);

				try {
					System.out.println("absolootee== " + test);
					rows.absolute(test + 1);
					rows.deleteRow();

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});

		lSONGID = new JLabel("InstID");
		lALBUMIDENTIFIER = new JLabel("Dname");
		lAUTHOR = new JLabel("key");
	

		tfosngID = new JTextField(null, 15);
		tfAlbumID = new JTextField(null, 15);
		tfAuthor = new JTextField(null, 15);
	
		jPanel = new JPanel();

		jPanel.add(lSONGID);
		jPanel.add(tfosngID);

		jPanel.add(lAUTHOR);
		jPanel.add(tfAuthor);

	

		jPanel.add(lALBUMIDENTIFIER);
		jPanel.add(tfAlbumID);

		jPanel.add(badd);
		jPanel.add(bremove);
		bsearch = new JButton("serach");
		bsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] temprow;
				String searchvalue = tfsearch.getText();
				System.out.println("searchvalue=" + searchvalue);

				String selectstuff = "select  INSTRID,DNAME,KEY from  Instruments where   INSTRID = '" + searchvalue
						+ "' or DNAME = '" + searchvalue + "' or KEY = '\"+searchvalue+\"'   ";
				if (defaultTableModel.getRowCount() > 0) {
					for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
						defaultTableModel.removeRow(i);
					}
				}

				try {
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rows = stmt.executeQuery(selectstuff);

					while (rows.next()) {
						temprow = new Object[] { rows.getString(1), rows.getString(2), rows.getString(3) };
						defaultTableModel.addRow(temprow);
						System.out.println("initial rows= " + temprow[0]);

					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			}
		});
		jPanel.add(bsearch);
		tfsearch = new JTextField("search", 15);
		jPanel.add(tfsearch);
		jPanel.add(jScrollPane);
		

		jTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent me) {
				if (me.getClickCount() == 2) {
					System.out.println("raft tu if double click");
					String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");

					if (value != null) {
						System.out.println("raft tu if e value");
						jTable.setValueAt(value, jTable.getSelectedRow(), jTable.getSelectedColumn());

					}

					String updatecolumn;
					try {

						rows.absolute(jTable.getSelectedRow() + 1);
						updatecolumn = defaultTableModel.getColumnName(jTable.getSelectedColumn());
						rows.updateString(updatecolumn, value);
						try {
							rows.updateRow();
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("tu update rowcount avval=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select INSTRID,DNAME,KEY from  Instruments ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2), rows.getString(3) };
								defaultTableModel.addRow(temprow);
								System.out.println("ezafe shod tu update=" + temprow);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
						// }

					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					System.out.println("raft tu else tu update");
				}
			}
		});

		return jPanel;
	}

	public JPanel updatesongs() throws Exception {

		Object databaseresult[][] = {};
		Object columns[] = { "SONGID", "AUTHOR", "TITLE", "ALBUMIDENTIFIER" };
		defaultTableModel = new DefaultTableModel(databaseresult, columns) {
			public Class getclasscolumn(int column) {
				Class returnvalue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnvalue = getValueAt(0, column).getClass();
				} else {
					returnvalue = Object.class;

				}

				return returnvalue;
			}

		};

		rows = null;
		Object[] temprow;

		String selectstuff = "select SONGID,AUTHOR,TITLE,ALBUMIDENTIFIER from  SONGS_APPEARS ";

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rows = stmt.executeQuery(selectstuff);

			while (rows.next()) {
				temprow = new Object[] { rows.getInt(1), rows.getString(2), rows.getString(3), rows.getInt(4) };
				defaultTableModel.addRow(temprow);
				System.out.println("initial rows= " + temprow[0]);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		jTable = new JTable(defaultTableModel);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);

		badd = new JButton("add");
		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String songid = "null", author = "null", title = "null", albumID = "null";
				songid = tfosngID.getText();
				author = tfAuthor.getText();
				title = tfTitle.getText();
				albumID = tfAlbumID.getText();

				if (songid.equals("")) {
					System.out.println("raft toooooooooooooo");
					songid = "'null'";
				}
				System.out.println(songid);
				System.out.println(author);
				System.out.println(title);
				System.out.println(albumID);
				try {
					rows.moveToInsertRow();

					rows.updateString("SONGID", songid);
					rows.updateString("AUTHOR", author);
					rows.updateString("TITLE", title);
					rows.updateString("ALBUMIDENTIFIER", albumID);
					try {
						rows.insertRow();
						Object[] song = { songid, author, title, albumID };
						defaultTableModel.addRow(song);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select SONGID,AUTHOR,TITLE,ALBUMIDENTIFIER from  SONGS_APPEARS ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getInt(1), rows.getString(2), rows.getString(3),
										rows.getInt(4) };
								defaultTableModel.addRow(temprow);
								System.out.println("rowcount in add=" + defaultTableModel.getRowCount());
								System.out.println("rows in add=" + temprow[0]);
							}

						} catch (SQLException eee) {
							eee.printStackTrace();
						}
					}

					rows.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}

			}
		});

		bremove = new JButton("remove ");
		bremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				test = jTable.getSelectedRow();

				try {
					rows.absolute(test + 1);
					try {
						rows.deleteRow();
						defaultTableModel.removeRow(test);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
					}

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});

		lSONGID = new JLabel("SONGID");
		lALBUMIDENTIFIER = new JLabel("ALBUMIDENTIFIER");
		lAUTHOR = new JLabel("AUTHOR");
		lTITLE = new JLabel("TITLE");

		tfosngID = new JTextField(null, 15);
		tfAlbumID = new JTextField(null, 15);
		tfAuthor = new JTextField(null, 15);
		tfTitle = new JTextField(null, 15);

		jPanel = new JPanel();

		jPanel.add(lSONGID);
		jPanel.add(tfosngID);

		jPanel.add(lAUTHOR);
		jPanel.add(tfAuthor);

		jPanel.add(lTITLE);
		jPanel.add(tfTitle);

		jPanel.add(lALBUMIDENTIFIER);
		jPanel.add(tfAlbumID);

		bsearch = new JButton("serach");
		bsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] temprow;
				String searchvalue = tfsearch.getText();
				System.out.println("searchvalue=" + searchvalue);

				String selectstuff = "select  SONGID,AUTHOR,TITLE,ALBUMIDENTIFIER from  SONGS_APPEARS where   SONGID = "
						+ searchvalue + " or AUTHOR = '" + searchvalue
						+ "' or ALBUMIDENTIFIER = \"+searchvalue+\" or TITLE = \"+searchvalue+\" ";

				if (defaultTableModel.getRowCount() > 0) {
					for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
						defaultTableModel.removeRow(i);
					}
				}

				try {
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rows = stmt.executeQuery(selectstuff);

					while (rows.next()) {
						temprow = new Object[] { rows.getInt(1), rows.getString(2), rows.getString(3), rows.getInt(4) };
						defaultTableModel.addRow(temprow);
						System.out.println("initial rows= " + temprow[0]);

					}

				} catch (SQLException ee) {
					JOptionPane.showMessageDialog(null, ee.getMessage());
					ee.printStackTrace();
				}

			}
		});
		jPanel.add(bsearch);
		tfsearch = new JTextField("search", 15);
		jPanel.add(tfsearch);

		jPanel.add(badd);
		jPanel.add(bremove);

		jPanel.add(jScrollPane);

		jTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent me) {
				if (me.getClickCount() == 2) {
					System.out.println("raft tu if double click");
					String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");

					if (value != null) {
						System.out.println("raft tu if e value");
						jTable.setValueAt(value, jTable.getSelectedRow(), jTable.getSelectedColumn());

					}

					String updatecolumn;
					try {

						rows.absolute(jTable.getSelectedRow() + 1);
						updatecolumn = defaultTableModel.getColumnName(jTable.getSelectedColumn());
						rows.updateString(updatecolumn, value);
						try {
							rows.updateRow();
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("tu update rowcount avval=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select SONGID,AUTHOR,TITLE,ALBUMIDENTIFIER from  SONGS_APPEARS ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getInt(1), rows.getString(2), rows.getString(3),
										rows.getInt(4) };
								defaultTableModel.addRow(temprow);
								System.out.println("ezafe shod tu update=" + temprow);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
						// }

					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					System.out.println("raft tu else tu update");
				}
			}
		});

		return jPanel;
	}

	public JPanel updatemusicians() throws Exception {

		Object databaseresult[][] = {};
		Object columns[] = { "SSN", "NAME" };
		defaultTableModel = new DefaultTableModel(databaseresult, columns) {
			public Class getclasscolumn(int column) {
				Class returnvalue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnvalue = getValueAt(0, column).getClass();
				} else {
					returnvalue = Object.class;

				}

				return returnvalue;
			}

		};

		rows = null;
		Object[] temprow;

		String selectstuff = "select SSN,NAME from  Musicians ";

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rows = stmt.executeQuery(selectstuff);

			while (rows.next()) {
				temprow = new Object[] { rows.getString(1), rows.getString(2) };
				defaultTableModel.addRow(temprow);
				System.out.println("initial rows= " + temprow[0]);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		jTable = new JTable(defaultTableModel);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);

		
		badd = new JButton("add");
		badd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String ssn = "null", name = "null";
				ssn = tfosngID.getText();
				name = tfAuthor.getText();
				

				if (ssn.equals("")) {
					System.out.println("raft toooooooooooooo");
					ssn = "'null'";
				}
				System.out.println(ssn);
				System.out.println(name);
				
				try {
					rows.moveToInsertRow();

					rows.updateString("SSN", ssn);
					rows.updateString("NAME", name);
					
					try {
						rows.insertRow();
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("rowcount=" + defaultTableModel.getRowCount());
						Object[] temprow;

						String selectstuff = "select SSN,NAME from  Musicians ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2) };/
								defaultTableModel.addRow(temprow);
								System.out.println("rowcount in add=" + defaultTableModel.getRowCount());
								System.out.println("rows in add=" + temprow[0]);
							}
							

						} catch (SQLException eee) {
							eee.printStackTrace();
						}
					}

					rows.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}

				Object[] musician = { ssn, name };
				defaultTableModel.addRow(musician);
			}
		});

		bremove = new JButton("remove ");
		bremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				test = jTable.getSelectedRow();
				try {
					rows.absolute(test + 1);
					try {
						rows.deleteRow();
						defaultTableModel.removeRow(test);
					} catch (SQLException ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage());
						ee.printStackTrace();
					}
				} catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});

		lSONGID = new JLabel("SSN");
		
		lAUTHOR = new JLabel("AUTHOR");
	

		tfosngID = new JTextField(null, 15);
		
		tfAuthor = new JTextField(null, 15);
		

		jPanel = new JPanel();

		jPanel.add(lSONGID);
		jPanel.add(tfosngID);

		jPanel.add(lAUTHOR);
		jPanel.add(tfAuthor);

		
		bsearch = new JButton("serach");
		bsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] temprow;
				String searchvalue = tfsearch.getText();
				System.out.println("searchvalue=" + searchvalue);

				String selectstuff = "select SSN,NAME from  Musicians where   SSN = '" + searchvalue + "' or NAME = '"
						+ searchvalue + "' ";

				if (defaultTableModel.getRowCount() > 0) {
					for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
						defaultTableModel.removeRow(i);
					}
				}

				try {
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					rows = stmt.executeQuery(selectstuff);

					while (rows.next()) {
						temprow = new Object[] { rows.getString(1), rows.getString(2) };
						defaultTableModel.addRow(temprow);
						System.out.println("initial rows= " + temprow[0]);

					}

				} catch (SQLException ee) {
					ee.printStackTrace();
				}

			}
		});
		jPanel.add(bsearch);
		jPanel.add(badd);
		jPanel.add(bremove);
		tfsearch = new JTextField("search", 15);
		jPanel.add(tfsearch);

		jPanel.add(jScrollPane);
		

		jTable.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent me) {
				if (me.getClickCount() == 2) {
					System.out.println("raft tu if double click");
					String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");

					if (value != null) {
						System.out.println("raft tu if e value");
						jTable.setValueAt(value, jTable.getSelectedRow(), jTable.getSelectedColumn());

					}

					String updatecolumn;
					try {

						rows.absolute(jTable.getSelectedRow() + 1);
						updatecolumn = defaultTableModel.getColumnName(jTable.getSelectedColumn());
						rows.updateString(updatecolumn, value);
						try {
							rows.updateRow();
						} catch (SQLException ee) {
							JOptionPane.showMessageDialog(null, ee.getMessage());
							ee.printStackTrace();
						}
						if (defaultTableModel.getRowCount() > 0) {
							for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
								defaultTableModel.removeRow(i);
							}
						}
						System.out.println("tu update rowcount avval=" + defaultTableModel.getRowCount());
						Object[] temprow;

						// ###
						String selectstuff = "select SSN,NAME from  Musicians ";
						try {
							Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rows = stmt.executeQuery(selectstuff);
							while (rows.next()) {
								temprow = new Object[] { rows.getString(1), rows.getString(2) };
								defaultTableModel.addRow(temprow);
								System.out.println("ezafe shod tu update=" + temprow);
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
						// }

					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					System.out.println("raft tu else tu update");
				}
			}
		});

		return jPanel;
	}

	public String[][] serachmusicianname(String Table, String name) throws Exception {

		ResultSet rs = null;
		String result[][] = null;
		System.out.println("name=" + name + "table=" + Table);
		String query = "select * from  " + Table + "  where NAME  ='" + name + "'";
		System.out.println("ghable try tu searchmusician");
		try {

			ps = con.prepareStatement(query);
			System.out.println("prepare shod stmnt");
			rs = ps.executeQuery();
			System.out.println("execute shod query");

			result = convert2D(rs);

			System.out.println("ghable catch");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println(result);
		return result;

	}

	public String[][] serachalbum(String Table, String name) throws Exception {

		ResultSet rs = null;
		String result[][] = null;
		System.out.println("name=" + name + "table=" + Table);
		String query = "select * from  " + Table + "  where title  ='" + name + "'";
		System.out.println("ghable try tu searchmusician");
		try {

			ps = con.prepareStatement(query);
			System.out.println("prepare shod stmnt");
			rs = ps.executeQuery();
			System.out.println("execute shod query");

			result = convert2D(rs);

			System.out.println("ghable catch");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println(result);
		return result;

	}

	public String[][] get1(String Table) throws Exception {

		ResultSet rs = null;
		String result[][] = null;
		String query = "select * from LP ";
		System.out.println("ghable try");
		try {

			ps = con.prepareStatement("select * from " + Table + " ");
			rs = ps.executeQuery();

			result = convert2D(rs);

			System.out.println("ghable catch");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println(result);
		return result;

	}

	public String[][] convert2D(ResultSet rs) {
		String[] result = null;
		String[][] finalResult = null;
		int count = 0;
		ArrayList<String[]> temp = new ArrayList<String[]>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberofcolumn = rsmd.getColumnCount();

			result = new String[numberofcolumn];
			finalResult = new String[100][numberofcolumn];
			int j = 0;
			while (rs.next()) {
				for (int i = 1; i <= numberofcolumn; i++) {
					String s = rs.getString(i);
					finalResult[j][i - 1] = s;
				}
				j++;
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return finalResult;
	}

	public String[] convert(ResultSet rs) {
		String[] result = null;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberofcolumn = rsmd.getColumnCount();
			result = new String[numberofcolumn];
			while (rs.next()) {
				for (int i = 1; i <= numberofcolumn; i++) {
					String s = rs.getString(i);
					result[i - 1] = s;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	public void insert() throws Exception {
		ps = con.prepareStatement("insert into LP (MODEL) values (?)");

		ps.setString(1, "1111");
		ps.executeUpdate();

	}

	public void close() throws Exception {
		ps.close();
		con.close();
	}

	public static java.util.Date getADate(String sDate) {

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			dateBirthDate = dateFormatter.parse(sDate);
			sqlBirthDate = new java.sql.Date(dateBirthDate.getTime());
		} catch (ParseException e1) {

			e1.printStackTrace();
		}

		return sqlBirthDate;

	}

};
