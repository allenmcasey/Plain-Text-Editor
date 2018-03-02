import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class TextEditor extends JFrame{

	//Text window declarations
	private static final long serialVersionUID = 1L;
	JFrame window = new JFrame();
	JTextArea text = new JTextArea(20, 50);
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
		text.setLineWrap(true);
		panel.add(text);
		add(panel);
		
		//Builds save menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menu= new JMenu("File");
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
		
		//Adds 'open' option
		save = new JMenuItem("Open");
		menu.add(save);
		save.setActionCommand("open");
		save.addActionListener(new SaveListener());
	
		pack();
		setVisible(true);
		
	}
	
	//Responds to clicks on save menu
	private class SaveListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			//Checks if a file has been created, then writes current text to file if so
			if (e.getActionCommand().equals("save")) {
				//if (filename == null)
				//	filename = JOptionPane.showInputDialog("What would you like to name the file?");
				try {
					saveFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			//Allows the user to create and name a file then save current text to it
			if (e.getActionCommand().equals("saveAs")) {
				//filename = JOptionPane.showInputDialog("What would you like to name the file?");
				try {
					saveFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			//Allows user to open and edit existing .txt files
			if (e.getActionCommand().equals("open")) {
				openFile();
			}
		}
	}
	
	//Method that saves current text field to file
	public void saveFile() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showSaveDialog(TextEditor.this);
		if (result == JFileChooser.APPROVE_OPTION) {
		  File file = fileChooser.getSelectedFile();
		  FileWriter writer = new FileWriter(file);
		  text.write(writer);
		}
	}
	
	//Method that allows the user to choose to open an existing file in the editor
	public void openFile() {
		JFileChooser fc = new JFileChooser();
		StringBuffer buffer = new StringBuffer();
		int result = fc.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fc.getSelectedFile();
				FileReader reader = new FileReader(file);
				int i = 1;
				while (i != -1) {
					i = reader.read();
					char ch = (char)i;
					buffer.append(ch);
				}
				reader.close();
				String fileText = buffer.toString().substring(0, buffer.toString().length() - 1);
				text.setText(fileText);
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//Main method that instantiates the editor
	public static void main(String[] args) {
		
		// Set System L&F
		try {
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
			
    		} 
		catch (UnsupportedLookAndFeelException e) {
		       // handle exception
		    }
		    catch (ClassNotFoundException e) {
		       // handle exception
		    }
		    catch (InstantiationException e) {
		       // handle exception
		    }
		    catch (IllegalAccessException e) {
		       // handle exception
		    }
		
		new TextEditor();
	}
}
