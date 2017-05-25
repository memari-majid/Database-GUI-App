package view;

import model.search;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class v1 extends JFrame {
	private JPanel panel1, panel2, panel22, panel3, panel4, panel5, panel6, panel212, panelpass, panel1DTable,
			panelstaffcombo, panel1DTable1;
	private JButton but, but2, but5, but6, but212, butpass, jButton, butstaffcombo, mainmenubutton;
	private JRadioButton radioButton1, radioButton2;
	private JComboBox combo, combo1;
	private JTextField text1, text5, text6, text212, textpass, tfosngID, tfAuthor, tfTitle, tfAlbumID;
	private JTextField text2;
	private JTextField text3;
	public DefaultTableModel defaultTableModel;
	public JTable jTable;

	String[] tables = { "Musicians", "Songs_Appears", "Instruments", "Plays", "Telephone_Home", "Lives", "Places",
			"Perform", "Album_Producer" };
	String[] tables1 = { "Musicians", "Album_Producer", "Songs_Appears" };

	public v1() {
		createPanel();
		add(panel1);
	}

	private void createPanel()
    {
        panel1 = new JPanel();

        radioButton1 =new JRadioButton();
        radioButton1.setText("customer");
        radioButton1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                radioButton2.setSelected(false);
               
                */
               getContentPane().removeAll();
                getContentPane().add(panel2);//Adding to content pane, not to Frame
               repaint();
               printAll(getGraphics());//Extort print all content


            }

            @Override
            public void mousePressed(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        panel1.add(radioButton1);

        radioButton2 =new JRadioButton();
        radioButton2.setText("staff");
        radioButton2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                radioButton1.setSelected(false);

                getContentPane().removeAll();
                getContentPane().add(panel212);//Adding to content pane, not to Frame
                repaint();
                printAll(getGraphics());
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        panel1.add(radioButton2);

        //////////////////////////////////////
        panel212 = new JPanel();
        JLabel label212=new JLabel("please enter the password")  ;
        text212 =new JTextField(15);
        text212.setBounds(20,20,20,20);
        panel212.add(label212)  ;
        panel212.add(text212)      ;
        but212 =new JButton("submit passwrd")  ;
        but212.addActionListener(new addButtonListener212());
        panel212.add(but212);

        /////////////////
        panelpass = new JPanel();

        JLabel labelpass1=new JLabel("####the passwrd was wrong#######")  ;
        JLabel labelpass2=new JLabel("####please enter the password again####")  ;
        textpass =new JTextField("cs430@SIUC");
        panelpass.add(labelpass1)  ;
        panelpass.add(labelpass2)  ;
        panelpass.add(textpass)      ;
        butpass =new JButton("submit passwrd")  ;
        butpass.addActionListener(new addButtonListenerpass());
        panelpass.add(butpass);
        //////////////////
        System.out.println("mainmenu button sakhte shod");
        mainmenubutton = new JButton("mainmenu");
        mainmenubutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(panel1);//Adding to content pane, not to Frame
                repaint();
                printAll(getGraphics());
                //To change body of implemented methods use File | Settings | File Templates.
            }


        });
        /////////////////////////////////////////////////////////////////
        panel2 = new JPanel();
        
        combo = new JComboBox() ;
        int i=0;
        while(i<tables1.length)
        {combo.addItem(tables1[i]);
            i++;
        }
        combo.addActionListener(new combolistener());
        // combo.setBounds(300,300,100,500);



        panel2.add(combo);
        panel2.add(mainmenubutton);
        ///////////////////////////
        panelstaffcombo = new JPanel();
        butstaffcombo = new  JButton("submit")  ;

        combo1 = new JComboBox() ;
        int ii=0;
        while(ii<tables.length)
        {combo1.addItem(tables[ii]);
            ii++;
        }
        butstaffcombo.addActionListener(new butstafflistener());
        // combo.setBounds(300,300,100,500);


        panelstaffcombo.add(butstaffcombo)    ;
        panelstaffcombo.add(combo1);
        panelstaffcombo.add(mainmenubutton);
        // //////////////////////////////////////////////////////////////

        panel3 = new JPanel();
        text3 =new JTextField("musician Name")  ;
        but = new JButton("Search from col 2");
        but.setBounds(50, 90, 190, 30);
        but.addActionListener(new addButtonListener());
        panel3.add(text3);
        panel3.add(but);
        panel3.add(mainmenubutton);
        ///////////////////

        panel4 =new JPanel();

        /////////////////////

        panel5 = new JPanel();
        text5 =new JTextField("Album TITLE")  ;
        but5 = new JButton("Search from col 5");
        but5.addActionListener(new addButtonListener5() );
        but5.setBounds(50, 90, 190, 30);
        panel5.add(text5);
        panel5.add(mainmenubutton);
        panel5.add(but5);

        /////////////////////

        panel6 = new JPanel();
        text6 =new JTextField("song title")  ;
        but6 = new JButton("Search from col 3");
        but6.addActionListener(new addButtonListener6());
        but6.setBounds(50, 90, 190, 30);
        panel6.add(text6);
        panel6.add(but6);

        panel6.add(mainmenubutton);
        /////////////////////////







    }

	public class combolistener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			combo = (JComboBox) ae.getSource();

			String str = (String) combo.getSelectedItem();

			if (str == tables1[0]) // musician
			{
				JScrollPane pane = getdata(str);

				addtonewpanel(panel3, pane);
			}
			if (str == tables1[1]) // album
			{
				JScrollPane pane = getdata(str);

				addtonewpanel(panel5, pane);
			}
			if (str == tables1[2]) // song
			{
				System.out.println("ifffff");
				JScrollPane pane = getdata(str);

				addtonewpanel(panel6, pane);
			}

		}
	}

	class butstafflistener implements ActionListener {

		public void actionPerformed(ActionEvent ae1) {

			// combo1= (JComboBox) ae.getSource();

			String str = (String) combo1.getSelectedItem();

			if (str == tables[0]) // musician
			{
				System.out.println("tu if");
				getContentPane().removeAll();

				try {
					search search2 = new search();
					panel1DTable = new JPanel();
					panel1DTable = search2.updatemusicians();
					mainmenubutton = new JButton("mainmenu");
					mainmenubutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().removeAll();
							getContentPane().add(panel1);// Adding to content
															// pane, not to
															// Frame
							repaint();
							printAll(getGraphics());
							// To change body of implemented methods use File |
							// Settings | File Templates.
						}

					});
					panel1DTable1 = new JPanel();
					panel1DTable1.setLayout(new BorderLayout());
					panel1DTable1.add(panel1DTable, BorderLayout.CENTER);
					// panel1DTable1.add(panel1DTable);
					panel1DTable1.add(mainmenubutton, BorderLayout.PAGE_START);
					// panel1DTable1.setLayout(doLayout());
					// panel1DTable1.add(panel1DTable);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				getContentPane().add(panel1DTable1);// Adding to content pane,
													// not to Frame
				repaint();
				printAll(getGraphics());
			}
			if (str == tables[1]) // song appears
			{
				System.out.println("tu if");
				getContentPane().removeAll();

				try {
					search search2 = new search();
					panel1DTable = new JPanel();
					panel1DTable = search2.updatesongs();
					mainmenubutton = new JButton("mainmenu");
					mainmenubutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().removeAll();
							getContentPane().add(panel1);// Adding to content
															// pane, not to
															// Frame
							repaint();
							printAll(getGraphics());
							// To change body of implemented methods use File |
							// Settings | File Templates.
						}

					});
					panel1DTable1 = new JPanel();
					panel1DTable1.setLayout(new BorderLayout());
					panel1DTable1.add(panel1DTable, BorderLayout.CENTER);
					// panel1DTable1.add(panel1DTable);
					panel1DTable1.add(mainmenubutton, BorderLayout.PAGE_START);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				getContentPane().add(panel1DTable1);// Adding to content pane,
													// not to Frame
				repaint();
				printAll(getGraphics());
			}
			if (str == tables[2]) // instruments
			{
				System.out.println("tu if");
				getContentPane().removeAll();

				try {
					search search2 = new search();
					panel1DTable = new JPanel();
					panel1DTable = search2.updateinstruments();
					mainmenubutton = new JButton("mainmenu");
					mainmenubutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().removeAll();
							getContentPane().add(panel1);// Adding to content
															// pane, not to
															// Frame
							repaint();
							printAll(getGraphics());
							// To change body of implemented methods use File |
							// Settings | File Templates.
						}

					});
					panel1DTable1 = new JPanel();
					panel1DTable1.setLayout(new BorderLayout());
					panel1DTable1.add(panel1DTable, BorderLayout.CENTER);
					// panel1DTable1.add(panel1DTable);
					panel1DTable1.add(mainmenubutton, BorderLayout.PAGE_START);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				getContentPane().add(panel1DTable1);// Adding to content pane,
													// not to Frame
				repaint();
				printAll(getGraphics());
			}
			if (str == tables[3]) // plays
			{
				System.out.println("tu if");
				getContentPane().removeAll();

				try {
					search search2 = new search();
					panel1DTable = new JPanel();
					panel1DTable = search2.updateplays();
					mainmenubutton = new JButton("mainmenu");
					mainmenubutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().removeAll();
							getContentPane().add(panel1);// Adding to content
															// pane, not to
															// Frame
							repaint();
							printAll(getGraphics());
							// To change body of implemented methods use File |
							// Settings | File Templates.
						}

					});
					panel1DTable1 = new JPanel();
					panel1DTable1.setLayout(new BorderLayout());
					panel1DTable1.add(panel1DTable, BorderLayout.CENTER);
					// panel1DTable1.add(panel1DTable);
					panel1DTable1.add(mainmenubutton, BorderLayout.PAGE_START);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				getContentPane().add(panel1DTable1);// Adding to content pane,
													// not to Frame
				repaint();
				printAll(getGraphics());
			}
			if (str == tables[4]) // instruments
			{
				System.out.println("tu if");
				getContentPane().removeAll();

				try {
					search search2 = new search();
					panel1DTable = new JPanel();
					panel1DTable = search2.updateTelephone_Home();
					mainmenubutton = new JButton("mainmenu");
					mainmenubutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().removeAll();
							getContentPane().add(panel1);// Adding to content
															// pane, not to
															// Frame
							repaint();
							printAll(getGraphics());
							// To change body of implemented methods use File |
							// Settings | File Templates.
						}

					});
					panel1DTable1 = new JPanel();
					panel1DTable1.setLayout(new BorderLayout());
					panel1DTable1.add(panel1DTable, BorderLayout.CENTER);
					// panel1DTable1.add(panel1DTable);
					panel1DTable1.add(mainmenubutton, BorderLayout.PAGE_START);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				getContentPane().add(panel1DTable1);// Adding to content pane,
													// not to Frame
				repaint();
				printAll(getGraphics());
			}

			if (str == tables[5]) // instruments
			{
				System.out.println("tu if");
				getContentPane().removeAll();

				try {
					search search2 = new search();
					panel1DTable = new JPanel();
					panel1DTable = search2.updatelives();
					mainmenubutton = new JButton("mainmenu");
					mainmenubutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().removeAll();
							getContentPane().add(panel1);// Adding to content
															// pane, not to
															// Frame
							repaint();
							printAll(getGraphics());
							// To change body of implemented methods use File |
							// Settings | File Templates.
						}

					});
					panel1DTable1 = new JPanel();
					panel1DTable1.setLayout(new BorderLayout());
					panel1DTable1.add(panel1DTable, BorderLayout.CENTER);
					// panel1DTable1.add(panel1DTable);
					panel1DTable1.add(mainmenubutton, BorderLayout.PAGE_START);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				getContentPane().add(panel1DTable1);// Adding to content pane,
													// not to Frame
				repaint();
				printAll(getGraphics());
			}

			if (str == tables[6]) // instruments
			{
				System.out.println("tu if");
				getContentPane().removeAll();

				try {
					search search2 = new search();
					panel1DTable = new JPanel();
					panel1DTable = search2.updateplaces();
					mainmenubutton = new JButton("mainmenu");
					mainmenubutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().removeAll();
							getContentPane().add(panel1);// Adding to content
															// pane, not to
															// Frame
							repaint();
							printAll(getGraphics());
							// To change body of implemented methods use File |
							// Settings | File Templates.
						}

					});
					panel1DTable1 = new JPanel();
					panel1DTable1.setLayout(new BorderLayout());
					panel1DTable1.add(panel1DTable, BorderLayout.CENTER);
					// panel1DTable1.add(panel1DTable);
					panel1DTable1.add(mainmenubutton, BorderLayout.PAGE_START);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				getContentPane().add(panel1DTable1);// Adding to content pane,
													// not to Frame
				repaint();
				printAll(getGraphics());
			}

			if (str == tables[7]) // instruments
			{
				System.out.println("tu if");
				getContentPane().removeAll();

				try {
					search search2 = new search();
					panel1DTable = new JPanel();
					panel1DTable = search2.updateperform();
					mainmenubutton = new JButton("mainmenu");
					mainmenubutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().removeAll();
							getContentPane().add(panel1);// Adding to content
															// pane, not to
															// Frame
							repaint();
							printAll(getGraphics());
							// To change body of implemented methods use File |
							// Settings | File Templates.
						}

					});
					panel1DTable1 = new JPanel();
					panel1DTable1.setLayout(new BorderLayout());
					panel1DTable1.add(panel1DTable, BorderLayout.CENTER);
					// panel1DTable1.add(panel1DTable);
					panel1DTable1.add(mainmenubutton, BorderLayout.PAGE_START);
				}

				catch (Exception e1) {
					e1.printStackTrace();
				}

				getContentPane().add(panel1DTable1);// Adding to content pane,
													// not to Frame
				repaint();
				printAll(getGraphics());
			}

			if (str == tables[8]) // instruments
			{
				System.out.println("tu if");
				getContentPane().removeAll();

				try {
					search search2 = new search();
					panel1DTable = new JPanel();
					panel1DTable = search2.updateAlbum();
					mainmenubutton = new JButton("mainmenu");
					mainmenubutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getContentPane().removeAll();
							getContentPane().add(panel1);// Adding to content
															// pane, not to
															// Frame
							repaint();
							printAll(getGraphics());
							// To change body of implemented methods use File |
							// Settings | File Templates.
						}

					});
					panel1DTable1 = new JPanel();
					panel1DTable1.setLayout(new BorderLayout());
					panel1DTable1.add(panel1DTable, BorderLayout.CENTER);
					// panel1DTable1.add(panel1DTable);
					panel1DTable1.add(mainmenubutton, BorderLayout.PAGE_START);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				getContentPane().add(panel1DTable1);// Adding to content pane,
													// not to Frame
				repaint();
				printAll(getGraphics());
			}

		}
	}

	public class combolistener1 implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

		}
	}

	public void addtonewpanel(JPanel panel, JScrollPane pane) {

		// panel.setLayout(new BorderLayout());
		// panel.add(pane, BorderLayout.PAGE_END);
		// panel1DTable1.add(panel1DTable);
		// panel.add(mainmenubutton,BorderLayout.PAGE_START) ;

		panel.add(pane);
		panel.add(mainmenubutton);
		getContentPane().removeAll();
		getContentPane().add(panel);// Adding to content pane, not to Frame
		repaint();
		printAll(getGraphics());
	}

	public JScrollPane getdata(String str) {

		search search1 = null;

		String[][] result;

		JScrollPane pane1 = null;
		try {

			search1 = new search();

			result = search1.get1(str);

			String data[][] = result;

			int col = data[1].length;
			System.out.println("data[1].length=" + data[1].length);
			String[] column = new String[col];
			try {
				for (int j = 0; j < column.length; j++) {
					column[j] = "" + (j + 1) + "";
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			// String [][]data = {{"1","2"},{"1","2"}};

			JTable table = new JTable(data, column);

			pane1 = new JScrollPane(table);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return (pane1);
	}

	class addButtonListener implements ActionListener {
		private String txt;

		public void actionPerformed(ActionEvent ae) {
			// panel4=new JPanel();

			txt = text3.getText();
			// int txt1= Integer.parseInt(txt);
			System.out.println("az txtbox :" + txt);

			String str = (String) combo.getSelectedItem();
			System.out.println("combo=" + str);

			search search1 = null;

			String[][] result;

			try {

				search1 = new search();

				result = search1.serachmusicianname(str, txt);
				System.out.println("result az database daryaft shod");
				String data[][] = result;
				System.out.println("rikhte shod tu array");

				// int col = data[1].length;
				// System.out.println("data[1].length="+data[1].length);
				String[] column = { "ssn", "name" };// new String[col] ;
				/*
				 * try{ for(int j=0;j<column.length;j++) { column[j]=
				 * ""+(j+1)+""; } } catch (Exception e) {
				 * System.out.println(e.getMessage());}
				 */
				// String [][]data = {{"1","2"},{"1","2"}};
				// column={"ssn","name"} ;

				System.out.println("column =" + column);

				JTable table = new JTable(data, column);

				JScrollPane pane = new JScrollPane(table);

				addtonewpanel(panel4, pane);

			} catch (Exception e1) {
				e1.printStackTrace(); // To change body of catch statement use
										// File | Settings | File Templates.
			}
		}
	}

	class addButtonListener5 implements ActionListener {
		private String txt;

		public void actionPerformed(ActionEvent ae1) {
			// panel4=new JPanel();

			txt = text5.getText();
			// int txt1= Integer.parseInt(txt);
			System.out.println("az txtbox5 :" + txt);

			String str = (String) combo.getSelectedItem();
			System.out.println("combo=" + str);

			search search1 = null;

			String[][] result;

			try {

				search1 = new search();

				result = search1.serachalbum(str, txt);
				System.out.println("result az database daryaft shod");
				String data[][] = result;
				System.out.println("rikhte shod tu array");

				// int col = data[1].length;
				// System.out.println("data[1].length="+data[1].length);
				String[] column = { "ID", "SSN", "CPYRIGHT", "SPEED", "TITLE" };// new
																				// String[col]
																				// ;
				/*
				 * try{ for(int j=0;j<column.length;j++) { column[j]=
				 * ""+(j+1)+""; } } catch (Exception e) {
				 * System.out.println(e.getMessage());}
				 */
				// String [][]data = {{"1","2"},{"1","2"}};
				// column={"ssn","name"} ;

				System.out.println("column =" + column);

				JTable table = new JTable(data, column);

				JScrollPane pane = new JScrollPane(table);

				addtonewpanel(panel4, pane);

			} catch (Exception e1) {
				e1.printStackTrace(); // To change body of catch statement use
										// File | Settings | File Templates.
			}
		}
	}

	class addButtonListener6 implements ActionListener {
		private String txt;

		public void actionPerformed(ActionEvent ae1) {
			// panel4=new JPanel();

			txt = text6.getText();
			// int txt1= Integer.parseInt(txt);
			System.out.println("az txtbox6 :" + txt);

			String str = (String) combo.getSelectedItem();
			System.out.println("combo=" + str);

			search search1 = null;

			String[][] result;

			try {

				search1 = new search();

				result = search1.serachalbum(str, txt);
				System.out.println("result az database daryaft shod");
				String data[][] = result;
				System.out.println("rikhte shod tu array");

				// int col = data[1].length;
				// System.out.println("data[1].length="+data[1].length);

				String[] column = { "ID", "author", "title", "album" };// new
																		// String[col]
																		// ;

				/*
				 * try{ for(int j=0;j<column.length;j++) { column[j]=
				 * ""+(j+1)+""; } } catch (Exception e) {
				 * System.out.println(e.getMessage());}
				 */
				// String [][]data = {{"1","2"},{"1","2"}};
				// column={"ssn","name"} ;

				System.out.println("column =" + column);

				JTable table = new JTable(data, column);

				JScrollPane pane = new JScrollPane(table);

				addtonewpanel(panel4, pane);

			} catch (Exception e1) {
				e1.printStackTrace(); // To change body of catch statement use
										// File | Settings | File Templates.
			}
		}
	}

	class addButtonListener212 implements ActionListener {
		private String txt;

		public void actionPerformed(ActionEvent ae) {
			String t = text212.getText();
			System.out.println("text = " + text212);
			System.out.println("t=" + t);
			String t1 = "cs430@SIUC";
			System.out.println("t1=" + t1);

			if (t1.equals(t))

			{
				getContentPane().removeAll();
				getContentPane().add(panelstaffcombo);// Adding to content pane,
														// not to Frame
				repaint();
				printAll(getGraphics());

			} else {
				System.out.println("else");
				getContentPane().removeAll();
				getContentPane().add(panelpass);// Adding to content pane, not
												// to Frame
				repaint();
				printAll(getGraphics());
			}

		}
	}

	class addButtonListenerpass implements ActionListener {
		private String txt;

		public void actionPerformed(ActionEvent ae) {
			if (textpass.getText().equals("cs430@SIUC")) {
				getContentPane().removeAll();
				getContentPane().add(panelstaffcombo);// Adding to content pane,
														// not to Frame
				repaint();
				printAll(getGraphics());
			} else {
				getContentPane().removeAll();
				getContentPane().add(panelpass);// Adding to content pane, not
												// to Frame
				repaint();
				printAll(getGraphics());
			}

		}
	}

	public static void main(String args[]) {
		v1 frame = new v1();
		frame.setTitle("Test Software");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}

}