import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel implements ActionListener {
    static JTextField ID;
    static JPasswordField pass;
    static JPasswordField pass_2;
    ListcheckPanel check;
	public void actionPerformed(ActionEvent e){
		String cmd=e.getActionCommand();
		int flags=0;
		if(cmd.equals("New!")) {
			MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.REGISTER);
		}else {
			String id = new String(ID.getText());
		    String password = new String(pass.getPassword());
		    int flag = 0;
		    if (id.equals("")) {
		    	JOptionPane.showMessageDialog(this, "No ID", "NO ID",
	              JOptionPane.ERROR_MESSAGE);
		    	pass.setText("");
		    	ID.setText("");
		    }else {
		    	try {
		    		File f = new File("input.txt");
		    		BufferedReader br = new BufferedReader(new FileReader(f));
		    		String line = br.readLine();
		      
		    		while (line != null) {
		    			String[] inputs = line.split("\\s+");
		    			if (id.equals(inputs[0])) {
		    				if (password.equals(inputs[1])) {
		    					flag = 1;
		    				}else {
		    					flag = 2;
		    				}
		    				break;
		    			}
		    			line = br.readLine();
		    		}
		    		br.close();
		    	} catch (IOException error) {
		    		System.err.println("IOException " + e.toString());
		    	}
		    	if (flag == 1){
		    		File f = new File(id+".txt");   		
		    		check.user = id;
		    		check.userdir = Paths.get(f.getAbsolutePath()).getParent().toString();
		    		JLabel label = new JLabel("Success");
		    		JOptionPane.showMessageDialog(this, label, "Success",
		        JOptionPane.INFORMATION_MESSAGE);
		    		flags=1;
		    	}else if (flag == 0){
		    		JLabel label = new JLabel("ID is wrong.");
		    		JOptionPane.showMessageDialog(this, label, "ID is wrong.",
		    				JOptionPane.ERROR_MESSAGE);
		    		ID.setText("");
		    		pass.setText("");
		    	}else {
		    		JLabel label = new JLabel("Pasword is wrong.");
		    		JOptionPane.showMessageDialog(this, label, "Pasword is wrong.",
		    				JOptionPane.ERROR_MESSAGE);
		    		pass.setText("");
		    	}
			}
		    if(flags==1) {
		    	MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.LISTCHECK);
		    }
		}
	}
	LoginPanel(){
		this.setLayout(null);
	}
	public void prepareComponents() {
	    setBounds(100, 100, 600, 400);
	    JLabel label_ID = new JLabel("Enter your ID");
	    JLabel attention_ID = new JLabel("( You can't use  '/'  ','  ' ' )");
	    attention_ID.setForeground(Color.red);
	    JLabel label_pass = new JLabel("Enter your password");
	    JLabel attention_pass = new JLabel("( You can't use  '/'  ','  ' ' )");
	    attention_pass.setForeground(Color.red);
	    ID = new JTextField(30);
	    pass = new JPasswordField(30);
	    JButton button_check = new JButton("Check!");
	    button_check.addActionListener(this);
	    JButton button_new = new JButton("New!");
	    button_new.addActionListener(this);
	    this.add(label_ID);
	    this.add(attention_ID);
	    this.add(ID);
	    this.add(label_pass);
	    this.add(attention_pass);
	    this.add(pass);
	    this.add(button_check);
	    this.add(button_new);
	    button_check.setBounds(300, 350, 200, 50);
	    button_new.setBounds(300, 420, 200, 50);
	    label_ID.setBounds(100, 20, 600, 30);
	    attention_ID.setBounds(105, 50, 600, 30);
	    ID.setBounds(100, 90, 600, 50);
	    label_pass.setBounds(100, 160, 600, 30);
	    attention_pass.setBounds(105, 190, 600, 30);
	    pass.setBounds(100, 230, 600, 50);
	}	
}
