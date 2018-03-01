import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class TextEditor extends JFrame{

	//Text window declarations
	private static final long serialVersionUID = 1L;
	JFrame window = new JFrame();
	JTextArea text = new JTextArea(25, 40);
	private JPanel panel = new JPanel();
	
	//Save menu declarations
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem save;
	JMenuItem saveAs;
	
	//Name that user wishes to save file as
	String filename = null;
	
	//Editor constructor
	public TextEditor() {
		
		//builds JPanel and adds to JFrame
		setTitle("Text Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new BorderLayout());
		panel.add(text, BorderLayout.SOUTH);
		add(panel);
		
		//Builds save menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menu= new JMenu("Save");
		menuBar.add(menu);
		
		//Adds 'save' option
		save = new JMenuItem("Save");
		menu.add(save);
		save.setActionCommand("save");
		save.addActionListener(new SaveListener());
		
		//Adds 'save as' option
		saveAs = new JMenuItem("Save As");
		menu.add(saveAs);
		saveAs.setActionCommand("saveAs");
		saveAs.addActionListener(new SaveListener());
	
		pack();
		setVisible(true);
		
	}
	
	//Responds to clicks on save menu
	private class SaveListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			//Checks if a file has been created, then writes current text to file if so
			if (e.getActionCommand().equals("save")) {
				if (filename == null)
					filename = JOptionPane.showInputDialog("What would you like to name the file?");
				File file = new File("C:\\Users\\Allen\\Desktop\\" + filename + ".txt");
				FileWriter fileWriter;
				try {
					fileWriter = new FileWriter(file);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					printWriter.print(text.getText());
					printWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			//Allows the user to create and name a file then save current text to it
			if (e.getActionCommand().equals("saveAs")) {
				filename = JOptionPane.showInputDialog("What would you like to name the file?");
				File file = new File("C:\\Users\\Allen\\Desktop\\" + filename + ".txt");
				FileWriter fileWriter;
				try {
					fileWriter = new FileWriter(file);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					printWriter.print(text.getText());
					printWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	//Main method that instantiates the editor
	public static void main(String[] args) {
		new TextEditor();
	}

}
