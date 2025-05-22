import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

public class MainWindow extends JFrame {
	ScreenMode screenMode=ScreenMode.LOGIN;
	final int WIDTH=800;
	final int HEIGHT=600;
	CardLayout layout = new CardLayout();
	LoginPanel loginPanel;
	RegisterPanel registerPanel;
	ListcheckPanel listcheckPanel;
	EditPanel editPanel;
	ArchivePanel archivePanel;
	OtherlistPanel otherlistPanel;
	InsertPanel insertPanel;
	OtherseelistPanel otherseelistPanel;
	JMenu menu1 = new JMenu("settings");

	class Menulistener1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String othersname = JOptionPane.showInputDialog("Who can see YOUR LIST?", "");
			if (othersname == null){
				JOptionPane.showMessageDialog(null, "Interruptied!");
			}else if(othersname.equals("")) {
				JOptionPane.showMessageDialog(null, "NO INPUT!");
			}else if(othersname.equals(ListcheckPanel.user)){
				JOptionPane.showMessageDialog(null, "This is your account's name. Input other name");
			}else {
				File f = new File("input.txt");
				int flag = 0;
				try {
		    		BufferedReader br = new BufferedReader(new FileReader(f));
		    		String line = br.readLine();
		    		while (line != null) {
		    			String[] inputs = line.split("\\s+");
		    			if (othersname.equals(inputs[0])) {
		    				flag = 1;
		    				break;
		    			}
		    			line = br.readLine();
		    		}
		    		br.close();
		    	} catch (IOException error) {
		    		error.printStackTrace();
		    	}
				if(flag == 0) {
					JOptionPane.showMessageDialog(null, "This account does not exist.");
				}else {
					flag = 0;
					String pth_per= Paths.get(f.getAbsolutePath()).getParent().toString();
					try {
			    		BufferedReader br = new BufferedReader(new FileReader(pth_per+"\\"+othersname+"\\"+othersname+"-permitted.txt"));
			    		String line = br.readLine();
			    		String permit = "";
			    		while (line != null) {
			    			if (ListcheckPanel.user.equals(line)) {
			    				flag = 1;
			    				line = br.readLine();
			    				continue;
			    			}
			    			permit += line+"\r\n";
			    			line = br.readLine();
			    		}
			    		if(flag == 0) {
			    			JOptionPane.showMessageDialog(null, othersname+" can see your toDo-List!");
			    			permit += ListcheckPanel.user+"\r\n";
			    		}else {
			    			JOptionPane.showMessageDialog(null, othersname+" can't see your toDo-List!");
			    		}
			    		FileWriter out = new FileWriter(pth_per+"\\"+othersname+"\\"+othersname+"-permitted.txt");
		 	  	      	PrintWriter output = new PrintWriter(new BufferedWriter(out));
		 	  	      	output.write(permit);
		 	  	      	br.close();
		 	  	      	output.close();
			    	} catch (IOException error) {
			    		error.printStackTrace();
			    	}
				}
			}
			
		}
	}
	
	class Menulistener2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.OTHER);
		}
	}

	class Menulistener3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LoginPanel.ID.setText("");
			LoginPanel.pass.setText("");
			MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.LOGIN);
		}
	}
	
	class Menulistener4 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.ARCHIVE);
		}
	}
	
	MainWindow(){
		this.setTitle("To Do");
		JMenuBar menubar = new JMenuBar();
	    menubar.add(menu1);
	    JMenuItem menuitem1_1 = new JMenuItem("permit others");
	    JMenuItem menuitem1_2 = new JMenuItem("see others");
	    JMenuItem menuitem1_4 = new JMenuItem("archive");
	    JMenuItem menuitem1_3 = new JMenuItem("logout");
	    menu1.add(menuitem1_1);
	    menu1.add(menuitem1_2);
	    menu1.add(menuitem1_4);
	    menu1.add(menuitem1_3);
	    Menulistener1 menulistener1 = new Menulistener1();
	    menuitem1_1.addActionListener( menulistener1 );
	    Menulistener2 menulistener2 = new Menulistener2();
	    menuitem1_2.addActionListener( menulistener2 );	    
	    Menulistener4 menulistener4 = new Menulistener4();
	    menuitem1_4.addActionListener( menulistener4 );	 
	    Menulistener3 menulistener3 = new Menulistener3();
	    menuitem1_3.addActionListener( menulistener3 );	    
	    setJMenuBar(menubar);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public void preparePanels() {
		loginPanel=new LoginPanel();
		this.add(loginPanel,"login_screen");
		registerPanel=new RegisterPanel();
		this.add(registerPanel,"registration_screen");
		listcheckPanel=new ListcheckPanel();
		this.add(listcheckPanel,"list_viewing_screen");
		editPanel=new EditPanel();
		this.add(editPanel,"edit_screen");
		archivePanel=new ArchivePanel();
		this.add(archivePanel,"archeive_screen");
		otherlistPanel=new OtherlistPanel();
		this.add(otherlistPanel,"permitted_people_screen");
		insertPanel=new InsertPanel();
		this.add(insertPanel,"insert_screen");
		otherseelistPanel=new OtherseelistPanel();
		this.add(otherseelistPanel,"others_screen");
		this.pack();
	}

	public void prepareComponents() {
		loginPanel.prepareComponents();
		registerPanel.prepareComponents();
		listcheckPanel.prepareComponents();
		editPanel.prepareComponents();
		archivePanel.prepareComponents();
		otherlistPanel.prepareComponents();
		insertPanel.prepareComponents();
		otherseelistPanel.prepareComponents();
	}
	
	public void setFrontScreenAndFocus(ScreenMode s) {
		screenMode=s;
		switch(screenMode) {
		case LOGIN:
			layout.show(this.getContentPane(),"login_screen");
			//this.getContentPane().add(loginPanel, BorderLayout.CENTER);
			loginPanel.requestFocus();
			MainGUI.now=1;
			menu1.setEnabled(false);
			break;
		
		case REGISTER:
			layout.show(this.getContentPane(),"registration_screen");
			registerPanel.requestFocus();
			MainGUI.now=2;
			menu1.setEnabled(false);
			break;
			
		case LISTCHECK:
			ListcheckPanel.listModel.clear();
			OtherlistPanel.listModel.clear();
			OtherseelistPanel.listModel.clear();
			layout.show(this.getContentPane(),"list_viewing_screen");
			ArchivePanel.listModel.clear();
			listcheckPanel.requestFocus();
			listcheckPanel.personal();
			menu1.setEnabled(true);
			MainGUI.now=3;
			
			break;
		
		case EDIT:
			layout.show(this.getContentPane(),"edit_screen");
			editPanel.requestFocus();
			editPanel.indivisual();
			menu1.setEnabled(false);
			MainGUI.now=4;
			break;
		
		case ARCHIVE:
			layout.show(this.getContentPane(),"archeive_screen");
			archivePanel.requestFocus();
			archivePanel.personal();
			MainGUI.now=5;
			menu1.setEnabled(false);
			break;
		
		case OTHER:
			layout.show(this.getContentPane(),"permitted_people_screen");
			ListcheckPanel.listModel.clear();
			otherlistPanel.requestFocus();
			otherlistPanel.personal();
			MainGUI.now=7;
			menu1.setEnabled(false);
			break;
		
		case INSERT:
			layout.show(this.getContentPane(),"insert_screen");
			ListcheckPanel.listModel.clear();
			insertPanel.requestFocus();
			MainGUI.now=6;
			menu1.setEnabled(false);
			break;
		
		case OTHERSEE:
			layout.show(this.getContentPane(),"others_screen");
			ListcheckPanel.listModel.clear();
			otherseelistPanel.requestFocus();
			otherseelistPanel.personal();
			MainGUI.now=8;
			menu1.setEnabled(false);
			break;
		}
	}
}
