/*
 * Jointly developed by Anjith Sasindran, Shifna Khadeeja, Salu Thomas & Ujwala Vijayan
 * Create Database campusautomation with full privilages for username and password 
 * Create Tables student, marks1, marks2, marks3, marks4 as per the screenshots in the folder database
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class UserInterface {

	/**
	 * @param args
	 */
	JFrame entireFrame;
	JButton add, marks;
	JTable table;
	Dimension entireFrameMin, buttons, dialogSize;
	JPanel top;
	JScrollPane scrollPane;
	JLabel edit, delete;
	String sqlQuery;
	int i, admissionNo=3000;
	String columnNames[]={"Firstname", "Lastname", "Admission No", "Mobile", "Sex", "Branch", "Sem", "DOB"};
	String rowValues[];
	DefaultTableModel tabelModel;
	
	//JDBC
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserInterface ui = new UserInterface();
		ui.build();
	}
	public void build()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/campusautomation", "username", "password");
			stmt = conn.createStatement();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		entireFrameMin = new Dimension(550, 550);
		buttons = new Dimension(100,100);
		dialogSize = new Dimension(300, 370);
		
		entireFrame = new JFrame("Campus Automation");
		entireFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		entireFrame.setMinimumSize(entireFrameMin);
		entireFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
		
		add = new JButton("Add student");
		marks = new JButton("Enter Marks");	
		
		edit = new JLabel("      Press E to edit a row            Press DEL to delete a row");
		
		top.add(add);
		top.add(marks);
		
		tabelModel = new DefaultTableModel(columnNames, 0);
		table = new Table();
		table.setModel(tabelModel);
		
		try
		{
			updateDatabase();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		table.setRowHeight(25);
		table.setFont(new Font("Arial", Font.ITALIC, 15));
		
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		
		table.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_DELETE)
				{
					//Delete an entry
					try
					{
						if(!(table.getValueAt(table.getSelectedRow(), 2).equals("")))
						{
							if(JOptionPane.showConfirmDialog(entireFrame, "Are you sure you want to delete?")==JOptionPane.YES_OPTION)
							{
								sqlQuery = "DELETE FROM student WHERE AdmissionNumber = "+table.getValueAt(table.getSelectedRow(), 2)+";";
								stmt.executeUpdate(sqlQuery);
								sqlQuery = "DELETE FROM marks1 WHERE AdmissionNumber = "+table.getValueAt(table.getSelectedRow(), 2)+";";
								stmt.executeUpdate(sqlQuery);
								sqlQuery = "DELETE FROM marks2 WHERE AdmissionNumber = "+table.getValueAt(table.getSelectedRow(), 2)+";";
								stmt.executeUpdate(sqlQuery);
								sqlQuery = "DELETE FROM marks3 WHERE AdmissionNumber = "+table.getValueAt(table.getSelectedRow(), 2)+";";
								stmt.executeUpdate(sqlQuery);
								sqlQuery = "DELETE FROM marks4 WHERE AdmissionNumber = "+table.getValueAt(table.getSelectedRow(), 2)+";";
								stmt.executeUpdate(sqlQuery);
								tabelModel.removeRow(table.getSelectedRow());
								admissionNo--;
							}
							
						}
					}
					catch(Exception e1)
					{
						
					}
				}
				else
					if(e.getKeyCode()==KeyEvent.VK_E)
					{
						//Edit an entry
						try
						{
							
							final int admissionNo=Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 2));
							String sexs[] = {"Male", "Female"};
							final JDialog box = new JDialog(entireFrame, "Add Student");
							box.setSize(dialogSize);
							box.setResizable(false);
							box.setLocationRelativeTo(null);
							
							final JPanel dialogBox = new JPanel();
							JPanel down = new JPanel();
							
							dialogBox.setLayout(new GridLayout(8, 2,0, 10));
							box.setLayout(new BorderLayout());
							
							JLabel firstName = new JLabel("First Name");
							JLabel lastName = new JLabel("Last Name");
							JLabel admisNo = new JLabel("Admission Number");
							JLabel mobile = new JLabel("Mobile                           +91");
							JLabel sex = new JLabel("Sex"); 
							JLabel branch = new JLabel("Branch");
							JLabel sem = new JLabel("Semester");
							JLabel dob = new JLabel("DOB  (YYYY-MM-DD)");
							
							final JTextField firstNa = new JTextField((String) table.getValueAt(table.getSelectedRow(), 0));
							final JTextField lastNa = new JTextField((String) table.getValueAt(table.getSelectedRow(), 1));
							final JTextField admisNa = new JTextField((String) table.getValueAt(table.getSelectedRow(), 2));
							admisNa.setEditable(false);
							final JTextField mobileNa = new JTextField((String) table.getValueAt(table.getSelectedRow(), 3));
							final JComboBox<?> sexNa = new JComboBox<Object>(sexs);
							sexNa.setSelectedItem(table.getValueAt(table.getSelectedRow(), 4));
							final JTextField branchNa = new JTextField("CSE");
							final JTextField semNa = new JTextField("6");
							final JTextField dobNa = new JTextField((String) table.getValueAt(table.getSelectedRow(), 7));
							JButton enter = new JButton("OK");
							final JLabel info = new JLabel();
							
							semNa.setEditable(false);
							branchNa.setEditable(false);
							
							dialogBox.add(firstName);
							dialogBox.add(firstNa);
							dialogBox.add(lastName);
							dialogBox.add(lastNa);
							dialogBox.add(admisNo);
							dialogBox.add(admisNa);
							dialogBox.add(mobile);
							dialogBox.add(mobileNa);
							dialogBox.add(sex);
							dialogBox.add(sexNa);
							dialogBox.add(branch);
							dialogBox.add(branchNa);
							dialogBox.add(sem);
							dialogBox.add(semNa);
							dialogBox.add(dob);
							dialogBox.add(dobNa);
							
							down.setLayout(new GridLayout(2,1));
							down.add(enter);
							down.add(info);
							box.add(down, BorderLayout.SOUTH);
							box.add(dialogBox, BorderLayout.NORTH);
							box.setVisible(true);
							
							enter.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e)
								{
									try
									{
										sqlQuery = "UPDATE student SET firstname = '"+firstNa.getText()+"',lastname = '"+lastNa.getText()+"',admissionnumber = "+
												admisNa.getText()+",mobile = '"+mobileNa.getText()+"', sex = '"+sexNa.getSelectedItem()+"', dob = '"+
												dobNa.getText()+"' WHERE admissionnumber="+admissionNo+";";
										stmt.executeUpdate(sqlQuery);	
										tabelModel.removeRow(table.getSelectedRow());
										for (i = 0 ; i < 8 ; i++)
										{
											rowValues[i] = ((JTextField)dialogBox.getComponent((2*i)+1)).getText();
											if (i == 3)
												i++;
										}
										rowValues[4] = (String) sexNa.getSelectedItem();
										tabelModel.addRow(rowValues);
										box.dispose();
									}
									catch(Exception exc)
									{
										info.setText("Failed to add to database");
									}
								}
							});
						}
						catch(Exception e2)
						{
							
						}
					}
				
			}
		});
		
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String sexs[] = {"Male", "Female"};
				final JDialog box = new JDialog(entireFrame, "Add Student");
				box.setSize(dialogSize);
				box.setResizable(false);
				box.setLocationRelativeTo(null);
				 
				final JPanel dialogBox = new JPanel();
				JPanel down = new JPanel();
				
				dialogBox.setLayout(new GridLayout(8, 2,0, 10));
				box.setLayout(new BorderLayout());
				
				JLabel firstName = new JLabel("First Name");
				JLabel lastName = new JLabel("Last Name");
				JLabel admisNo = new JLabel("Admission Number");
				JLabel mobile = new JLabel("Mobile                           +91");
				JLabel sex = new JLabel("Sex"); 
				JLabel branch = new JLabel("Branch");
				JLabel sem = new JLabel("Semester");
				JLabel dob = new JLabel("DOB  (YYYY-MM-DD)");
				
				final JTextField firstNa = new JTextField();
				final JTextField lastNa = new JTextField();
				final JTextField admisNa = new JTextField();
				admisNa.setEditable(false);
				admisNa.setText(admissionNo+"");
				final JTextField mobileNa = new JTextField();
				final JComboBox<?> sexNa = new JComboBox<Object>(sexs);
				final JTextField branchNa = new JTextField("CSE");
				final JTextField semNa = new JTextField("6");
				final JTextField dobNa = new JTextField();
				JButton enter = new JButton("OK");
				final JLabel info = new JLabel();
				
				semNa.setEditable(false);
				branchNa.setEditable(false);
				
				dialogBox.add(firstName);
				dialogBox.add(firstNa);
				dialogBox.add(lastName);
				dialogBox.add(lastNa);
				dialogBox.add(admisNo);
				dialogBox.add(admisNa);
				dialogBox.add(mobile);
				dialogBox.add(mobileNa);
				dialogBox.add(sex);
				dialogBox.add(sexNa);
				dialogBox.add(branch);
				dialogBox.add(branchNa);
				dialogBox.add(sem);
				dialogBox.add(semNa);
				dialogBox.add(dob);
				dialogBox.add(dobNa);
				
				down.setLayout(new GridLayout(2,1));
				down.add(enter);
				down.add(info);
				box.add(down, BorderLayout.SOUTH);
				box.add(dialogBox, BorderLayout.NORTH);
				box.setVisible(true);
				
				enter.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							sqlQuery = "INSERT INTO STUDENT VALUES ('"+firstNa.getText()+"', '"+lastNa.getText()+"', "+
									admisNa.getText()+", '"+mobileNa.getText()+"', '"+sexNa.getSelectedItem()+"', 'CSE', 6, '"+
									dobNa.getText()+"');";
							stmt.executeUpdate(sqlQuery);
							for (i = 0 ; i < 8 ; i++)
							{
								rowValues[i] = ((JTextField)dialogBox.getComponent((2*i)+1)).getText();
								if (i == 3)
									i++;
							}
							rowValues[4] = (String) sexNa.getSelectedItem();
							tabelModel.addRow(rowValues);
							admissionNo++;
							box.dispose();
						}
						catch(Exception exc)
						{
							info.setText("Failed to add to database");
						}
					}
				});
			}
		});
		
		//Marks Section
		
		marks.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				final JDialog marksDialog = new JDialog(entireFrame, "Enter Marks");
				JPanel tops = new JPanel();
				tops.setLayout(new GridLayout(1,2));
				marksDialog.setSize(400, 450);
				marksDialog.setLayout(new BorderLayout(1, 7));
				marksDialog.setResizable(false);
				marksDialog.setLocationRelativeTo(null);				
				
				final JTextField admisNa = new JTextField();
				@SuppressWarnings("unused")
				TextPrompt prompt = new TextPrompt("Enter Admission No", admisNa);
				JButton checker = new JButton("OK");

				final JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
				tabs.addTab("1st Series", makePanel());
				tabs.addTab("2nd Series", makePanel());
				tabs.addTab("3rd Series", makePanel());
				tabs.addTab("4th Series", makePanel());
				
				tops.add(admisNa, BorderLayout.NORTH);
				tops.add(checker);
				
				final JButton finisher = new JButton("ENTER");
				finisher.setEnabled(false);
				
				marksDialog.add(tops, BorderLayout.NORTH);
				marksDialog.add(tabs, BorderLayout.CENTER);
				marksDialog.add(finisher, BorderLayout.SOUTH);
				marksDialog.setVisible(true);
				
				//Start of this
				admisNa.addFocusListener(new FocusListener(){

					public void focusGained(FocusEvent arg0) {
						// TODO Auto-generated method stub
						finisher.setEnabled(false);
						
					}

					public void focusLost(FocusEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				checker.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int temp;
						try
						{
							
							sqlQuery = "SELECT Admissionnumber FROM student WHERE Admissionnumber = "+Integer.parseInt(admisNa.getText());
							rs = stmt.executeQuery(sqlQuery);
							for ( i = 0 ; i < 4 ; i++)
								for ( int k = 0 ; k < 8 ; k++)
									((JTextField)((JPanel)tabs.getComponent(i)).getComponent(1+(2*k))).setText("");
							if (!rs.next()) //Record not found
								{
									finisher.setEnabled(false);
									JOptionPane.showMessageDialog(marksDialog, "Record not found");
								}
							else
							{
								
								sqlQuery = "SELECT * FROM marks1 WHERE Admissionnumber = "+Integer.parseInt(admisNa.getText());
								rs = stmt.executeQuery(sqlQuery);
								while(rs.next())
								{
									for ( i = 0 ; i < 8 ; i++)
									{
										temp = Integer.parseInt(rs.getString(i+1));
										JTextField instance = (JTextField)((JPanel)tabs.getComponent(0)).getComponent(1+(2*i));
										instance.setText(temp+"");
									}
								}
								sqlQuery = "SELECT * from marks2 WHERE Admissionnumber = "+Integer.parseInt(admisNa.getText());
								rs = stmt.executeQuery(sqlQuery);
								while(rs.next())
								{
									for ( i = 0 ; i < 8 ; i++)
									{
										temp = Integer.parseInt(rs.getString(i+1));
										JTextField instance = (JTextField) ((JPanel)tabs.getComponent(1)).getComponent(1+(2*i));
										instance.setText(temp+"");
									}
								}
								sqlQuery = "SELECT * from marks3 WHERE Admissionnumber = "+Integer.parseInt(admisNa.getText());
								rs = stmt.executeQuery(sqlQuery);
								while(rs.next())
								{
									for ( i = 0 ; i < 8 ; i++)
									{
										temp = Integer.parseInt(rs.getString(i+1));
										JTextField instance = (JTextField)((JPanel) tabs.getComponent(2)).getComponent(1+(2*i));
										instance.setText(temp+"");
									}
								}
								sqlQuery = "SELECT * from marks4 WHERE Admissionnumber = "+Integer.parseInt(admisNa.getText());
								rs = stmt.executeQuery(sqlQuery);
								while(rs.next())
								{
									for ( i = 0 ; i < 8 ; i++)
									{
										temp = Integer.parseInt(rs.getString(i+1));
										JTextField instance = (JTextField)((JPanel)tabs.getComponent(3)).getComponent(1+(2*i));
										instance.setText(temp+"");
									}
								}
								finisher.setEnabled(true);
							}
							
						}
						catch(NumberFormatException e1) //NumberFormatException of "" String
						{
							finisher.setEnabled(false);
							JOptionPane.showMessageDialog(marksDialog, "Enter a valid number");
						}
						catch(SQLException e2)
						{
							e2.printStackTrace();
						}
					}
				});
				
				
				finisher.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int sample = tabs.getSelectedIndex();
						int currentValues[] = new int[8];
						
						for( int i = 0 ; i < 8 ; i++)
						{
							currentValues[i] = Integer.parseInt(((JTextField)((JPanel)tabs.getComponent(sample)).getComponent(1+(2*i))).getText());
						}
						
						sqlQuery = "INSERT INTO marks"+(sample+1)+" VALUES ("+currentValues[0]+
								", "+currentValues[1]+", "+currentValues[2]+", "+
								currentValues[3]+", "+currentValues[4]+", "+currentValues[5]+
								", "+currentValues[6]+", "+currentValues[7]+", "+(Integer.parseInt(admisNa.getText())+");");
						try
						{
							stmt.executeUpdate(sqlQuery);
						}
						catch(SQLException e3)
						{
						    sqlQuery = "UPDATE marks"+(sample+1)+" SET k601 = "+currentValues[0]+
									", k602 = "+currentValues[1]+", k603 = "+currentValues[2]+", k604 = "+
									currentValues[3]+", k605 = "+currentValues[4]+", k606 = "+currentValues[5]+
									", k607 = "+currentValues[6]+", k608 = "+currentValues[7]+", Admissionnumber = "+(Integer.parseInt(admisNa.getText())+" WHERE Admissionnumber = "+(Integer.parseInt(admisNa.getText())+";"));
						    try {
								stmt.executeUpdate(sqlQuery);
							} 
						    catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
				
				//End of this
			}
			public JPanel makePanel()
			{
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(8,2));
				for (int i = 0; i < 8 ; i++)
				{
					panel.add(new JLabel("2K6CS60"+(i+1)));
					panel.add(new JTextField());
				}
				return panel;
			}
		});
		
		
		entireFrame.add(top, BorderLayout.NORTH);
		entireFrame.add(scrollPane, BorderLayout.CENTER);
		entireFrame.add(edit, BorderLayout.SOUTH);
		entireFrame.setVisible(true);
		
	}
	public void updateDatabase() throws Exception
	{
		rowValues=new String[8];
		sqlQuery = "Select * from student";
		rs = stmt.executeQuery(sqlQuery);
		while(rs.next())
		{
			rowValues[0]=rs.getString(1);
			rowValues[1]=rs.getString(2);
			rowValues[2]=rs.getString(3);
			rowValues[3]=rs.getString(4);
			rowValues[4]=rs.getString(5);
			rowValues[5]=rs.getString(6);
			rowValues[6]=rs.getString(7);
			rowValues[7]=rs.getString(8);
			admissionNo++;
			tabelModel.addRow(rowValues);
		}
	}

}
