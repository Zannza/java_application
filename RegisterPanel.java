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

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterPanel extends JPanel implements ActionListener {
	JTextField ID;
    JPasswordField pass;
    JPasswordField pass_2;

    public void actionPerformed(ActionEvent e){
    	String id = new String(ID.getText());
        String password = new String(pass.getPassword());
        String password_2 = new String(pass_2.getPassword());
        int flag = 0;
        int flag_2 = 0;
        int flag3=0;
        if (password.equals(password_2) == false) {
        	JLabel label = new JLabel("password is wrong.");
            JOptionPane.showMessageDialog(this, label, "password is wrong.",
              JOptionPane.ERROR_MESSAGE);
            pass.setText("");
            pass_2.setText("");
            flag_2 = 1;
        }
        if (flag_2 == 0) {
        	if(id.contains(" ") || id.contains("*") || id.contains("/")) {
        		JLabel label = new JLabel("The ID contains characters that cannot be used.");
        		JOptionPane.showMessageDialog(this, label, "The ID contains characters that cannot be used.",
        	              JOptionPane.ERROR_MESSAGE);
        		ID.setText("");
				pass.setText("");
				pass_2.setText("");
        	}else if(password.contains(" ") || password.contains("*") || password.contains("/")) {
        		JLabel label = new JLabel("The password contains characters that cannot be used.");
        		JOptionPane.showMessageDialog(this, label, "The password contains characters that cannot be used.",
        	              JOptionPane.ERROR_MESSAGE);
        		ID.setText("");
				pass.setText("");
				pass_2.setText("");
        	}else {
	        	try {
					File f = new File("input.txt");
					BufferedReader br = new BufferedReader(new FileReader(f));
					FileWriter out = new FileWriter("input.txt", true);
					PrintWriter output = new PrintWriter(new BufferedWriter(out));
					String line = br.readLine();
					String[] inputs = {};
					while (line != null) {
						inputs = line.split("\\s+");
						if (id.equals(inputs[0])) {
							flag = 1;
							break;
						}
						line = br.readLine();
					}
					if (flag == 0) {
						output.write(id+" "+password+"\r\n");
						String path = Paths.get(f.getAbsolutePath()).getParent().toString();
						path = path + "\\" + id;
						File newdir = new File(path);
						try {
							newdir.mkdir();
							String pathtxt = path + "\\" + id + ".txt";
							File newFile = new File(pathtxt);
							newFile.createNewFile();
							pathtxt = path + "\\" + id + "-archive.txt";
							newFile = new File(pathtxt);
							newFile.createNewFile();
							pathtxt = path + "\\" + id + "-permitted.txt";
							newFile = new File(pathtxt);
							newFile.createNewFile();
						} catch (IOException ee) {
							ee.printStackTrace();
							System.exit(1);
						}
						JLabel label = new JLabel("Success");
						JOptionPane.showMessageDialog(this, label, "Success",
							JOptionPane.INFORMATION_MESSAGE);
						ID.setText("");
						pass.setText("");
						pass_2.setText("");
	        	        flag3=1;  
	    	      	}else {
	    	      		JLabel label = new JLabel("ID already exists.");
	    	      		JOptionPane.showMessageDialog(this, label, "ID already exists..",
	    	            JOptionPane.ERROR_MESSAGE);
	    	      		ID.setText("");
	    	      		pass.setText("");
	    	      		pass_2.setText("");
	    	      	}
	    	      	br.close();
	    	      	output.close();
	    	      	if(flag3==1) {
	    	      		MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.LOGIN);
	    	      	}
	    		} catch (IOException error) {
	    	    	System.err.println("IOException " + e.toString());
		    	}
        	}
    	}
    }
    class ReturnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.LOGIN);
		}
	}
	RegisterPanel(){
		this.setLayout(null);
	}

	public void prepareComponents() {
		JLabel label_ID = new JLabel("Enter your ID!");
	    JLabel label_pass = new JLabel("Enter your password!");
	    JLabel label_pass_2 = new JLabel("Enter your password!");  
	    ID = new JTextField(30);
	    pass = new JPasswordField(30);
	    pass_2 = new JPasswordField(30);
	    JButton button_check = new JButton("Check");
	    button_check.addActionListener(this);
	    this.setLayout(new GridLayout(10, 2));
	    this.add(label_ID);
	    this.add(ID);
	    this.add(label_pass);
	    this.add(pass);
	    this.add(label_pass_2);
	    this.add(pass_2);
	    this.add(button_check);
		JButton quitButton = new JButton("return");
		ReturnAction returnListener = new ReturnAction();
		quitButton.addActionListener( returnListener );
		this.add(quitButton);
		button_check.setBounds(300, 350, 200, 50);
		quitButton.setBounds(300, 420, 200, 50);
		label_ID.setBounds(100, 20, 600, 20);
		ID.setBounds(100, 50, 600, 50);
		label_pass.setBounds(100, 120, 600, 20);
		pass.setBounds(100, 150, 600, 50);
		label_pass_2.setBounds(100, 220, 600, 20);
		pass_2.setBounds(100, 250, 600, 50);
	}
}
