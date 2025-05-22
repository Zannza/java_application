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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ArchivePanel extends JPanel {

	private JTextField field;
	public static DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> list;
	public JLabel per=new JLabel();


	class ReturnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.LISTCHECK);
		}
	}

	ArchivePanel(){
		this.setLayout(null);
	}
	public void prepareComponents() {
		field = new JTextField("initial text");
		list = new JList<String>(listModel);
		list.setVisibleRowCount(10);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.createVerticalScrollBar();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scrollPane);

		JButton returnButton = new JButton("return to my list");
		ReturnAction returnListener = new ReturnAction();
		returnButton.addActionListener( returnListener );
		this.add(returnButton);
		scrollPane.setBounds(200, 20, 570, 500);
		returnButton.setBounds(10, 20, 180, 50);

	}
	public void personal() {
		try {
    		File f = new File(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+"-archive.txt");
    		BufferedReader br = new BufferedReader(new FileReader(f));
    		String line = br.readLine();
    		ArrayList<Task> table = new ArrayList<Task>();
    		int cnt = 0;
    		while (line != null) {
    			cnt++;
    			String[] a_task = line.split(",");
    			Task t = new Task(a_task[0],a_task[1]+"/"+a_task[2]+"/"+a_task[3],a_task[4]);
    			if(a_task[4].equals("***")) {
    				a_task[4] = "";
    			}
    			String str_date = new SimpleDateFormat("yyyy/MM/dd").format(ListcheckPanel.sdf.parse(t.Ret_date()));
				listModel.addElement(a_task[0]+"/"+str_date+"/  "+a_task[4]);
    			table.add(t);
    			line = br.readLine();
    		}
    		br.close();
    		if(cnt == 0) {
    			listModel.addElement("No Task is here...");
    			list.setFont(new Font("Serif", Font.PLAIN, 15));
    		}else {
    			list.setFont(new Font(Font.DIALOG_INPUT	, Font.PLAIN, 15));
    		}
    	} catch (IOException error) {
    		System.err.println("IOException " + error.toString());
    	}catch(ParseException e) {
        	System.err.println("ParseException " + e.toString());
        }
	}
}
