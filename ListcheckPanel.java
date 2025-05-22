import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.*;

public class ListcheckPanel extends JPanel {
	private JTextField field;
	public static DefaultListModel<String> listModel= new DefaultListModel<String>();
	public JList<String> list = new JList<String>(listModel);
	public JLabel per=new JLabel();
	public ArrayList<JLabel> tasklist;
	public static String userdir = "";
	public static String user = "";
	public static ArrayList<Task> table;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	public static String nowtime = "";
	public static int indexnumber;
	
	class InsertButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.INSERT);
		}
	}
	class EditButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = list.getSelectedIndex();
			indexnumber = index;
			if (index != -1) {
				MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.EDIT);
			} else {
				JOptionPane.showMessageDialog(list, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	class DeleteButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = list.getSelectedIndex();
			if (index != -1) {
  	          	JOptionPane.showMessageDialog(list, "Success", "Success",JOptionPane.INFORMATION_MESSAGE);
  	          	try {
  	          		listModel.remove(index);
	  	          	File f = new File(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+".txt");
		    		BufferedReader br = new BufferedReader(new FileReader(f));
		    		String tasks = "";
	 	  	      	String line = br.readLine();
	 	  	      	int cnt = 0;
	 	  	      	while (line != null) {
	 	    			if(cnt == index) {
	 	    				line = br.readLine();
	 	    				continue;
	 	    			}
	 	    			tasks += line+"\r\n";
	 	    			line = br.readLine();
	 	    			cnt++;
	 	  	      	}
	 	  	      	FileWriter out = new FileWriter(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+".txt");
	 	  	      	PrintWriter output = new PrintWriter(new BufferedWriter(out));
	 	  	      	output.write(tasks);
	 	  	      	br.close();
	 	  	      	output.close();
				}catch(IOException error) {
					System.err.println("IOException " + e.toString());
				}
			} else {
				JOptionPane.showMessageDialog(list, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	class ToarchiveButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = list.getSelectedIndex();
			if (index != -1) {
  	          	JOptionPane.showMessageDialog(list, "Success", "Success",JOptionPane.INFORMATION_MESSAGE);
  	          	try {
  	          		String x = listModel.elementAt(index);
  	          		String[] middle = x.split("/");
  	          		listModel.remove(index);
	  	          	File f = new File(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+".txt");
		    		BufferedReader br = new BufferedReader(new FileReader(f));
		    		String tasks = "";
	 	  	      	String line = br.readLine();
	 	  	      	int cnt = 0;
	 	  	      	while (line != null) {
	 	    			if(cnt == index) {
	 	    				line = br.readLine();
	 	    				continue;
	 	    			}
	 	    			tasks += line+"\r\n";
	 	    			line = br.readLine();
	 	    			cnt++;
	 	  	      	}
	 	  	      	FileWriter out = new FileWriter(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+".txt");
	 	  	      	PrintWriter output = new PrintWriter(new BufferedWriter(out));
	 	  	      	output.write(tasks);
	 	  	      	br.close();
	 	  	      	output.close();
	 	  	      	tasks = "";
	 	  	      	File f_arc = new File(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+"-archive.txt");
	 	  	      	BufferedReader br_arc = new BufferedReader(new FileReader(f_arc));
	 	  	      	line = br_arc.readLine();
	 	  	      	int ins = 0;
		  	      	while (line != null) {
		    			String[] a_task = line.split(",");
		    			Task t = new Task(a_task[0],a_task[1]+"/"+a_task[2]+"/"+a_task[3],a_task[4]);
		    			if(ins == 0) {
		    				if(ListcheckPanel.sdf.parse(middle[1]+"/"+middle[2]+"/"+middle[3]).compareTo(ListcheckPanel.sdf.parse(t.Ret_date())) < 0) {
		    					try {
		    						tasks+=middle[0]+","+middle[1]+","+middle[2]+","+middle[3]+","+middle[4]+"\r\n";
		    					}catch(ArrayIndexOutOfBoundsException er) {
		    						tasks+=middle[0]+","+middle[1]+","+middle[2]+","+middle[3]+",***"+"\r\n";
		    					}
		    					ins = 1;
		    				}
		    			}
		    			tasks += line+"\r\n";
		    			line = br_arc.readLine();
		  	      	}
		  	      	if(ins == 0) {
			  	      	try {
    						tasks+=middle[0]+","+middle[1]+","+middle[2]+","+middle[3]+","+middle[4]+"\r\n";
    					}catch(ArrayIndexOutOfBoundsException er) {
    						tasks+=middle[0]+","+middle[1]+","+middle[2]+","+middle[3]+",***"+"\r\n";
    					}
		  	      	}
		  	      	FileWriter out_arc = new FileWriter(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+"-archive.txt");
		  	      	PrintWriter output_arc = new PrintWriter(new BufferedWriter(out_arc));
		  	      	output_arc.write(tasks);
		  	      	br_arc.close();
		  	      	output_arc.close();
				}catch(IOException error) {
					System.err.println("IOException " + error.toString());
				} catch (ParseException e1) {
				}
			} else {
				JOptionPane.showMessageDialog(list, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	ListcheckPanel(){
		this.setLayout(null);
	}
	public void prepareComponents() {
		field = new JTextField("initial text");
		list.setVisibleRowCount(10);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.createVerticalScrollBar();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JButton insert = new JButton("INSERT");
		InsertButtonAction insertListener = new InsertButtonAction();
		insert.addActionListener( insertListener );
		insert.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton editButton = new JButton("EDIT");
		EditButtonAction editButtonListener = new EditButtonAction();
		editButton.addActionListener( editButtonListener );
		JButton deleteButton = new JButton("DELETE");
		DeleteButtonAction deleteButtonListener = new DeleteButtonAction();
		deleteButton.addActionListener( deleteButtonListener );
		JButton toarchiveButton = new JButton("To Archive");
		ToarchiveButtonAction toarchiveButtonListener = new ToarchiveButtonAction();
		toarchiveButton.addActionListener( toarchiveButtonListener );
		this.add(scrollPane);
		this.add(insert);
		this.add(editButton);
		this.add(deleteButton);
		this.add(toarchiveButton);
		if(MainGUI.permitflag==0) {
			per.setText("Others can not see your List.");
		}else {
			per.setText("Others can see your List.");
		}
		this.add(per);
		scrollPane.setBounds(200, 20, 570, 500);
		insert.setBounds(10, 20, 180, 50);
		editButton.setBounds(10, 90, 180, 50);
		deleteButton.setBounds(10, 160, 180, 50);
		toarchiveButton.setBounds(10, 230, 180, 50);
	}
	public void personal() {
		try {
    		File f = new File(userdir+"\\"+user+"\\"+user+".txt");
    		BufferedReader br = new BufferedReader(new FileReader(f));
    		String line = br.readLine();
    		table = new ArrayList<Task>();
    		Calendar cl = Calendar.getInstance();
    		sdf.setLenient(false);
            nowtime = sdf.format(cl.getTime());
            tasklist = new ArrayList<JLabel>();
            String for_file = "";
            try {
            	int cnt = 0;
	    		while (line != null) {
	    			cnt++;
	    			String[] a_task = line.split(",");
	    			Task t = new Task(a_task[0],a_task[1]+"/"+a_task[2]+"/"+a_task[3],a_task[4]);
	    			if(sdf.parse(nowtime).compareTo(sdf.parse(t.Ret_date())) > 0){
	    				File f_archive = new File(userdir+"\\"+user+"\\"+user+"-archive.txt");
	          	      	BufferedReader br_archive = new BufferedReader(new FileReader(f_archive));
	          	      	FileWriter out_archive = new FileWriter(userdir+"\\"+user+"\\"+user+"-archive.txt", true);
	        	      	PrintWriter output_archive = new PrintWriter(new BufferedWriter(out_archive));
	        	      	output_archive.write(line+"\r\n");
	        	      	br_archive.close();
	        	      	output_archive.close();
	    			}else {
	    				for_file += line + "\r\n";
	    				if(a_task[4].equals("***")) {
	    					a_task[4] = "";
	    				}
	    				String str = new SimpleDateFormat("yyyy/MM/dd").format(sdf.parse(t.Ret_date()));
	    				listModel.addElement(a_task[0]+"/"+str+"/  "+a_task[4]);
	    			}
	    			table.add(t);
	    			line = br.readLine();
	    		}
	    		if(cnt == 0) {
	    			listModel.addElement("No Task is here...");
	    			list.setFont(new Font("Serif", Font.PLAIN, 15));
	    		}else {
	    			list.setFont(new Font(Font.DIALOG_INPUT	, Font.PLAIN, 15));
	    		}
            }catch(ParseException e) {
            	System.err.println("ParseException " + e.toString());
            }
    		FileWriter out = new FileWriter(userdir+"\\"+user+"\\"+user+".txt");
  	      	PrintWriter output = new PrintWriter(new BufferedWriter(out));
  	      	output.write(for_file);
    		br.close();
    		output.close();
    	} catch (IOException error) {
    		System.err.println("IOException " + error.toString());
    	}
	}
}