import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OtherseelistPanel extends JPanel {
	private JTextField field;
	public static DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> list = new JList<String>(listModel);
	public JLabel per=new JLabel();
	class DelButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = list.getSelectedIndex();	
			if (index != -1) {
				MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.LISTCHECK);
			} else {
				JOptionPane.showMessageDialog(list, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	class QuitButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.LISTCHECK);
		}
	}
	OtherseelistPanel(){
		this.setLayout(null);
	}
	public void prepareComponents() {
		field = new JTextField("initial text");
		list.setVisibleRowCount(10);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.createVerticalScrollBar();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton selectButton = new JButton("OK");
		DelButtonAction selectButtonListener = new DelButtonAction();
		selectButton.addActionListener( selectButtonListener );

		JButton returnButton = new JButton("return to my list");
		QuitButtonAction returnButtonListener = new QuitButtonAction();
		returnButton.addActionListener( returnButtonListener );

		
		this.add(scrollPane);
		this.add(returnButton);
		scrollPane.setBounds(200, 20, 570, 500);
		returnButton.setBounds(10, 20, 180, 50);	
	}
	public void personal() {
		try {
    		File f = new File(ListcheckPanel.userdir+"\\"+OtherlistPanel.othername+"\\"+OtherlistPanel.othername+".txt");
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
    			listModel.addElement(a_task[0]+a_task[1]+"/"+a_task[2]+"/"+a_task[3]+"/  "+a_task[4]);
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
    	}
	}
	
}