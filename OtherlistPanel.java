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
import java.util.ArrayList;
import java.util.Calendar;

public class OtherlistPanel extends JPanel {
	private JTextField field;
	public static  DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> list;
	public JLabel per=new JLabel();
	public static String othername;
	class DelButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = list.getSelectedIndex();
			if (index != -1) {	
				othername = listModel.elementAt(index);
				MainGUI.mainWindow.setFrontScreenAndFocus(ScreenMode.OTHERSEE);
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
	OtherlistPanel(){
		this.setLayout(null);
	}
	public void prepareComponents() {
		field = new JTextField("initial text");
		list = new JList<String>(listModel);
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
		this.add(selectButton);
		this.add(returnButton);
		scrollPane.setBounds(200, 20, 570, 500);
		selectButton.setBounds(10, 20, 180, 50);
		returnButton.setBounds(10, 90, 180, 50);
	}
	public void personal() {
		try {
    		File f = new File(ListcheckPanel.userdir+"\\"+ListcheckPanel.user+"\\"+ListcheckPanel.user+"-permitted.txt");
    		BufferedReader br = new BufferedReader(new FileReader(f));
    		String line = br.readLine();
    		ArrayList<String> people = new ArrayList<String>();
    		int cnt = 0;
    		while (line != null) {
    			cnt++;
    			people.add(line);
    			listModel.addElement(line);
    			line = br.readLine();
    		}
    		br.close();
    		if(cnt == 0) {
    			listModel.addElement("You aren't permitted by anyone...");
    			list.setFont(new Font("Serif", Font.PLAIN, 15));
    		}else {
    			list.setFont(new Font(Font.DIALOG_INPUT	, Font.PLAIN, 15));
    		}
    	} catch (IOException error) {
    		System.err.println("IOException " + error.toString());
    	}
	}
	
}