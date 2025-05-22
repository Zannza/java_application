import javax.swing.*;

public class MainGUI {
	static MainWindow mainWindow;
	static int now=0;
	
	static int permitflag=0;
	public static void main(String[] args) {

		mainWindow=new MainWindow();

		mainWindow.preparePanels();

		mainWindow.prepareComponents();

		mainWindow.setFrontScreenAndFocus(ScreenMode.LOGIN);

		mainWindow.setVisible(true);
	}
}
