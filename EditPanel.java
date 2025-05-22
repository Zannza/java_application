import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.text.ParseException;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.*;
import java.awt.event.*;

public class EditPanel extends JPanel implements ActionListener {
	JTextField c1;
	JTextField c2;
	JTextField c3;
	JTextField c4;
	JTextField c5;
    public void actionPerformed(ActionEvent e){	
    	String c1s = new String(c1.getText());
        String c2s = new String(c2.getText());
        String c3s = new String(c3.getText());
        String c4s = new String(c4.getText());
        String c5s = new String(c5.getText());
        Task newtask = new Task(c1s,c2s+"/"+c3s+"/"+c4s,c5s);
        int flag = 0;
        ListcheckPanel.sdf.setLenient(false);
        try {
	       if(c1s.equals("")||c2s.equals("")||c3s.equals("")||c4s.equals("")) {
	    	   JLabel label = new JLabel("Please fill in the mandatory information in the spaces provided.");
	    	   JOptionPane.showMessageDialog(this, label, "ERROR",JOptionPane.ERROR_MESSAGE);
	    	   flag=0;
	       }else if(ListcheckPanel.sdf.parse(ListcheckPanel.nowtime).compareTo(ListcheckPanel.sdf.parse(newtask.Ret_date())) > 0) {
	       		JLabel label = new JLabel("Please set the date later than today.");
				JOptionPane.showMessageDialog(this, label, "ERROR",JOptionPane.ERROR_MESSAGE);
				c2.setText("");c3.setText("");c4.setText("");
	       }else if(c1s.contains("*") || c1s.contains("/")) {
	       		JLabel label = new JLabel("The task's name contains characters that cannot be used.");
	       		JOptionPane.showMessageDialog(this, label, "The task's name contains characters that cannot be used.",
	       	              JOptionPane.ERROR_MESSAGE);
	       		c1.setText("");
	       }else if(c5s.contains("*") || c5s.contains("/")) {
	       		JLabel label = new JLabel("The remarks contains characters that cannot be used.");
	       		JOptionPane.showMessageDialog(this, label, "The remarks contains characters that cannot be used.",
	       	              JOptionPane.ERROR_MESSAGE);
	       		c5.setText("");
		   }else {
	       		flag=1;
	       }
        }catch(ParseException e1) {
        	JLabel label = new JLabel("The date entered is invalid.");
			JOptionPane.showMessageDialog(this, label, "ERROR",JOptionPane.ERROR_MESSAGE);
		}
         if(flag==1) {
        	 try {
 	        	File f = new File(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+".txt");
 	    		BufferedReader br = new BufferedReader(new FileReader(f));
 	    		String tasks = "";
 	  	      	String line = br.readLine();
 	  	      	int ins = 0;
 	  	      	int cnt = 0;
 	  	      	ListcheckPanel.sdf.setLenient(false);
 	  	      	while (line != null) {
 	    			String[] a_task = line.split(",");
 	    			Task t = new Task(a_task[0],a_task[1]+"/"+a_task[2]+"/"+a_task[3],a_task[4]);
 	    			if(ins == 0) {
	    				if(ListcheckPanel.sdf.parse(newtask.Ret_date()).compareTo(ListcheckPanel.sdf.parse(t.Ret_date())) < 0) {
	    					while(c1s.length() < 30) {
	    						c1s+=" ";
	    					}
	    					if(c5s == "") {
	    						c5s = "***";
	    					}
	    					tasks+=c1s+","+c2s+","+c3s+","+c4s+","+c5s+"\r\n";
	    					ins = 1;
	    				}
	    			}
 	    			if(cnt != ListcheckPanel.indexnumber) {
 	    				tasks += line+"\r\n";
 	    			}
 	    			line = br.readLine();
 	    			cnt++;
 	  	      	}
 	  	      	if(ins == 0) {
	 	  	      	while(c1s.length() < 30) {
						c1s+=" ";
					}
	 	  	      	if(c5s == "") {
						c5s = "***";
					}
 	  	      		tasks+=c1s+","+c2s+","+c3s+","+c4s+","+c5s+"\r\n";
 	  	      	}
 	  	      	FileWriter out = new FileWriter(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+".txt");
 	  	      	PrintWriter output = new PrintWriter(new BufferedWriter(out));
 	  	      	output.write(tasks);
 	  	      	br.close();
 	  	      	output.close();
         	}catch(IOException error) {
     		System.err.println("IOException " + error.toString());
         	} catch (ParseException e1) {
         		JLabel label = new JLabel("The date entered is invalid.");
    			JOptionPane.showMessageDialog(this, label, "ERROR",JOptionPane.ERROR_MESSAGE);
    			c1.setText("");c2.setText("");c3.setText("");c4.setText("");c5.setText("");
			}
        	 c1.setText("");c2.setText("");c3.setText("");c4.setText("");c5.setText("");
        	 MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.LISTCHECK);
         }
      }
    	class ReturnAction implements ActionListener {
    		public void actionPerformed(ActionEvent e) {
    			MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.LISTCHECK);
    		}
    	}
	EditPanel(){
		this.setLayout(null);
	}
	public void prepareComponents() {
		JLabel attention = new JLabel("You can't use  '/'  ','   in task's name and remarks");
		attention.setForeground(Color.red);
		this.add(attention);
		JLabel c1l = new JLabel("Task");
		c1 = new JTextField(30);
		this.add(c1l);
	    this.add(c1);
	    JLabel c2l = new JLabel("year");
	    c2 = new JTextField(30);
	    this.add(c2l);
	    this.add(c2);
	    JLabel c3l = new JLabel("month");
	    c3 = new JTextField(30);
	    this.add(c3l);
	    this.add(c3);
	    JLabel c4l = new JLabel("day");
	    c4 = new JTextField(30);
	    this.add(c4l);
	    this.add(c4);
	    JLabel c5l = new JLabel("remarks");
	    c5 = new JTextField(30);
	    this.add(c5l);
	    this.add(c5);
	    c1.setText("");c2.setText("");c3.setText("");c4.setText("");c5.setText("");
	    JButton button_check = new JButton("OK");
	    button_check.addActionListener(this);
	    this.add(button_check);
	    JButton returnButton = new JButton("return to my list");
		ReturnAction returnButtonListener = new ReturnAction();
		returnButton.addActionListener( returnButtonListener );
		this.add(returnButton);
		int tate=50;
		int yoko=400;
		c1l.setBounds(200, 10, yoko, tate);
		c1.setBounds(320, 10, yoko, tate);
		c2l.setBounds(200, 88, yoko, tate);
		c2.setBounds(320, 88, yoko, tate);
		c3l.setBounds(200, 166, yoko, tate);
		c3.setBounds(320, 166, yoko, tate);
		c4l.setBounds(200, 244, yoko, tate);
		c4.setBounds(320, 244, yoko, tate);
		c5l.setBounds(200, 322, yoko, tate);
		c5.setBounds(320, 322, yoko, tate);
		attention.setBounds(230, 380, yoko, tate);
		returnButton.setBounds(150, 450, 200, 50);
		button_check.setBounds(400, 450, 200, 50);
	}
	public void indivisual() {
		String task = ListcheckPanel.listModel.elementAt(ListcheckPanel.indexnumber);
		String[] middle = task.split("/");
		try {
			c1.setText(middle[0]);c2.setText(middle[1]);c3.setText(middle[2]);c4.setText(middle[3]);c5.setText(middle[4]);
		}catch(ArrayIndexOutOfBoundsException er) {
			c1.setText(middle[0]);c2.setText(middle[1]);c3.setText(middle[2]);c4.setText(middle[3]);c5.setText("");
		}
	}
}
